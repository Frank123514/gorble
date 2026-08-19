package net.francis.got.network;

import net.francis.got.event.entity.npc.data.NpcTrades;
import net.francis.got.event.entity.npc.smallfolk.SmallfolkEntity;
import net.francis.got.event.PlayerEvents;
import net.francis.got.faction.Factions;
import net.francis.got.item.Coin;
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

public final class Network {

    // registers every client<->server packet type and its handler
    public static void register(RegisterPayloadHandlersEvent event) {
        var r = event.registrar("got");

        r.playToServer(MapTeleportPayload.TYPE, MapTeleportPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    
                    if (player == null
                            || !player.permissions().hasPermission(net.minecraft.server.permissions.Permissions.COMMANDS_GAMEMASTER))
                        return;
                    ServerLevel level = (ServerLevel) player.level();
                    int x = payload.x(), z = payload.z();

                    int y;
                    if (level.getChunkSource().getGenerator()
                            instanceof net.francis.got.worldgen.GotChunkGenerator) {
                        y = net.francis.got.worldgen.GotChunkGenerator.computeSurfaceY(x, z);
                    } else {
                        level.getChunk(x >> 4, z >> 4);
                        y = level.getHeight(Heightmap.Types.WORLD_SURFACE, x, z);
                    }
                    if (y < level.getMinY()) y = level.getMinY();
                    player.teleportTo(level, x + 0.5, y + 1, z + 0.5,
                            Set.of(), player.getYRot(), player.getXRot(), false);
                }));

        r.playToClient(OpenInteractScreenPayload.TYPE, OpenInteractScreenPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    if (FMLEnvironment.getDist() == Dist.CLIENT) {
                        net.francis.got.client.gui.NpcInteractScreen.open(
                                payload.entityId(), payload.occupationId(), payload.npcName(), payload.militaryTitle());
                    }
                }));

        r.playToServer(CloseInteractScreenPayload.TYPE, CloseInteractScreenPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    if (player == null) return;
                    Entity entity = ((ServerLevel) player.level()).getEntity(payload.entityId());
                    if (entity instanceof SmallfolkEntity npc) npc.stopTalking();
                }));

        r.playToServer(RequestTradeMenuPayload.TYPE, RequestTradeMenuPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    if (player == null) return;
                    Entity entity = ((ServerLevel) player.level()).getEntity(payload.entityId());
                    if (!(entity instanceof SmallfolkEntity npc)) return;
                    if (!npc.getOccupation().isEmployed()) return;
                    if (npc.distanceTo(player) > 12.0f) return;

                    npc.startTalkingTo(player);
                    npc.extendTalkTimer(600);

                    String npcName = npc.getNpcName().isEmpty()
                            ? npc.getType().getDescription().getString()
                            : npc.getNpcName();
                    
                    net.neoforged.neoforge.network.PacketDistributor.sendToPlayer(player,
                            new OpenTradeScreenPayload(payload.entityId(),
                                    npc.getOccupation().id, npcName));
                }));

        r.playToClient(OpenTradeScreenPayload.TYPE, OpenTradeScreenPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    if (FMLEnvironment.getDist() == Dist.CLIENT) {
                        net.francis.got.client.gui.NpcTradeScreen.open(
                                payload.entityId(), payload.occupationId(), payload.npcName());
                    }
                }));

        r.playToServer(ExecuteSellPayload.TYPE, ExecuteSellPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    if (player == null) return;

                    Entity entity = ((ServerLevel) player.level()).getEntity(payload.entityId());
                    if (!(entity instanceof SmallfolkEntity npc)) return;
                    if (!npc.getOccupation().isEmployed()) return;
                    if (npc.distanceTo(player) > 12.0f) return;

                    List<NpcTrades.SellOffer> sellOffers =
                            NpcTrades.getSellOffers(npc.getOccupation());
                    int idx = payload.offerIndex();
                    if (idx < 0 || idx >= sellOffers.size()) return;

                    NpcTrades.SellOffer offer = sellOffers.get(idx);
                    Inventory inv = player.getInventory();

                    int found = 0;
                    for (int i = 0; i < inv.getContainerSize(); i++) {
                        ItemStack s = inv.getItem(i);
                        if (s.is(offer.costItem())) found += s.getCount();
                    }
                    if (found < offer.costCount()) return;

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

                    inv.add(offer.coinStack());
                    inv.setChanged();
                }));

        r.playToServer(CoinExchangePayload.TYPE, CoinExchangePayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    if (player == null) return;
                    Inventory inv = player.getInventory();
                    Coin coin = Coin.fromId(payload.fromCoinId());

                    // break 1 coin into "ratio" smaller coins, or combine "ratio" smaller coins into 1
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

        r.playToClient(FactionSyncPayload.TYPE, FactionSyncPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    if (FMLEnvironment.getDist() == Dist.CLIENT) {
                        net.francis.got.client.ClientFactionCache.onSyncReceived(payload);
                    }
                }));

        r.playToClient(OpenFactionScreenPayload.TYPE, OpenFactionScreenPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    if (FMLEnvironment.getDist() == Dist.CLIENT) {
                        net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
                        if (mc != null) mc.setScreen(new net.francis.got.client.gui.FactionSelectionScreen());
                    }
                }));

        r.playToServer(SelectFactionPayload.TYPE, SelectFactionPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    if (player == null) return;
                    
                    if (Factions.BY_ID.containsKey(payload.factionId())) {
                        PlayerEvents.setFactionId(player, payload.factionId());
                    }
                }));

        r.playToServer(SelectSmithyRecipePayload.TYPE, SelectSmithyRecipePayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    if (player == null) return;
                    if (player.containerMenu instanceof net.francis.got.menu.SmithyMenu menu &&
                            menu.getContainer() instanceof net.francis.got.block.ForgeBlockEntity be) {
                        be.setSelectedRecipeIndex(payload.recipeIndex());
                    }
                }));

        r.playToServer(SelectSmithingAnvilRecipePayload.TYPE, SelectSmithingAnvilRecipePayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    if (player == null) return;
                    if (player.containerMenu instanceof net.francis.got.menu.SmithingAnvilMenu menu &&
                            menu.getContainer() instanceof net.francis.got.block.SmithingAnvilBlockEntity be) {
                        be.setSelectedRecipeIndex(payload.recipeIndex());
                        
                        if (payload.recipeIndex() >= 0) {
                            player.closeContainer();
                        }
                    }
                }));

        r.playToServer(SelectAlloyRecipePayload.TYPE, SelectAlloyRecipePayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    if (player == null) return;
                    if (player.containerMenu instanceof net.francis.got.menu.AlloyMenu menu &&
                            menu.getContainer() instanceof net.francis.got.block.ForgeBlockEntity be) {
                        be.setSelectedRecipeIndex(payload.recipeIndex());
                    }
                }));

        r.playToServer(SelectForgeModePayload.TYPE, SelectForgeModePayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    if (player == null) return;
                    net.francis.got.block.ForgeBlockEntity be = null;
                    if (player.containerMenu instanceof net.francis.got.menu.HeatTreatingMenu menu &&
                            menu.getContainer() instanceof net.francis.got.block.ForgeBlockEntity fbe) {
                        be = fbe;
                    } else if (player.containerMenu instanceof net.francis.got.menu.AlloyMenu menu &&
                            menu.getContainer() instanceof net.francis.got.block.ForgeBlockEntity fbe) {
                        be = fbe;
                    }
                    if (be != null) {
                        be.setMode(payload.mode());
                        player.openMenu(be);
                    }
                }));

        r.playToClient(SeasonSyncPayload.TYPE, SeasonSyncPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    if (FMLEnvironment.getDist() == Dist.CLIENT) {
                        net.francis.got.climate.Season prev = net.francis.got.climate.SeasonCache.get();
                        net.francis.got.climate.SeasonCache.set(payload.season());
                        
                        if (payload.season() != prev) {
                            net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
                            if (mc != null && mc.levelRenderer != null) {
                                mc.levelRenderer.allChanged();
                            }
                        }
                    }
                }));

        r.playToClient(PlayerVitalsPayload.TYPE, PlayerVitalsPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    if (FMLEnvironment.getDist() == Dist.CLIENT) {
                        net.francis.got.client.gui.overlay.TemperatureHudOverlay
                                .setClientVitals(payload.bodyTemp(), payload.thirst());
                    }
                }));

        r.playToClient(SmithingAnvilStatePayload.TYPE, SmithingAnvilStatePayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    if (FMLEnvironment.getDist() == Dist.CLIENT) {
                        net.francis.got.client.gui.overlay.SmithingAnvilHudOverlay.onStatePacket(payload);
                    }
                }));

        r.playToClient(SkillSyncPayload.TYPE, SkillSyncPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    if (FMLEnvironment.getDist() == Dist.CLIENT) {
                        net.francis.got.client.ClientSkillCache.onSyncReceived(payload);
                    }
                }));

        r.playToServer(UnlockPerkPayload.TYPE, UnlockPerkPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    if (player == null) return;

                    net.francis.got.skill.SkillPerk perk = net.francis.got.skill.SkillPerks.byId(payload.perkId());
                    if (perk == null) return;

                    if (net.francis.got.skill.PlayerSkillState.unlockPerk(player, perk)) {
                        net.francis.got.skill.SkillPerkEffects.applyAttributeModifiers(player);
                        net.francis.got.skill.SkillXpService.syncToClient(player);
                    }
                }));
    }

    public static void init() {}
}