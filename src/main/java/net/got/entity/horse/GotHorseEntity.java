package net.got.entity.horse;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;


/**
 * GOT custom horse entity.
 *
 * <p>Coat and markings are stored in our own synced data fields
 * ({@link #DATA_COAT} and {@link #DATA_MARKINGS_IDX}) and exposed through
 * {@link #getCoatVariant()} / {@link #getMarkingsIndex()} so the renderer
 * can pick the right texture layers without conflicting with any vanilla
 * Horse interface methods.
 */
public class GotHorseEntity extends Horse {

    public static final int COAT_COUNT     = 6;   // black, brown, chestnut, creamy, darkbrown, gray
    public static final int MARKINGS_COUNT = 5;   // 0 = none, 1-4 = patterns

    // Use unique DATA_IDs so they never clash with vanilla Horse's own fields.
    private static final EntityDataAccessor<Integer> DATA_COAT =
            SynchedEntityData.defineId(GotHorseEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_MARKINGS_IDX =
            SynchedEntityData.defineId(GotHorseEntity.class, EntityDataSerializers.INT);




    public GotHorseEntity(EntityType<? extends Horse> type, Level level) {
        super(type, level);
    }

    // ── Custom synced coat / markings ─────────────────────────────────────────

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_COAT, 0);
        builder.define(DATA_MARKINGS_IDX, 0);
    }

    /** 0-5 index into the GOT coat textures. */
    public int getCoatVariant()   { return this.entityData.get(DATA_COAT); }
    /** 0 = no markings; 1-4 = markings overlay index. */
    public int getMarkingsIndex() { return this.entityData.get(DATA_MARKINGS_IDX); }

    public void setCoatVariant(int coat)      { this.entityData.set(DATA_COAT, coat); }
    public void setMarkingsIndex(int markings){ this.entityData.set(DATA_MARKINGS_IDX, markings); }

    // ── NBT persistence ───────────────────────────────────────────────────────

    @Override
    public void addAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("GotCoat", getCoatVariant());
        tag.putInt("GotMarkings", getMarkingsIndex());
    }

    @Override
    public void readAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("GotCoat"))     setCoatVariant(tag.getInt("GotCoat"));
        if (tag.contains("GotMarkings")) setMarkingsIndex(tag.getInt("GotMarkings"));
    }


    // ── Spawn ─────────────────────────────────────────────────────────────────

    @SuppressWarnings("unchecked")
    public static boolean checkSpawnRules(EntityType<GotHorseEntity> type,
                                          ServerLevelAccessor level,
                                          EntitySpawnReason spawnType,
                                          BlockPos pos, RandomSource random) {
        return net.got.entity.npc.smallfolk.SmallfolkEntity.defaultSpawnRules(
                (EntityType) type, level, spawnType, pos, random);
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level,
                                                  DifficultyInstance difficulty,
                                                  EntitySpawnReason spawnType,
                                                  @Nullable SpawnGroupData groupData) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, spawnType, groupData);
        if (spawnType != EntitySpawnReason.LOAD && spawnType != EntitySpawnReason.DIMENSION_TRAVEL) {
            setCoatVariant(this.random.nextInt(COAT_COUNT));
            setMarkingsIndex(this.random.nextInt(MARKINGS_COUNT));
        }
        return result;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return (AgeableMob) getType().create(level, EntitySpawnReason.BREEDING);
    }
}