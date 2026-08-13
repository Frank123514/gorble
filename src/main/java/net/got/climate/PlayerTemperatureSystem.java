package net.got.climate;

import net.got.GotMod;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@EventBusSubscriber(modid = GotMod.MODID)
public final class PlayerTemperatureSystem {

    public static final float BODY_MAX        = 1.0f;
    public static final float BODY_MIN        = 0.0f;
    public static final float BODY_OVERHEAT   = 0.85f;
    public static final float BODY_WARM       = 0.60f;
    public static final float BODY_CHILLY     = 0.40f;
    public static final float BODY_COLD       = 0.20f;

    private static final float DRIFT_NORMAL       = 0.035f;
    
    private static final float DRIFT_WET_COLD     = 0.09f;
    
    private static final float DRIFT_WET_HOT      = 0.05f;
    
    private static final float ARMOR_INSULATION   = 0.007f;
    
    private static final float HEAT_SOURCE_BONUS  = 0.06f;

    private static final int   INTERVAL           = 20;
    private static final float FREEZE_DAMAGE      = 0.5f;
    private static final float OVERHEAT_DAMAGE    = 0.5f;

    public static final float ADJ_SUMMER =  0.00f;
    public static final float ADJ_SPRING = +0.05f;
    public static final float ADJ_AUTUMN = -0.20f;
    public static final float ADJ_WINTER = -0.80f;

    public static final float BIOME_TEMP_MIN   = -0.5f;
    public static final float BIOME_TEMP_MAX   =  2.0f;
    private static final float BIOME_RANGE     = BIOME_TEMP_MAX - BIOME_TEMP_MIN;

    private static final ConcurrentHashMap<UUID, Float> BODY_TEMPS =
            new ConcurrentHashMap<>();

    public static float getBodyTemp(UUID id) {
        return BODY_TEMPS.getOrDefault(id, 0.5f);
    }

    public static void setBodyTemp(UUID id, float value) {
        BODY_TEMPS.put(id, Mth.clamp(value, BODY_MIN, BODY_MAX));
    }

    public static void remove(UUID id) {
        BODY_TEMPS.remove(id);
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;
        if (!(player instanceof ServerPlayer sp)) return;
        if (player.isCreative() || player.isSpectator()) return;
        if (player.tickCount % INTERVAL != 0) return;

        UUID  id   = player.getUUID();
        float body = getBodyTemp(id);

        float envTarget = computeEnvTarget(sp);
        body = drift(body, envTarget, sp);

        setBodyTemp(id, body);
        applyEffects(sp, body);
    }

    private static float computeEnvTarget(ServerPlayer player) {
        if (isNearHeatSource(player)) return Mth.clamp(BODY_WARM + 0.1f, BODY_MIN, BODY_MAX);
        if (!isOutdoors(player))      return 0.5f;
        return biomeToBodyTarget(getEffectiveBiomeTemp(player));
    }

    public static float getEffectiveBiomeTemp(ServerPlayer player) {
        var   level = (net.minecraft.server.level.ServerLevel) player.level();
        var   pos   = player.blockPosition();
        Biome biome = level.getBiome(pos).value();
        float base  = biome.getTemperature(pos, level.getSeaLevel());
        float latitudeAdj = LatitudeClimate.temperatureAdjustment(pos.getX(), pos.getZ());

        if (biome.getBaseTemperature() > 0.8f) {
            return Mth.clamp(base + latitudeAdj, BIOME_TEMP_MIN, BIOME_TEMP_MAX);
        }

        float adj = switch (SeasonCache.get()) {
            case SUMMER -> ADJ_SUMMER;
            case SPRING -> ADJ_SPRING;
            case AUTUMN -> ADJ_AUTUMN;
            case WINTER -> ADJ_WINTER;
        };
        return Mth.clamp(base + adj + latitudeAdj, BIOME_TEMP_MIN, BIOME_TEMP_MAX);
    }

    private static float biomeToBodyTarget(float effectiveBiomeTemp) {
        return Mth.clamp(
                (effectiveBiomeTemp - BIOME_TEMP_MIN) / BIOME_RANGE,
                BODY_MIN, BODY_MAX
        );
    }

    // moves body temp toward the target, capped per tick; armor/heat/wetness change the rate
    private static float drift(float current, float target, ServerPlayer player) {
        float diff = target - current;
        float step;

        boolean wet = isWet(player);

        if (diff > 0f) {
            
            step = Math.min(diff, DRIFT_NORMAL);
            if (isNearHeatSource(player)) step += HEAT_SOURCE_BONUS;
            
            if (wet && current > 0.5f) step -= DRIFT_WET_HOT;
        } else {
            
            int   armor      = countArmor(player);
            float insulation = armor * ARMOR_INSULATION;
            float rate       = DRIFT_NORMAL - insulation;

            if (wet && current < 0.5f) rate += DRIFT_WET_COLD;

            step = Math.max(diff, -Math.max(0f, rate));
        }

        return Mth.clamp(current + step, BODY_MIN, BODY_MAX);
    }

    private static void applyEffects(ServerPlayer player, float body) {
        
        player.removeEffect(MobEffects.SLOWNESS);
        player.removeEffect(MobEffects.MINING_FATIGUE);
        player.removeEffect(MobEffects.NAUSEA);

        if (body < BODY_COLD) {
            player.hurt(player.damageSources().freeze(), FREEZE_DAMAGE);
        }

        if (body >= BODY_MAX) {
            player.hurt(player.damageSources().hotFloor(), OVERHEAT_DAMAGE);
        }
    }

    public static boolean isWet(ServerPlayer player) {
        var level = (net.minecraft.server.level.ServerLevel) player.level();
        var pos   = player.blockPosition();
        
        FluidState fluid = level.getFluidState(pos);
        if (!fluid.isEmpty() && fluid.is(Fluids.WATER)) return true;
        if (!fluid.isEmpty() && fluid.is(Fluids.FLOWING_WATER)) return true;
        
        return level.isRainingAt(pos) && isOutdoors(player);
    }

    private static boolean isOutdoors(ServerPlayer player) {
        return ((net.minecraft.server.level.ServerLevel) player.level()).canSeeSky(player.blockPosition().above());
    }

    private static boolean isNearHeatSource(ServerPlayer player) {
        var level = (net.minecraft.server.level.ServerLevel) player.level();
        var pos   = player.blockPosition();
        for (int dy = -1; dy <= 2; dy++) {
            var state = level.getBlockState(pos.below(dy));
            if (state.is(net.minecraft.tags.BlockTags.CAMPFIRES)
                    || state.getBlock() == net.minecraft.world.level.block.Blocks.LAVA
                    || state.getBlock() == net.minecraft.world.level.block.Blocks.FIRE
                    || state.getBlock() == net.minecraft.world.level.block.Blocks.SOUL_FIRE) {
                return true;
            }
        }
        return false;
    }

    private static int countArmor(ServerPlayer player) {
        int n = 0;
        for (EquipmentSlot slot : new EquipmentSlot[]{
                EquipmentSlot.HEAD, EquipmentSlot.CHEST,
                EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
            
            ItemStack s = player.getItemBySlot(slot);
            if (!s.isEmpty()) n++;
        }
        return n;
    }

    public static TempBand getBand(UUID id) {
        float t = getBodyTemp(id);
        if (t >= BODY_OVERHEAT) return TempBand.OVERHEATED;
        if (t >= BODY_WARM)     return TempBand.WARM;
        if (t >= BODY_CHILLY)   return TempBand.CHILLY;
        if (t >= BODY_COLD)     return TempBand.COLD;
        return TempBand.FREEZING;
    }

    public enum TempBand {
        FREEZING  ("Freezing",   0xFF4466EE),
        COLD      ("Cold",       0xFF88AAFF),
        CHILLY    ("Chilly",     0xFFBBDDFF),
        WARM      ("Warm",       0xFFFFFFFF),
        OVERHEATED("Overheated", 0xFFFF6622);

        public final String label;
        public final int    color;
        TempBand(String label, int color) { this.label = label; this.color = color; }
    }

    private PlayerTemperatureSystem() {}
}