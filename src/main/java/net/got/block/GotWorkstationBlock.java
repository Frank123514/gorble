package net.got.block;

import net.got.entity.npc.data.GotNpcOccupation;
import net.got.entity.npc.smallfolk.SmallfolkEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Comparator;
import java.util.List;

/**
 * A workstation block that grants a specific job to the nearest jobless
 * {@link SmallfolkEntity} within {@value #ASSIGN_RADIUS} blocks when
 * right-clicked by a player.
 *
 * <p>Each occupation that needs a block gets its own registered instance of
 * this class in {@link net.got.init.GotModBlocks}:
 * <pre>{@code
 *   GotModBlocks.SMITHING_FORGE    → GotNpcOccupation.SMITH
 *   GotModBlocks.FARMING_POST      → GotNpcOccupation.FARMER
 *   ...
 * }</pre>
 *
 * <p>{@link GotNpcOccupation#BAKER} reuses the existing {@code OvenBlock}
 * via a separate event handler in {@link net.got.entity.GotEntityEvents}.
 *
 * <p>{@link GotNpcOccupation#FISHERMAN} has no workstation block — a player
 * assigns it by right-clicking a jobless NPC while holding a fishing rod
 * (handled in {@link SmallfolkEntity#mobInteract}).
 */
public class GotWorkstationBlock extends Block {

    /** How far (in blocks) we scan for a jobless NPC. */
    public static final double ASSIGN_RADIUS = 8.0;

    private final GotNpcOccupation occupation;

    public GotWorkstationBlock(GotNpcOccupation occupation, Properties properties) {
        super(properties);
        this.occupation = occupation;
    }

    public GotNpcOccupation getOccupation() {
        return occupation;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level,
                                               BlockPos pos, Player player,
                                               BlockHitResult hit) {
        if (level.isClientSide) return InteractionResult.SUCCESS;
        return tryAssignJob(level, pos, player);
    }

    /**
     * Finds the nearest jobless {@link SmallfolkEntity} within
     * {@value #ASSIGN_RADIUS} blocks of {@code pos} and gives them this
     * workstation's occupation.
     *
     * @return {@link InteractionResult#SUCCESS} if a job was assigned,
     *         {@link InteractionResult#FAIL} if no eligible NPC was found
     */
    public InteractionResult tryAssignJob(Level level, BlockPos pos, Player player) {
        AABB searchBox = new AABB(pos).inflate(ASSIGN_RADIUS);
        List<SmallfolkEntity> candidates = level.getEntitiesOfClass(
                SmallfolkEntity.class, searchBox,
                e -> e.isAlive() && !e.getOccupation().isEmployed());

        if (candidates.isEmpty()) {
            player.displayClientMessage(
                    Component.translatable("got.workstation.no_npc"), true);
            return InteractionResult.FAIL;
        }

        // Assign to the closest jobless NPC
        SmallfolkEntity npc = candidates.stream()
                .min(Comparator.comparingDouble(e -> e.distanceToSqr(
                        pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5)))
                .orElseThrow();

        npc.setOccupation(occupation);

        String npcName = npc.getNpcName().isEmpty()
                ? npc.getType().getDescription().getString()
                : npc.getNpcName();
        player.displayClientMessage(
                Component.translatable("got.workstation.assigned", npcName,
                        Component.translatable("got.occupation." + occupation.id)),
                true);

        return InteractionResult.SUCCESS;
    }
}
