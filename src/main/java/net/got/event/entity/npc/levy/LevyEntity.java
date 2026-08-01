package net.got.event.entity.npc.levy;

import net.got.event.entity.npc.data.GotGenderProvider;
import net.got.event.entity.npc.goal.GotHurtByTargetGoal;
import net.got.event.entity.npc.goal.GotMeleeAttackGoal;
import net.got.event.entity.npc.goal.GotNearestTargetGoal;
import net.got.event.entity.npc.smallfolk.SmallfolkEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * Abstract base for all Levy NPC entities — Tier 2.
 *
 * <p>Levies are armed smallfolk conscripted by their house lord.
 * They are always male, carry melee weapons, and defend themselves
 * when attacked but do not seek out combat unprovoked.
 */
public abstract class LevyEntity extends SmallfolkEntity {

    protected LevyEntity(EntityType<? extends LevyEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return SmallfolkEntity.createAttributes()
                .add(Attributes.MAX_HEALTH, 24.0)
                .add(Attributes.ATTACK_DAMAGE, 4.0)
                .add(Attributes.ARMOR, 2.0);
    }

    @Override
    public boolean isCivilian() { return false; }

    @Override
    protected GotGenderProvider getGenderProvider() { return GotGenderProvider.MALE; }

    /** Levies and fighters are military — they never hold civilian occupations. */
    @Override protected boolean shouldHaveOccupation() { return false; }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(2, new GotMeleeAttackGoal(this, 1.3));
        goalSelector.addGoal(4, new OpenDoorGoal(this, true));
        goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0f));
        goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        // Non-aggressive: fights back when attacked but won't seek out targets
        targetSelector.addGoal(1, new GotHurtByTargetGoal(this));
        targetSelector.addGoal(2, new GotNearestTargetGoal<>(this, Monster.class, true));
    }
}