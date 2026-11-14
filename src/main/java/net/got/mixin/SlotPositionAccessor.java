package net.got.mixin;

import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

/** Lets the client move a slot (the creative screen puts extra slots on top of the hotbar). */
@Mixin(Slot.class)
public interface SlotPositionAccessor {
    @Mutable @Accessor(value = "x", remap = false) void got$setX(int x);
    @Mutable @Accessor(value = "y", remap = false) void got$setY(int y);
}
