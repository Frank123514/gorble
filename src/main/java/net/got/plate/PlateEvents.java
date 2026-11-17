package net.got.plate;

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
public final class PlateEvents {

    /** Attribute modifiers aren't saved, so re-apply the armor bonuses whenever an entity (re)loads. */
    @SubscribeEvent
    public static void onJoin(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()) return;
        if (event.getEntity() instanceof LivingEntity living) {
            for (PlatePiece piece : PlatePiece.values()) {
                ItemStack stack = PlateSlotData.get(living, piece);
                if (!stack.isEmpty()) PlateSlotData.applyArmor(living, piece, stack);
            }
        }
    }

    /** The slots aren't part of the vanilla inventory, so drop them ourselves on death. */
    @SubscribeEvent
    public static void onDrops(LivingDropsEvent event) {
        LivingEntity entity = event.getEntity();

        boolean player = entity instanceof ServerPlayer;
        if (entity instanceof ServerPlayer sp
                && ((ServerLevel) sp.level()).getGameRules().get(GameRules.KEEP_INVENTORY)) return; // kept on respawn

        for (PlatePiece piece : PlatePiece.values()) {
            ItemStack stack = PlateSlotData.get(entity, piece);
            if (stack.isEmpty()) continue;

            if (!player && entity.getRandom().nextFloat() >= 0.085F) {
                PlateSlotData.set(entity, piece, ItemStack.EMPTY);   // mobs only drop worn gear occasionally
                continue;
            }
            ItemEntity drop = new ItemEntity(entity.level(), entity.getX(), entity.getY(), entity.getZ(), stack.copy());
            drop.setDefaultPickUpDelay();
            event.getDrops().add(drop);
            PlateSlotData.set(entity, piece, ItemStack.EMPTY);
        }
    }

    private PlateEvents() {}
}
