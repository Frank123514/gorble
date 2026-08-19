package net.francis.got.mixin;

import net.francis.got.client.animation.player.SwingStyle;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LivingEntity.class, remap = false)
public abstract class SwingDurationMixin {

    private static final int SWORD_SWING_TICKS = 32;
    
    private static final int GREATSWORD_SWING_TICKS = 30;
    
    private static final int AXE_SWING_TICKS = 31;
    
    private static final int TRIDENT_SWING_TICKS = 10;
    private static final int TOOL_SWING_TICKS = 8;
    private static final int GENERIC_SWING_TICKS = 7;

    @Inject(method = "getCurrentSwingDuration", at = @At("RETURN"), cancellable = true, remap = false)
    private void got_stretchSwordSwing(CallbackInfoReturnable<Integer> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (!(self instanceof Player player)) {
            return;
        }
        ItemStack mainHand = player.getMainHandItem();
        SwingStyle style = SwingStyle.fromItem(mainHand);
        switch (style) {
            case SWORD -> cir.setReturnValue(SWORD_SWING_TICKS);
            case GREATSWORD -> cir.setReturnValue(GREATSWORD_SWING_TICKS);
            case AXE -> cir.setReturnValue(AXE_SWING_TICKS);
            case TRIDENT -> cir.setReturnValue(TRIDENT_SWING_TICKS);
            case TOOL -> cir.setReturnValue(TOOL_SWING_TICKS);
            case GENERIC -> cir.setReturnValue(GENERIC_SWING_TICKS);
            default -> {
                
            }
        }
    }
}