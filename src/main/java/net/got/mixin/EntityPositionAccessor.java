package net.got.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = Entity.class, remap = false)
public interface EntityPositionAccessor {

    @Accessor("position")
    Vec3 got$getRawPosition();

    @Accessor("position")
    void got$setRawPosition(Vec3 position);
}