package net.got.mixin;

import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

/** Lets the creative-inventory fix move the gambeson slot's wrapper. */
@Mixin(Slot.class)
public interface SlotAccessor {
    @Mutable @Accessor(value = "x", remap = false) void got$setX(int x);
    @Mutable @Accessor(value = "y", remap = false) void got$setY(int y);
}
