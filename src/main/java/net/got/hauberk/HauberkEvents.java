package net.got.hauberk;

import net.got.GotMod;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gamerules.GameRules;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

@EventBusSubscriber(modid = GotMod.MODID)
public final class HauberkEvents {

    /** Attribute modifiers aren't saved, so re-apply the armor bonus whenever an entity (re)loads. */
    @SubscribeEvent
    public static void onJoin(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()) return;
        if (event.getEntity() instanceof LivingEntity living) {
            ItemStack stack = HauberkSlotData.get(living);
            if (!stack.isEmpty()) HauberkSlotData.applyArmor(living, stack);
        }
    }

    /** The slot isn't part of the vanilla inventory, so drop it ourselves on death. */
    @SubscribeEvent
    public static void onDrops(LivingDropsEvent event) {
        LivingEntity entity = event.getEntity();
        ItemStack stack = HauberkSlotData.get(entity);
        if (stack.isEmpty()) return;

        if (entity instanceof ServerPlayer player) {
            if (((ServerLevel) player.level()).getGameRules().get(GameRules.KEEP_INVENTORY)) return; // kept on respawn
        } else if (entity.getRandom().nextFloat() >= 0.085F) {
            HauberkSlotData.set(entity, ItemStack.EMPTY);   // mobs only drop worn gear occasionally
            return;
        }

        ItemEntity drop = new ItemEntity(entity.level(), entity.getX(), entity.getY(), entity.getZ(), stack.copy());
        drop.setDefaultPickUpDelay();
        event.getDrops().add(drop);
        HauberkSlotData.set(entity, ItemStack.EMPTY);
    }

    private HauberkEvents() {}
}
