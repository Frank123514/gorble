package net.got.network;

import net.got.entity.npc.data.GotNpcTrades;
import net.got.entity.npc.smallfolk.SmallfolkEntity;
import net.got.item.GotCoin;
import net.got.menu.NpcTradeMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

import java.util.List;
import java.util.Set;

public final class GotNetwork {

    public static void register(RegisterPayloadHandlersEvent event) {
        var r = event.registrar("got");

        // ── Map teleport (C→S) ────────────────────────────────────────────────
        r.playToServer(MapTeleportPayload.TYPE, MapTeleportPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    if (player == null || !player.hasPermissions(2)) return;
                    ServerLevel level = player.serverLevel();
                    int x = payload.x(), z = payload.z();
                    level.getChunk(x >> 4, z >> 4);
                    int y = level.getHeight(Heightmap.Types.WORLD_SURFACE, x, z);
                    if (y < level.getMinY()) y = level.getMinY();
                    player.teleportTo(level, x + 0.5, y + 1, z + 0.5,
                            Set.of(), player.getYRot(), player.getXRot(), false);
                }));

        // ── Open interact screen (S→C) ────────────────────────────────────────
        r.playToClient(OpenInteractScreenPayload.TYPE, OpenInteractScreenPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    if (FMLEnvironment.dist == Dist.CLIENT) {
                        net.got.client.gui.NpcInteractScreen.open(
                                payload.entityId(), payload.occupationId(), payload.npcName());
                    }
                }));

        // ── Request trade menu (C→S) ──────────────────────────────────────────
        r.playToServer(RequestTradeMenuPayload.TYPE, RequestTradeMenuPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    if (player == null) return;
                    Entity entity = player.serverLevel().getEntity(payload.entityId());
                    if (!(entity instanceof SmallfolkEntity npc)) return;
                    if (!npc.getOccupation().isEmployed()) return;
                    if (npc.distanceTo(player) > 12.0f) return;

                    String npcName = npc.getNpcName().isEmpty()
                            ? npc.getType().getDescription().getString()
                            : npc.getNpcName();
                    MenuProvider provider = new SimpleMenuProvider(
                            (id, playerInv, p) ->
                                    new NpcTradeMenu(id, playerInv, npc.getOccupation(), npcName),
                            Component.literal(npcName));
                    player.openMenu(provider);
                }));

        // ── Execute sell trade (C→S) ──────────────────────────────────────────
        r.playToServer(ExecuteSellPayload.TYPE, ExecuteSellPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    if (player == null) return;
                    if (!(player.containerMenu instanceof NpcTradeMenu menu)) return;

                    List<GotNpcTrades.SellOffer> sellOffers =
                            GotNpcTrades.getSellOffers(menu.getOccupation());
                    int idx = payload.offerIndex();
                    if (idx < 0 || idx >= sellOffers.size()) return;

                    GotNpcTrades.SellOffer offer = sellOffers.get(idx);
                    ItemStack slotItem = menu.getSellInputSlot().getItem(0);

                    if (slotItem.isEmpty() || !slotItem.is(offer.costItem())) return;
                    if (slotItem.getCount() < offer.costCount()) return;

                    slotItem.shrink(offer.costCount());
                    menu.getSellInputSlot().setItem(0,
                            slotItem.isEmpty() ? ItemStack.EMPTY : slotItem);
                    player.getInventory().add(offer.coinStack());
                    player.getInventory().setChanged();
                    menu.getSellInputSlot().setChanged();
                }));

        // ── Coin exchange (C→S) ───────────────────────────────────────────────
        r.playToServer(CoinExchangePayload.TYPE, CoinExchangePayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    if (player == null) return;
                    Inventory inv = player.getInventory();
                    GotCoin coin = GotCoin.fromId(payload.fromCoinId());

                    if (payload.toSmaller()) {
                        // Break 1 coin → ratio smaller coins
                        if (coin.smaller == null) return;
                        if (!coin.removeFrom(inv, 1)) return;
                        inv.add(coin.smaller.stack(coin.ratio()));
                    } else {
                        // Combine ratio smaller coins → 1 coin
                        if (coin.smaller == null) return;
                        int ratio = coin.ratio();
                        if (!coin.smaller.removeFrom(inv, ratio)) return;
                        inv.add(coin.stack(1));
                    }
                    inv.setChanged();
                }));
    }

    public static void init() {}
}
