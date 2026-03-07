package net.minecraft.world.entity.animal;

import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.ConversionParams;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SuspiciousEffectHolder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class MushroomCow extends Cow implements Shearable, VariantHolder<MushroomCow.Variant> {
    private static final EntityDataAccessor<String> DATA_TYPE = SynchedEntityData.defineId(MushroomCow.class, EntityDataSerializers.STRING);
    private static final int MUTATE_CHANCE = 1024;
    private static final String TAG_STEW_EFFECTS = "stew_effects";
    @Nullable
    private SuspiciousStewEffects stewEffects;
    /**
     * Stores the UUID of the most recent lightning bolt to strike
     */
    @Nullable
    private UUID lastLightningBoltUUID;

    public MushroomCow(EntityType<? extends MushroomCow> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return level.getBlockState(pos.below()).is(Blocks.MYCELIUM) ? 10.0F : level.getPathfindingCostFromLightLevels(pos);
    }

    public static boolean checkMushroomSpawnRules(
        EntityType<MushroomCow> entityType, LevelAccessor level, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random
    ) {
        return level.getBlockState(pos.below()).is(BlockTags.MOOSHROOMS_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
    }

    @Override
    public void thunderHit(ServerLevel level, LightningBolt lightning) {
        UUID uuid = lightning.getUUID();
        if (!uuid.equals(this.lastLightningBoltUUID)) {
            this.setVariant(this.getVariant() == MushroomCow.Variant.RED ? MushroomCow.Variant.BROWN : MushroomCow.Variant.RED);
            this.lastLightningBoltUUID = uuid;
            this.playSound(SoundEvents.MOOSHROOM_CONVERT, 2.0F, 1.0F);
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_TYPE, MushroomCow.Variant.RED.type);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.is(Items.BOWL) && !this.isBaby()) {
            boolean flag = false;
            ItemStack itemstack2;
            if (this.stewEffects != null) {
                flag = true;
                itemstack2 = new ItemStack(Items.SUSPICIOUS_STEW);
                itemstack2.set(DataComponents.SUSPICIOUS_STEW_EFFECTS, this.stewEffects);
                this.stewEffects = null;
            } else {
                itemstack2 = new ItemStack(Items.MUSHROOM_STEW);
            }

            ItemStack itemstack1 = ItemUtils.createFilledResult(itemstack, player, itemstack2, false);
            player.setItemInHand(hand, itemstack1);
            SoundEvent soundevent;
            if (flag) {
                soundevent = SoundEvents.MOOSHROOM_MILK_SUSPICIOUSLY;
            } else {
                soundevent = SoundEvents.MOOSHROOM_MILK;
            }

            this.playSound(soundevent, 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        } else if (itemstack.is(Items.SHEARS) && this.readyForShearing()) {
            if (this.level() instanceof ServerLevel serverlevel) {
                this.shear(serverlevel, SoundSource.PLAYERS, itemstack);
                this.gameEvent(GameEvent.SHEAR, player);
                itemstack.hurtAndBreak(1, player, getSlotForHand(hand));
            }

            return InteractionResult.SUCCESS;
        } else if (this.getVariant() == MushroomCow.Variant.BROWN && itemstack.is(ItemTags.SMALL_FLOWERS)) {
            if (this.stewEffects != null) {
                for (int i = 0; i < 2; i++) {
                    this.level()
                        .addParticle(
                            ParticleTypes.SMOKE,
                            this.getX() + this.random.nextDouble() / 2.0,
                            this.getY(0.5),
                            this.getZ() + this.random.nextDouble() / 2.0,
                            0.0,
                            this.random.nextDouble() / 5.0,
                            0.0
                        );
                }
            } else {
                Optional<SuspiciousStewEffects> optional = this.getEffectsFromItemStack(itemstack);
                if (optional.isEmpty()) {
                    return InteractionResult.PASS;
                }

                itemstack.consume(1, player);

                for (int j = 0; j < 4; j++) {
                    this.level()
                        .addParticle(
                            ParticleTypes.EFFECT,
                            this.getX() + this.random.nextDouble() / 2.0,
                            this.getY(0.5),
                            this.getZ() + this.random.nextDouble() / 2.0,
                            0.0,
                            this.random.nextDouble() / 5.0,
                            0.0
                        );
                }

                this.stewEffects = optional.get();
                this.playSound(SoundEvents.MOOSHROOM_EAT, 2.0F, 1.0F);
            }

            return InteractionResult.SUCCESS;
        } else {
            return super.mobInteract(player, hand);
        }
    }

    @Override
    public void shear(ServerLevel level, SoundSource soundSource, ItemStack shears) {
        level.playSound(null, this, SoundEvents.MOOSHROOM_SHEAR, soundSource, 1.0F, 1.0F);
        if (!net.neoforged.neoforge.event.EventHooks.canLivingConvert(this, EntityType.COW, (timer) -> {})) return;
        this.convertTo(EntityType.COW, ConversionParams.single(this, false, false), p_381482_ -> {
            net.neoforged.neoforge.event.EventHooks.onLivingConvert(this, p_381482_);
            level.sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(0.5), this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
            this.dropFromShearingLootTable(level, BuiltInLootTables.SHEAR_MOOSHROOM, shears, (p_381483_, p_381484_) -> {
                for (int i = 0; i < p_381484_.getCount(); i++) {
                    // Neo: Change from addFreshEntity to spawnAtLocation to ensure captureDrops can capture this, we also need to unset the default pickup delay from the item
                    // Vanilla uses this.getY(1.0) for the y-level, which is this.getY() + this.getBbHeight() * 1.0, so we pass the BB height as the Y-offset.
                    ItemEntity item = spawnAtLocation(p_381483_, p_381484_.copyWithCount(1), this.getBbHeight());
                    if (item != null) {
                        // addFreshEntity does not incur a pickup delay, while spawnAtLocation sets the default pickup delay.
                        item.setNoPickUpDelay();
                    }
                }
            });
        });
    }

    @Override
    public boolean readyForShearing() {
        return this.isAlive() && !this.isBaby();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Type", this.getVariant().getSerializedName());
        if (this.stewEffects != null) {
            SuspiciousStewEffects.CODEC.encodeStart(NbtOps.INSTANCE, this.stewEffects).ifSuccess(p_297973_ -> compound.put("stew_effects", p_297973_));
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setVariant(MushroomCow.Variant.byName(compound.getString("Type")));
        if (compound.contains("stew_effects", 9)) {
            SuspiciousStewEffects.CODEC.parse(NbtOps.INSTANCE, compound.get("stew_effects")).ifSuccess(p_330058_ -> this.stewEffects = p_330058_);
        }
    }

    private Optional<SuspiciousStewEffects> getEffectsFromItemStack(ItemStack stack) {
        SuspiciousEffectHolder suspiciouseffectholder = SuspiciousEffectHolder.tryGet(stack.getItem());
        return suspiciouseffectholder != null ? Optional.of(suspiciouseffectholder.getSuspiciousEffects()) : Optional.empty();
    }

    public void setVariant(MushroomCow.Variant variant) {
        this.entityData.set(DATA_TYPE, variant.type);
    }

    public MushroomCow.Variant getVariant() {
        return MushroomCow.Variant.byName(this.entityData.get(DATA_TYPE));
    }

    @Nullable
    public MushroomCow getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        MushroomCow mushroomcow = EntityType.MOOSHROOM.create(level, EntitySpawnReason.BREEDING);
        if (mushroomcow != null) {
            mushroomcow.setVariant(this.getOffspringVariant((MushroomCow)otherParent));
        }

        return mushroomcow;
    }

    private MushroomCow.Variant getOffspringVariant(MushroomCow partner) {
        MushroomCow.Variant mushroomcow$variant = this.getVariant();
        MushroomCow.Variant mushroomcow$variant1 = partner.getVariant();
        MushroomCow.Variant mushroomcow$variant2;
        if (mushroomcow$variant == mushroomcow$variant1 && this.random.nextInt(1024) == 0) {
            mushroomcow$variant2 = mushroomcow$variant == MushroomCow.Variant.BROWN ? MushroomCow.Variant.RED : MushroomCow.Variant.BROWN;
        } else {
            mushroomcow$variant2 = this.random.nextBoolean() ? mushroomcow$variant : mushroomcow$variant1;
        }

        return mushroomcow$variant2;
    }

    public static enum Variant implements StringRepresentable {
        RED("red", Blocks.RED_MUSHROOM.defaultBlockState()),
        BROWN("brown", Blocks.BROWN_MUSHROOM.defaultBlockState());

        public static final StringRepresentable.EnumCodec<MushroomCow.Variant> CODEC = StringRepresentable.fromEnum(MushroomCow.Variant::values);
        final String type;
        private final BlockState blockState;

        private Variant(String type, BlockState blockState) {
            this.type = type;
            this.blockState = blockState;
        }

        public BlockState getBlockState() {
            return this.blockState;
        }

        @Override
        public String getSerializedName() {
            return this.type;
        }

        static MushroomCow.Variant byName(String name) {
            return CODEC.byName(name, RED);
        }
    }
}
