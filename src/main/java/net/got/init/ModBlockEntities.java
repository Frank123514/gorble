package net.got.init;

import net.got.GotMod;
import net.got.block.OvenBlockEntity;
import net.got.block.BellowsBlockEntity;
import net.got.block.ForgeBlockEntity;
import net.got.block.SmithingAnvilBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;

public class ModBlockEntities {

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

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<OvenBlockEntity>> OVEN =
            REGISTRY.register("oven", () ->
                    new BlockEntityType<>(OvenBlockEntity::new,
                            Set.of(ModBlocks.OVEN.get())));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ForgeBlockEntity>> FORGE =
            REGISTRY.register("forge", () ->
                    new BlockEntityType<>(ForgeBlockEntity::new,
                            Set.of(ModBlocks.FORGE.get())));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SmithingAnvilBlockEntity>> SMITHING_ANVIL =
            REGISTRY.register("smithing_anvil", () ->
                    new BlockEntityType<>(SmithingAnvilBlockEntity::new,
                            Set.of(ModBlocks.SMITHING_ANVIL.get())));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BellowsBlockEntity>> BELLOWS =
            REGISTRY.register("bellows", () ->
                    new BlockEntityType<>(BellowsBlockEntity::new,
                            Set.of(ModBlocks.BELLOWS.get())));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            WEIRWOOD_SIGN = sign("weirwood", ModBlocks.WEIRWOOD_SIGN, ModBlocks.WEIRWOOD_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            ASPEN_SIGN = sign("aspen", ModBlocks.ASPEN_SIGN, ModBlocks.ASPEN_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            ALDER_SIGN = sign("alder", ModBlocks.ALDER_SIGN, ModBlocks.ALDER_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            PINE_SIGN = sign("pine", ModBlocks.PINE_SIGN, ModBlocks.PINE_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            FIR_SIGN = sign("fir", ModBlocks.FIR_SIGN, ModBlocks.FIR_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            SENTINAL_SIGN = sign("sentinal", ModBlocks.SENTINAL_SIGN, ModBlocks.SENTINAL_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            IRONWOOD_SIGN = sign("ironwood", ModBlocks.IRONWOOD_SIGN, ModBlocks.IRONWOOD_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            BEECH_SIGN = sign("beech", ModBlocks.BEECH_SIGN, ModBlocks.BEECH_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            SOLDIER_PINE_SIGN = sign("soldier_pine", ModBlocks.SOLDIER_PINE_SIGN, ModBlocks.SOLDIER_PINE_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            ASH_SIGN = sign("ash", ModBlocks.ASH_SIGN, ModBlocks.ASH_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            HAWTHORN_SIGN = sign("hawthorn", ModBlocks.HAWTHORN_SIGN, ModBlocks.HAWTHORN_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            BLACKBARK_SIGN = sign("blackbark", ModBlocks.BLACKBARK_SIGN, ModBlocks.BLACKBARK_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            BLOODWOOD_SIGN = sign("bloodwood", ModBlocks.BLOODWOOD_SIGN, ModBlocks.BLOODWOOD_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            BLUE_MAHOE_SIGN = sign("blue_mahoe", ModBlocks.BLUE_MAHOE_SIGN, ModBlocks.BLUE_MAHOE_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            COTTONWOOD_SIGN = sign("cottonwood", ModBlocks.COTTONWOOD_SIGN, ModBlocks.COTTONWOOD_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            BLACK_COTTONWOOD_SIGN = sign("black_cottonwood", ModBlocks.BLACK_COTTONWOOD_SIGN, ModBlocks.BLACK_COTTONWOOD_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            CINNAMON_SIGN = sign("cinnamon", ModBlocks.CINNAMON_SIGN, ModBlocks.CINNAMON_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            CLOVE_SIGN = sign("clove", ModBlocks.CLOVE_SIGN, ModBlocks.CLOVE_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            EBONY_SIGN = sign("ebony", ModBlocks.EBONY_SIGN, ModBlocks.EBONY_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            ELM_SIGN = sign("elm", ModBlocks.ELM_SIGN, ModBlocks.ELM_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            CEDAR_SIGN = sign("cedar", ModBlocks.CEDAR_SIGN, ModBlocks.CEDAR_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            APPLE_SIGN = sign("apple", ModBlocks.APPLE_SIGN, ModBlocks.APPLE_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            GOLDENHEART_SIGN = sign("goldenheart", ModBlocks.GOLDENHEART_SIGN, ModBlocks.GOLDENHEART_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            LINDEN_SIGN = sign("linden", ModBlocks.LINDEN_SIGN, ModBlocks.LINDEN_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            MAHOGANY_SIGN = sign("mahogany", ModBlocks.MAHOGANY_SIGN, ModBlocks.MAHOGANY_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            MAPLE_SIGN = sign("maple", ModBlocks.MAPLE_SIGN, ModBlocks.MAPLE_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            MYRRH_SIGN = sign("myrrh", ModBlocks.MYRRH_SIGN, ModBlocks.MYRRH_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            REDWOOD_SIGN = sign("redwood", ModBlocks.REDWOOD_SIGN, ModBlocks.REDWOOD_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            CHESTNUT_SIGN = sign("chestnut", ModBlocks.CHESTNUT_SIGN, ModBlocks.CHESTNUT_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            WILLOW_SIGN = sign("willow", ModBlocks.WILLOW_SIGN, ModBlocks.WILLOW_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            WORMTREE_SIGN = sign("wormtree", ModBlocks.WORMTREE_SIGN, ModBlocks.WORMTREE_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            NIGHTWOOD_SIGN = sign("nightwood", ModBlocks.NIGHTWOOD_SIGN, ModBlocks.NIGHTWOOD_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            PURPLEHEART_SIGN = sign("purpleheart", ModBlocks.PURPLEHEART_SIGN, ModBlocks.PURPLEHEART_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            TIGERWOOD_SIGN = sign("tigerwood", ModBlocks.TIGERWOOD_SIGN, ModBlocks.TIGERWOOD_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            SANDALWOOD_SIGN = sign("sandalwood", ModBlocks.SANDALWOOD_SIGN, ModBlocks.SANDALWOOD_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            SANDBEGGAR_SIGN = sign("sandbeggar", ModBlocks.SANDBEGGAR_SIGN, ModBlocks.SANDBEGGAR_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            APRICOT_SIGN = sign("apricot", ModBlocks.APRICOT_SIGN, ModBlocks.APRICOT_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            BLACKTHORN_SIGN = sign("blackthorn", ModBlocks.BLACKTHORN_SIGN, ModBlocks.BLACKTHORN_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            RED_CHERRY_SIGN = sign("red_cherry", ModBlocks.RED_CHERRY_SIGN, ModBlocks.RED_CHERRY_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            WHITE_CHERRY_SIGN = sign("white_cherry", ModBlocks.WHITE_CHERRY_SIGN, ModBlocks.WHITE_CHERRY_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            BLACK_CHERRY_SIGN = sign("black_cherry", ModBlocks.BLACK_CHERRY_SIGN, ModBlocks.BLACK_CHERRY_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            CRABAPPLE_SIGN = sign("crabapple", ModBlocks.CRABAPPLE_SIGN, ModBlocks.CRABAPPLE_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            DATE_PALM_SIGN = sign("date_palm", ModBlocks.DATE_PALM_SIGN, ModBlocks.DATE_PALM_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            FIG_SIGN = sign("fig", ModBlocks.FIG_SIGN, ModBlocks.FIG_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            LEMON_SIGN = sign("lemon", ModBlocks.LEMON_SIGN, ModBlocks.LEMON_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            LIME_SIGN = sign("lime", ModBlocks.LIME_SIGN, ModBlocks.LIME_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            OLIVE_SIGN = sign("olive", ModBlocks.OLIVE_SIGN, ModBlocks.OLIVE_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            ORANGE_SIGN = sign("orange", ModBlocks.ORANGE_SIGN, ModBlocks.ORANGE_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            PEACH_SIGN = sign("peach", ModBlocks.PEACH_SIGN, ModBlocks.PEACH_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            PEAR_SIGN = sign("pear", ModBlocks.PEAR_SIGN, ModBlocks.PEAR_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            PERSIMMON_SIGN = sign("persimmon", ModBlocks.PERSIMMON_SIGN, ModBlocks.PERSIMMON_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            PINK_IVORY_SIGN = sign("pink_ivory", ModBlocks.PINK_IVORY_SIGN, ModBlocks.PINK_IVORY_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            PLUM_SIGN = sign("plum", ModBlocks.PLUM_SIGN, ModBlocks.PLUM_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            POMEGRANATE_SIGN = sign("pomegranate", ModBlocks.POMEGRANATE_SIGN, ModBlocks.POMEGRANATE_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            PRUNE_SIGN = sign("prune", ModBlocks.PRUNE_SIGN, ModBlocks.PRUNE_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            ALMOND_SIGN = sign("almond", ModBlocks.ALMOND_SIGN, ModBlocks.ALMOND_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            NUTMEG_SIGN = sign("nutmeg", ModBlocks.NUTMEG_SIGN, ModBlocks.NUTMEG_WALL_SIGN);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SignBlockEntity>>
            HEMLOCK_SIGN = sign("hemlock", ModBlocks.HEMLOCK_SIGN, ModBlocks.HEMLOCK_WALL_SIGN);

}