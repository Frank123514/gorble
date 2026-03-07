package net.got.block;

import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.state.properties.WoodType;

/**
 * Custom standing sign block for GoT wood types.
 *
 * Extends StandingSignBlock rather than SignBlock directly because SignBlock
 * became abstract in MC 1.21.3 and requires implementing getYRotationDegrees().
 * StandingSignBlock already provides that implementation via the ROTATION
 * block-state property (16 orientations × 22.5°).
 */
public class GotSignBlock extends StandingSignBlock {

    public GotSignBlock(WoodType woodType, Properties properties) {
        super(woodType, properties);
    }
}