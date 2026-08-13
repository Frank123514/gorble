package net.got.climate;

import net.got.GotMod;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@EventBusSubscriber(modid = GotMod.MODID)
public final class PlayerThirstSystem {

    public static final float THIRST_MAX         = 1.0f;
    public static final float THIRST_MIN         = 0.0f;
    public static final float THIRST_HYDRATED    = 0.50f;
    public static final float THIRST_THIRSTY     = 0.25f;
    public static final float THIRST_CRITICAL    = 0.10f;

    private static final float BASE_DRAIN        = 0.004f;
    
    private static final float HEAT_DRAIN_FACTOR = 0.030f;
    private static final float DEHYDRATION_DMG   = 0.5f;
    private static final int   INTERVAL          = 20;

    private static final float DRINK_WATER_BOTTLE = 0.60f;
    private static final float DRINK_OTHER        = 0.30f;

    private static final ConcurrentHashMap<UUID, Float> THIRST =
            new ConcurrentHashMap<>();

    public static float getThirst(UUID id) {
        return THIRST.getOrDefault(id, THIRST_MAX);
    }

    public static void setThirst(UUID id, float value) {
        THIRST.put(id, Mth.clamp(value, THIRST_MIN, THIRST_MAX));
    }

    public static void remove(UUID id) {
        THIRST.remove(id);
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;
        if (!(player instanceof ServerPlayer sp)) return;
        if (player.isCreative() || player.isSpectator()) return;
        if (player.tickCount % INTERVAL != 0) return;

        UUID  id     = player.getUUID();
        float thirst = getThirst(id);

        float bodyTemp = PlayerTemperatureSystem.getBodyTemp(id);
        float heatBonus = Math.max(0f, bodyTemp - 0.5f) * HEAT_DRAIN_FACTOR;
        float drain = BASE_DRAIN + heatBonus;

        thirst = Mth.clamp(thirst - drain, THIRST_MIN, THIRST_MAX);
        setThirst(id, thirst);
        applyEffects(sp, thirst);
    }

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;
        if (!(player instanceof ServerPlayer sp)) return;

        var stack = event.getItemStack();
        float restore = 0f;

        if (stack.is(Items.POTION)) {
            
            var contents = stack.get(net.minecraft.core.component.DataComponents.POTION_CONTENTS);
            if (contents != null && contents.is(Potions.WATER)) {
                restore = DRINK_WATER_BOTTLE;
            } else {
                restore = DRINK_OTHER;
            }
        } else if (stack.is(Items.WATER_BUCKET)) {
            restore = DRINK_WATER_BOTTLE;
        } else if (stack.is(net.minecraft.tags.ItemTags.MEAT)
                || stack.get(net.minecraft.core.component.DataComponents.FOOD) != null) {
            
            restore = 0.05f;
        }

        if (restore > 0f) {
            UUID  id     = sp.getUUID();
            float thirst = getThirst(id);
            setThirst(id, thirst + restore);
        }
    }

    private static void applyEffects(ServerPlayer player, float thirst) {
        if (thirst >= THIRST_HYDRATED) {
            player.removeEffect(MobEffects.SLOWNESS);
            player.removeEffect(MobEffects.MINING_FATIGUE);
            player.removeEffect(MobEffects.WEAKNESS);
            return;
        }

        player.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, INTERVAL + 5, 0, true, false, false));

        if (thirst < THIRST_THIRSTY) {
            
            player.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, INTERVAL + 5, 1, true, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.MINING_FATIGUE,      INTERVAL + 5, 0, true, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,          INTERVAL + 5, 0, true, false, false));
        }

        if (thirst < THIRST_CRITICAL) {
            
            player.hurt(player.damageSources().dryOut(), DEHYDRATION_DMG);
        }
    }

    public static ThirstBand getBand(UUID id) {
        float t = getThirst(id);
        if (t >= THIRST_HYDRATED)  return ThirstBand.HYDRATED;
        if (t >= THIRST_THIRSTY)   return ThirstBand.THIRSTY;
        if (t >= THIRST_CRITICAL)  return ThirstBand.DEHYDRATED;
        return ThirstBand.CRITICAL;
    }

    public enum ThirstBand {
        HYDRATED   ("Hydrated",   0xFF44AAFF),
        THIRSTY    ("Thirsty",    0xFFFFCC44),
        DEHYDRATED ("Dehydrated", 0xFFFF8822),
        CRITICAL   ("Parched",    0xFFFF2200);

        public final String label;
        public final int    color;
        ThirstBand(String label, int color) { this.label = label; this.color = color; }
    }

    private PlayerThirstSystem() {}
}
