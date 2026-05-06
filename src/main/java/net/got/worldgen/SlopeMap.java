package net.got.worldgen;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

/**
 * Direct port of Middle Earth's {@code SlopeMap}.
 *
 * <p>Holds an ordered list of (maxAngle → block) entries. When the terrain
 * slope at a column is computed (via {@code getTerrainSlope} in
 * {@link GotChunkGenerator}), this map returns the first block whose
 * configured angle is ≥ the actual slope, so flat ground gets grass and
 * steep faces get stone.
 *
 * <p>Entries <em>must</em> be added in strictly ascending angle order.
 */
public class SlopeMap {

    public final ArrayList<SlopeData> slopeDatas;

    public SlopeMap(SlopeMap other) {
        this.slopeDatas = new ArrayList<>(other.slopeDatas);
    }

    public SlopeMap() {
        this.slopeDatas = new ArrayList<>();
    }

    /** Appends an entry. Angles must be strictly ascending. */
    public SlopeMap addSlopeData(float angle, Block block) {
        if (!slopeDatas.isEmpty()) {
            float prev = slopeDatas.get(slopeDatas.size() - 1).angle;
            if (prev >= angle)
                throw new ArithmeticException(
                        "Cannot add slope angle ≤ previous entry: " + prev + " >= " + angle);
        }
        if (angle < 0 || angle > 90)
            throw new ArithmeticException("Slope angle must be in [0,90], got: " + angle);
        slopeDatas.add(new SlopeData(angle, block));
        return this;
    }

    /** Returns the block for the given slope angle (degrees, 0=flat, 90=vertical). */
    public Block getBlockAtAngle(float angle) {
        for (SlopeData d : slopeDatas)
            if (angle <= d.angle) return d.block;
        throw new RuntimeException("Slope " + angle + "° exceeds max configured angle "
                + slopeDatas.get(slopeDatas.size() - 1).angle + "°");
    }

    /** Convenience: returns {@code getBlockAtAngle(angle).defaultBlockState()}. */
    public BlockState getStateAtAngle(float angle) {
        return getBlockAtAngle(angle).defaultBlockState();
    }

    public static final class SlopeData {
        public final float angle;
        public final Block block;
        SlopeData(float angle, Block block) { this.angle = angle; this.block = block; }
    }
}