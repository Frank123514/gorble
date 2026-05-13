package net.got.entity.stag;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;


/**
 * GOT Stag — a great red deer stag of the Westerosi forests.
 *
 * <p>Uses the custom stag geo model with antlers and a deer-like silhouette.
 * Extends vanilla {@link Horse} to reuse all horse behaviour (taming,
 * saddling, riding, breeding, health) while being rendered via GeckoLib
 * with its own model and the horse animation set remapped to stag bone names.
 *
 * <p>Animation states (same logic as the warhorse, using stag-prefixed clips):
 * <ul>
 *   <li>{@code idle}    — subtle breathing bob.</li>
 *   <li>{@code walk}    — 4-beat walk gait.</li>
 *   <li>{@code run}     — bounding gallop.</li>
 *   <li>{@code rear}    — rearing when {@link #isStanding()}.</li>
 *   <li>{@code swim}    — paddling motion in water.</li>
 *   <li>{@code eat}     — grazing head-dip.</li>
 *   <li>{@code tail_wag}— tail flick when tamed and idle.</li>
 * </ul>
 */
public class GotStagEntity extends Horse {




    // ── Attributes ────────────────────────────────────────────────────────────

    /**
     * Called by {@link net.got.entity.GotEntityEvents} during
     * {@code EntityAttributeCreationEvent} to register the stag's attribute set.
     * Stags are fast and nimble but not quite as tough as the warhorse.
     */
    public static AttributeSupplier.Builder createAttributes() {
        return AbstractHorse.createBaseHorseAttributes()
                .add(Attributes.MAX_HEALTH, 18.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.JUMP_STRENGTH, 0.65);
    }


    public GotStagEntity(EntityType<? extends Horse> type, Level level) {
        super(type, level);
    }


    // ── Spawn rules ───────────────────────────────────────────────────────────

    @SuppressWarnings("unchecked")
    public static boolean checkSpawnRules(EntityType<GotStagEntity> type,
                                          ServerLevelAccessor level,
                                          EntitySpawnReason spawnType,
                                          BlockPos pos,
                                          RandomSource random) {
        return net.got.entity.npc.smallfolk.SmallfolkEntity.defaultSpawnRules(
                (EntityType) type, level, spawnType, pos, random);
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level,
                                                  DifficultyInstance difficulty,
                                                  EntitySpawnReason spawnType,
                                                  @Nullable SpawnGroupData spawnGroupData) {
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }
}