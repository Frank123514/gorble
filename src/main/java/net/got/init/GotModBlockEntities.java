package net.got.init;

import net.got.GotMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;

/**
 * Custom block entity types for GoT sign blocks.
 *
 * Standing and wall signs each need their own BlockEntityType so that the
 * BlockEntity.validateBlockState() check passes (it verifies the type's
 * validBlocks set contains the block that spawned the entity).
 *
 * Hanging signs are NOT registered here — HangingSignBlockEntity hardcodes
 * BlockEntityType.HANGING_SIGN internally, so we instead add our hanging sign
 * blocks to that vanilla type's validBlocks set in GotMod.commonSetup (via AT).
 */
public class GotModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> REGISTRY =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, GotMod.MODID);

    private static DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>> sign(
            String name,
            java.util.function.Supplier<net.minecraft.world.level.block.Block> standing,
            java.util.function.Supplier<net.minecraft.world.level.block.Block> wall) {
        return REGISTRY.register(name + "_sign", () ->
                new BlockEntityType<>(SignBlockEntity::new,
                        Set.of(standing.get(), wall.get())));
    }

    // ── Signs ─────────────────────────────────────────────────────────────────

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            WEIRWOOD_SIGN = sign("weirwood", GotModBlocks.WEIRWOOD_SIGN, GotModBlocks.WEIRWOOD_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            ASPEN_SIGN = sign("aspen", GotModBlocks.ASPEN_SIGN, GotModBlocks.ASPEN_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            ALDER_SIGN = sign("alder", GotModBlocks.ALDER_SIGN, GotModBlocks.ALDER_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            PINE_SIGN = sign("pine", GotModBlocks.PINE_SIGN, GotModBlocks.PINE_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            FIR_SIGN = sign("fir", GotModBlocks.FIR_SIGN, GotModBlocks.FIR_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            SENTINAL_SIGN = sign("sentinal", GotModBlocks.SENTINAL_SIGN, GotModBlocks.SENTINAL_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            IRONWOOD_SIGN = sign("ironwood", GotModBlocks.IRONWOOD_SIGN, GotModBlocks.IRONWOOD_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            BEECH_SIGN = sign("beech", GotModBlocks.BEECH_SIGN, GotModBlocks.BEECH_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            SOLDIER_PINE_SIGN = sign("soldier_pine", GotModBlocks.SOLDIER_PINE_SIGN, GotModBlocks.SOLDIER_PINE_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            ASH_SIGN = sign("ash", GotModBlocks.ASH_SIGN, GotModBlocks.ASH_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            HAWTHORN_SIGN = sign("hawthorn", GotModBlocks.HAWTHORN_SIGN, GotModBlocks.HAWTHORN_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            BLACKBARK_SIGN = sign("blackbark", GotModBlocks.BLACKBARK_SIGN, GotModBlocks.BLACKBARK_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            BLOODWOOD_SIGN = sign("bloodwood", GotModBlocks.BLOODWOOD_SIGN, GotModBlocks.BLOODWOOD_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            BLUE_MAHOE_SIGN = sign("blue_mahoe", GotModBlocks.BLUE_MAHOE_SIGN, GotModBlocks.BLUE_MAHOE_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            COTTONWOOD_SIGN = sign("cottonwood", GotModBlocks.COTTONWOOD_SIGN, GotModBlocks.COTTONWOOD_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            BLACK_COTTONWOOD_SIGN = sign("black_cottonwood", GotModBlocks.BLACK_COTTONWOOD_SIGN, GotModBlocks.BLACK_COTTONWOOD_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            CINNAMON_SIGN = sign("cinnamon", GotModBlocks.CINNAMON_SIGN, GotModBlocks.CINNAMON_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            CLOVE_SIGN = sign("clove", GotModBlocks.CLOVE_SIGN, GotModBlocks.CLOVE_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            EBONY_SIGN = sign("ebony", GotModBlocks.EBONY_SIGN, GotModBlocks.EBONY_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            ELM_SIGN = sign("elm", GotModBlocks.ELM_SIGN, GotModBlocks.ELM_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            CEDAR_SIGN = sign("cedar", GotModBlocks.CEDAR_SIGN, GotModBlocks.CEDAR_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            APPLE_SIGN = sign("apple", GotModBlocks.APPLE_SIGN, GotModBlocks.APPLE_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            GOLDENHEART_SIGN = sign("goldenheart", GotModBlocks.GOLDENHEART_SIGN, GotModBlocks.GOLDENHEART_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            LINDEN_SIGN = sign("linden", GotModBlocks.LINDEN_SIGN, GotModBlocks.LINDEN_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            MAHOGANY_SIGN = sign("mahogany", GotModBlocks.MAHOGANY_SIGN, GotModBlocks.MAHOGANY_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            MAPLE_SIGN = sign("maple", GotModBlocks.MAPLE_SIGN, GotModBlocks.MAPLE_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            MYRRH_SIGN = sign("myrrh", GotModBlocks.MYRRH_SIGN, GotModBlocks.MYRRH_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            REDWOOD_SIGN = sign("redwood", GotModBlocks.REDWOOD_SIGN, GotModBlocks.REDWOOD_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            CHESTNUT_SIGN = sign("chestnut", GotModBlocks.CHESTNUT_SIGN, GotModBlocks.CHESTNUT_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            WILLOW_SIGN = sign("willow", GotModBlocks.WILLOW_SIGN, GotModBlocks.WILLOW_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            WORMTREE_SIGN = sign("wormtree", GotModBlocks.WORMTREE_SIGN, GotModBlocks.WORMTREE_WALL_SIGN);
}