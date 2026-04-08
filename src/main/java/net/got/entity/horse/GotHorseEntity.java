package net.got.entity.horse;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

/**
 * GOT custom horse entity — a warhorse for the Game of Thrones mod.
 *
 * <p>Extends vanilla {@link Horse} to inherit all vanilla horse behaviour
 * (taming, inventory, saddling, breeding, riding, jump strength, health).
 * Rendered via the vanilla horse renderer using a custom texture.
 */
public class GotHorseEntity extends Horse {

    public GotHorseEntity(EntityType<? extends Horse> type, Level level) {
        super(type, level);
    }

    @SuppressWarnings("unchecked")
    public static boolean checkSpawnRules(EntityType<GotHorseEntity> type,
                                          ServerLevelAccessor level,
                                          EntitySpawnReason spawnType,
                                          BlockPos pos,
                                          RandomSource random) {
        return net.got.entity.npc.smallfolk.SmallfolkEntity.defaultSpawnRules(
                (EntityType) type, level, spawnType, pos, random);
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level,
                                                  DifficultyInstance difficulty,
                                                  EntitySpawnReason spawnType,
                                                  @Nullable SpawnGroupData groupData) {
        return super.finalizeSpawn(level, difficulty, spawnType, groupData);
    }
}
