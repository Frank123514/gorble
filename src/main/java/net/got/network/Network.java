package net.got.network;

import net.got.event.entity.npc.data.NpcTrades;
import net.got.event.entity.npc.smallfolk.SmallfolkEntity;
import net.got.event.PlayerEvents;
import net.got.faction.Factions;
import net.got.item.Coin;
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
                            instanceof net.got.worldgen.GotChunkGenerator) {
                        y = net.got.worldgen.GotChunkGenerator.computeSurfaceY(x, z);
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
                        net.got.client.gui.NpcInteractScreen.open(
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
                        net.got.client.gui.NpcTradeScreen.open(
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
                        net.got.client.ClientFactionCache.onSyncReceived(payload);
                    }
                }));

        r.playToClient(OpenFactionScreenPayload.TYPE, OpenFactionScreenPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    if (FMLEnvironment.getDist() == Dist.CLIENT) {
                        net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
                        if (mc != null) mc.setScreen(new net.got.client.gui.FactionSelectionScreen());
                    }
                }));

        r.playToServer(SelectFactionPayload.TYPE, SelectFactionPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    if (player == null) return;

                    if (Factions.BY_ID.containsKey(payload.factionId())) {
                        PlayerEvents.setFactionId(player, payload.factionId());
                        net.got.intro.IntroState.setPendingWaypoint(player, payload.waypointName());
                    }
                }));

        // Opens the black-screen intro on login for anyone on a knownworld-preset
        // save who hasn't finished character creation yet. resumeAtFinalLine
        // skips straight to "Very well." for players who picked a faction but
        // disconnected before clicking through the closing line.
        r.playToClient(PlayIntroSequencePayload.TYPE, PlayIntroSequencePayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    if (FMLEnvironment.getDist() == Dist.CLIENT) {
                        net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
                        if (mc == null) return;
                        mc.setScreen(new net.got.client.gui.IntroScreen(
                                payload.resumeAtFinalLine()
                                        ? net.got.client.gui.IntroScreen.Mode.FINAL_ONLY
                                        : net.got.client.gui.IntroScreen.Mode.FULL));
                    }
                }));

        r.playToServer(SetCharacterNamePayload.TYPE, SetCharacterNamePayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    if (player == null) return;
                    String name = payload.name() == null ? "" : payload.name().trim();
                    if (name.isEmpty()) return;
                    if (name.length() > 24) name = name.substring(0, 24);
                    net.got.intro.IntroState.setCharacterName(player, name);
                }));

        // No dimension change (the player has been standing in the knownworld
        // the whole time), but this is the moment we move them to the exact
        // waypoint they dialed in on the faction screen's location nav.
        r.playToServer(CompleteIntroPayload.TYPE, CompleteIntroPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    if (player == null) return;

                    ServerLevel level = (ServerLevel) player.level();
                    String factionId = net.got.faction.PlayerFactionState.getFactionId(player);
                    String waypointName = net.got.intro.IntroState.getPendingWaypoint(player);

                    Integer pixelX = null, pixelY = null;
                    if (factionId != null && !waypointName.isEmpty()) {
                        for (net.got.faction.WaypointData wp :
                                net.got.faction.WaypointRegistry.BY_FACTION.getOrDefault(factionId, List.of())) {
                            if (wp.name().equals(waypointName)) {
                                pixelX = wp.pixelX();
                                pixelY = wp.pixelY();
                                break;
                            }
                        }
                    }
                    if (pixelX == null) {
                        // faction with no waypoints defined (e.g. the Night's Watch),
                        // or nothing was ever selected - fall back to the default spawn
                        pixelX = net.got.worldgen.ModDimensions.SPAWN_PIXEL_X;
                        pixelY = net.got.worldgen.ModDimensions.SPAWN_PIXEL_Z;
                    }

                    int worldX = net.got.worldgen.ModDimensions.mapPixelToWorldX(pixelX);
                    int worldZ = net.got.worldgen.ModDimensions.mapPixelToWorldZ(pixelY);
                    int y = net.got.worldgen.GotChunkGenerator.computeSurfaceY(worldX, worldZ);
                    if (y < level.getMinY()) y = level.getMinY();

                    player.teleportTo(level, worldX + 0.5, y + 1, worldZ + 0.5,
                            Set.of(), player.getYRot(), player.getXRot(), false);

                    net.got.intro.IntroState.markEnteredKnownWorld(player);
                }));

        r.playToServer(SelectSmithyRecipePayload.TYPE, SelectSmithyRecipePayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    if (player == null) return;
                    if (player.containerMenu instanceof net.got.menu.SmithyMenu menu &&
                            menu.getContainer() instanceof net.got.block.ForgeBlockEntity be) {
                        be.setSelectedRecipeIndex(payload.recipeIndex());
                    }
                }));

        r.playToServer(SelectSmithingAnvilRecipePayload.TYPE, SelectSmithingAnvilRecipePayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    if (player == null) return;
                    if (player.containerMenu instanceof net.got.menu.SmithingAnvilMenu menu &&
                            menu.getContainer() instanceof net.got.block.SmithingAnvilBlockEntity be) {
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
                    if (player.containerMenu instanceof net.got.menu.AlloyMenu menu &&
                            menu.getContainer() instanceof net.got.block.ForgeBlockEntity be) {
                        be.setSelectedRecipeIndex(payload.recipeIndex());
                    }
                }));

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

        r.playToClient(SeasonSyncPayload.TYPE, SeasonSyncPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    if (FMLEnvironment.getDist() == Dist.CLIENT) {
                        net.got.climate.Season prev = net.got.climate.SeasonCache.get();
                        net.got.climate.SeasonCache.set(payload.season());

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
                        net.got.client.gui.overlay.TemperatureHudOverlay
                                .setClientVitals(payload.bodyTemp(), payload.thirst());
                    }
                }));

        r.playToClient(SmithingAnvilStatePayload.TYPE, SmithingAnvilStatePayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    if (FMLEnvironment.getDist() == Dist.CLIENT) {
                        net.got.client.gui.overlay.SmithingAnvilHudOverlay.onStatePacket(payload);
                    }
                }));

        r.playToClient(SkillSyncPayload.TYPE, SkillSyncPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    if (FMLEnvironment.getDist() == Dist.CLIENT) {
                        net.got.client.ClientSkillCache.onSyncReceived(payload);
                    }
                }));

        r.playToServer(UnlockPerkPayload.TYPE, UnlockPerkPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> {
                    ServerPlayer player = (ServerPlayer) ctx.player();
                    if (player == null) return;

                    net.got.skill.SkillPerk perk = net.got.skill.SkillPerks.byId(payload.perkId());
                    if (perk == null) return;

                    if (net.got.skill.PlayerSkillState.unlockPerk(player, perk)) {
                        net.got.skill.SkillPerkEffects.applyAttributeModifiers(player);
                        net.got.skill.SkillXpService.syncToClient(player);
                    }
                }));
    }

    public static void init() {}
}