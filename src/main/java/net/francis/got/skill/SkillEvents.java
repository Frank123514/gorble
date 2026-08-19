package net.francis.got.skill;

import net.francis.got.GotMod;
import net.minecraft.tags.BlockTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.AxeItem;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.concurrent.ThreadLocalRandom;

@EventBusSubscriber(modid = GotMod.MODID)
public final class SkillEvents {

    private static final int XP_MINE_ORE      = 8;
    private static final int XP_MINE_STONE    = 2;
    private static final int XP_WOODCUTTING   = 3;
    private static final int XP_FARMING       = 4;
    private static final int XP_SMITHING_HIT  = 2;
    private static final int XP_SMITHING_DONE = 15;
    private static final int XP_COOKING       = 6;

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        BlockState state = event.getState();

        if (state.is(BlockTags.MINEABLE_WITH_PICKAXE)) {
            event.setNewSpeed(event.getNewSpeed() * SkillPerkEffects.breakSpeedMultiplier(player, Skill.MINING));
        } else if (state.is(BlockTags.LOGS)) {
            event.setNewSpeed(event.getNewSpeed() * SkillPerkEffects.breakSpeedMultiplier(player, Skill.WOODCUTTING));
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (!(event.getPlayer() instanceof ServerPlayer player)) return;
        BlockState state = event.getState();
        var heldStack = player.getMainHandItem();
        var heldItem = heldStack.getItem();

        if (state.is(BlockTags.MINEABLE_WITH_PICKAXE) && heldStack.is(ItemTags.PICKAXES)) {
            boolean isOre = state.getBlock() instanceof DropExperienceBlock;
            SkillXpService.grantXp(player, Skill.MINING, isOre ? XP_MINE_ORE : XP_MINE_STONE);
            maybeDropBonus(player, Skill.MINING, state, event);

        } else if (state.is(BlockTags.LOGS) && heldItem instanceof AxeItem) {
            SkillXpService.grantXp(player, Skill.WOODCUTTING, XP_WOODCUTTING);
            maybeDropBonus(player, Skill.WOODCUTTING, state, event);

        } else if (state.getBlock() instanceof CropBlock crop && crop.isMaxAge(state)) {
            SkillXpService.grantXp(player, Skill.FARMING, XP_FARMING);
            maybeDropBonus(player, Skill.FARMING, state, event);
        }
    }

    private static void maybeDropBonus(ServerPlayer player, Skill skill, BlockState state, BlockEvent.BreakEvent event) {
        float chance = SkillPerkEffects.bonusDropChance(player, skill);
        if (chance <= 0 || ThreadLocalRandom.current().nextFloat() >= chance) return;

        var drops = net.minecraft.world.level.block.Block.getDrops(
                state, (ServerLevel) player.level(), event.getPos(), null, player, player.getMainHandItem());
        var pos = event.getPos();
        for (var stack : drops) {
            net.minecraft.world.level.block.Block.popResource((ServerLevel) player.level(), pos, stack.copy());
        }
    }

    @SubscribeEvent
    public static void onIncomingDamage(LivingIncomingDamageEvent event) {
        LivingEntity victim = event.getEntity();
        var source = event.getSource();
        float amount = event.getAmount();
        if (amount <= 0) return;

        if (source.getEntity() instanceof ServerPlayer attacker && victim != attacker) {
            if (source.getDirectEntity() instanceof Projectile) {
                float multiplier = SkillPerkEffects.rangedDamageMultiplier(attacker);
                if (multiplier > 1.0f) {
                    event.setAmount(amount * multiplier);
                    amount = event.getAmount();
                }
                SkillXpService.grantXp(attacker, Skill.ARCHERY, Math.max(1, Math.round(amount)));
            } else if (source.getDirectEntity() == source.getEntity()) {
                SkillXpService.grantXp(attacker, Skill.COMBAT, Math.max(1, Math.round(amount)));
            }
        }

        if (victim instanceof ServerPlayer defender) {
            SkillXpService.grantXp(defender, Skill.DEFENSE, Math.max(1, Math.round(amount / 2f)));
        }
    }

    private SkillEvents() {}
}