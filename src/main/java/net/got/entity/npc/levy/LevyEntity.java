package net.got.entity.npc.levy;

import net.got.entity.npc.NpcGender;
import net.got.entity.npc.smallfolk.SmallfolkEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * Abstract base for all Levy NPC entities — Tier 2.
 *
 * <p>Levies are armed smallfolk conscripted by their house lord. Unlike their
 * civilian counterparts they <em>always</em> carry a weapon and will actively
 * pursue threats rather than fleeing. They sit between the unarmed smallfolk
 * (Tier 1) and the skilled fighters (Tier 3) in combat capability.
 *
 * <h3>Tier overview</h3>
 * <pre>
 *   Tier 1 — Smallfolk   : unarmed civilians, flee monsters, fight back only if hurt
 *   Tier 2 — Levy        : THIS CLASS — armed conscripts, stand their ground
 *   Tier 3 — Skilled     : see {@code net.got.entity.npc.fighter}
 * </pre>
 *
 * <h3>Adding a new house levy</h3>
 * Create a sub-package under {@code net.got.entity.npc.levy.<house>}, extend
 * this class, implement {@link SmallfolkEntity#getVariantCount()},
 * {@link SmallfolkEntity#registerControllers}, and override
 * {@link #populateDefaultEquipmentSlots} for house-specific gear.
 */
public abstract class LevyEntity extends SmallfolkEntity {

    protected LevyEntity(EntityType<? extends LevyEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected NpcGender selectGender() { return NpcGender.MALE; }

    // ── Goals ─────────────────────────────────────────────────────────────────
    // Levies stand their ground — they do NOT flee and they actively target
    // nearby monsters, not just retaliate when hurt.

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        // Levies retaliate when hurt and also hunt nearby monsters proactively.
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Monster.class, true));
        this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<>(this, false));
    }
}
