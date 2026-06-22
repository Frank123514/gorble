package net.got.network;

import net.got.entity.npc.data.GotNpcTrades;
import net.got.entity.npc.smallfolk.SmallfolkEntity;
import net.got.event.GotPlayerEvents;
import net.got.faction.GotFactions;
import net.got.item.GotCoin;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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
                                payload.entityId(), payload.occupationId(), payload.npcName(), payload.militaryTitle());
                    }
                }));

        // ── Close interact screen (C→S) ───────────────────────────────────────
        r.playToServer(CloseInteractScreenPayload.TYPE, CloseInteractScreenPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    if (player == null) return;
                    Entity entity = player.serverLevel().getEntity(payload.entityId());
                    if (entity instanceof SmallfolkEntity npc) npc.stopTalking();
                }));

        // ── Request trade menu (C→S) → reply with OpenTradeScreenPayload ─────
        r.playToServer(RequestTradeMenuPayload.TYPE, RequestTradeMenuPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    if (player == null) return;
                    Entity entity = player.serverLevel().getEntity(payload.entityId());
                    if (!(entity instanceof SmallfolkEntity npc)) return;
                    if (!npc.getOccupation().isEmployed()) return;
                    if (npc.distanceTo(player) > 12.0f) return;

                    // Keep NPC frozen while player is in the trade screen
                    npc.startTalkingTo(player);
                    npc.extendTalkTimer(600);

                    String npcName = npc.getNpcName().isEmpty()
                            ? npc.getType().getDescription().getString()
                            : npc.getNpcName();
                    // Send custom payload — client opens NpcTradeScreen directly (no container menu)
                    net.neoforged.neoforge.network.PacketDistributor.sendToPlayer(player,
                            new OpenTradeScreenPayload(payload.entityId(),
                                    npc.getOccupation().id, npcName));
                }));

        // ── Open trade screen (S→C) ───────────────────────────────────────────
        r.playToClient(OpenTradeScreenPayload.TYPE, OpenTradeScreenPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    if (FMLEnvironment.dist == Dist.CLIENT) {
                        net.got.client.gui.NpcTradeScreen.open(
                                payload.entityId(), payload.occupationId(), payload.npcName());
                    }
                }));

        // ── Execute sell trade (C→S) ──────────────────────────────────────────
        r.playToServer(ExecuteSellPayload.TYPE, ExecuteSellPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    if (player == null) return;

                    // Validate NPC still exists and player is close enough
                    Entity entity = player.serverLevel().getEntity(payload.entityId());
                    if (!(entity instanceof SmallfolkEntity npc)) return;
                    if (!npc.getOccupation().isEmployed()) return;
                    if (npc.distanceTo(player) > 12.0f) return;

                    List<GotNpcTrades.SellOffer> sellOffers =
                            GotNpcTrades.getSellOffers(npc.getOccupation());
                    int idx = payload.offerIndex();
                    if (idx < 0 || idx >= sellOffers.size()) return;

                    GotNpcTrades.SellOffer offer = sellOffers.get(idx);
                    Inventory inv = player.getInventory();

                    // Count matching items across all inventory slots
                    int found = 0;
                    for (int i = 0; i < inv.getContainerSize(); i++) {
                        ItemStack s = inv.getItem(i);
                        if (s.is(offer.costItem())) found += s.getCount();
                    }
                    if (found < offer.costCount()) return;

                    // Remove required items from inventory
                    int toRemove = offer.costCount();
                    for (int i = 0; i < inv.getContainerSize() && toRemove > 0; i++) {
                        ItemStack s = inv.getItem(i);
                        if (s.is(offer.costItem())) {
                            int take = Math.min(toRemove, s.getCount());
                            s.shrink(take);
                            toRemove -= take;
                            if (s.isEmpty()) inv.setItem(i, ItemStack.EMPTY);
                        }
                    }

                    // Grant coin reward
                    inv.add(offer.coinStack());
                    inv.setChanged();
                }));

        // ── Coin exchange (C→S) ───────────────────────────────────────────────
        r.playToServer(CoinExchangePayload.TYPE, CoinExchangePayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    if (player == null) return;
                    Inventory inv = player.getInventory();
                    GotCoin coin = GotCoin.fromId(payload.fromCoinId());

                    if (payload.toSmaller()) {
                        if (coin.smaller == null) return;
                        if (!coin.removeFrom(inv, 1)) return;
                        inv.add(coin.smaller.stack(coin.ratio()));
                    } else {
                        if (coin.smaller == null) return;
                        int ratio = coin.ratio();
                        if (!coin.smaller.removeFrom(inv, ratio)) return;
                        inv.add(coin.stack(1));
                    }
                    inv.setChanged();
                }));

        // ── Faction sync (S→C) ───────────────────────────────────────────────
        r.playToClient(FactionSyncPayload.TYPE, FactionSyncPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    if (FMLEnvironment.dist == Dist.CLIENT) {
                        net.got.client.ClientFactionCache.onSyncReceived(payload);
                    }
                }));

        // ── Open faction screen (S→C) ─────────────────────────────────────────
        r.playToClient(OpenFactionScreenPayload.TYPE, OpenFactionScreenPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    if (FMLEnvironment.dist == Dist.CLIENT) {
                        net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
                        if (mc != null) mc.setScreen(new net.got.client.gui.FactionSelectionScreen());
                    }
                }));

        // ── Select faction (C→S) ──────────────────────────────────────────────
        r.playToServer(SelectFactionPayload.TYPE, SelectFactionPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    if (player == null) return;
                    // Validate the faction id server-side before accepting it
                    if (GotFactions.BY_ID.containsKey(payload.factionId())) {
                        GotPlayerEvents.setFactionId(player, payload.factionId());
                    }
                }));

        // ── Select smithy recipe (C→S) — legacy SmithyMenu on ForgeBlockEntity ──
        r.playToServer(SelectSmithyRecipePayload.TYPE, SelectSmithyRecipePayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    if (player == null) return;
                    if (player.containerMenu instanceof net.got.menu.SmithyMenu menu &&
                            menu.getContainer() instanceof net.got.block.ForgeBlockEntity be) {
                        be.setSelectedRecipeIndex(payload.recipeIndex());
                    }
                }));

        // ── Select smithing anvil recipe (C→S) ───────────────────────────────────
        r.playToServer(SelectSmithingAnvilRecipePayload.TYPE, SelectSmithingAnvilRecipePayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    if (player == null) return;
                    if (player.containerMenu instanceof net.got.menu.SmithingAnvilMenu menu &&
                            menu.getContainer() instanceof net.got.block.SmithingAnvilBlockEntity be) {
                        be.setSelectedRecipeIndex(payload.recipeIndex());
                    }
                }));

        // ── Select alloy recipe (C→S) ────────────────────────────────────────────
        r.playToServer(SelectAlloyRecipePayload.TYPE, SelectAlloyRecipePayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    if (player == null) return;
                    if (player.containerMenu instanceof net.got.menu.AlloyMenu menu &&
                            menu.getContainer() instanceof net.got.block.ForgeBlockEntity be) {
                        be.setSelectedRecipeIndex(payload.recipeIndex());
                    }
                }));

        // ── Select forge mode (C→S) — switches Heat Treating <-> Alloying ───────
        r.playToServer(SelectForgeModePayload.TYPE, SelectForgeModePayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    if (player == null) return;
                    net.got.block.ForgeBlockEntity be = null;
                    if (player.containerMenu instanceof net.got.menu.HeatTreatingMenu menu &&
                            menu.getContainer() instanceof net.got.block.ForgeBlockEntity fbe) {
                        be = fbe;
                    } else if (player.containerMenu instanceof net.got.menu.AlloyMenu menu &&
                            menu.getContainer() instanceof net.got.block.ForgeBlockEntity fbe) {
                        be = fbe;
                    }
                    if (be != null) {
                        be.setMode(payload.mode());
                        player.openMenu(be);
                    }
                }));

        // ── Season sync (S→C) ────────────────────────────────────────────────────
        r.playToClient(SeasonSyncPayload.TYPE, SeasonSyncPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    if (FMLEnvironment.dist == Dist.CLIENT) {
                        net.got.climate.GotSeason prev = net.got.climate.SeasonCache.get();
                        net.got.climate.SeasonCache.set(payload.season());
                        // Re-render all loaded chunks immediately so foliage colors
                        // update at once rather than trickling in as chunks re-mesh.
                        if (payload.season() != prev) {
                            net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
                            if (mc != null && mc.levelRenderer != null) {
                                mc.levelRenderer.allChanged();
                            }
                        }
                    }
                }));

        // ── Player temperature sync (S→C) ─────────────────────────────────────
        r.playToClient(PlayerVitalsPayload.TYPE, PlayerVitalsPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    if (FMLEnvironment.dist == Dist.CLIENT) {
                        net.got.client.gui.overlay.TemperatureHudOverlay
                                .setClientVitals(payload.bodyTemp(), payload.thirst());
                    }
                }));

        // ── Combat animation trigger (S→C) ────────────────────────────────────
        r.playToClient(GotCombatAnimPayload.TYPE, GotCombatAnimPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    if (FMLEnvironment.dist == Dist.CLIENT) {
                        net.got.client.animation.GotCombatAnimationHandler
                                .onCombatAnimPayload(payload);
                    }
                }));
    }

    public static void init() {}
}