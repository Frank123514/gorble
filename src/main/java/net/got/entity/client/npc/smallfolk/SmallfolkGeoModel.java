package net.got.entity.client.npc.smallfolk;

import net.got.entity.npc.NpcGender;
import net.got.entity.npc.smallfolk.SmallfolkEntity;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

/**
 * Shared GeckoLib model for all Smallfolk tiers.
 *
 * <p>Uses gender-specific geometry files:
 * <ul>
 *   <li>{@code geo/smallfolk_male.geo.json} for male entities.</li>
 *   <li>{@code geo/smallfolk_female.geo.json} for female entities.</li>
 * </ul>
 * Animations are driven by {@code animations/smallfolk.animation.json}.
 *
 * <p>Also implements {@link SmallfolkGeoModelBoneAccessor} so that
 * {@link SmallfolkArmorLayer} can retrieve bone poses after each render
 * without re-searching the bone tree every frame.
 */
public final class SmallfolkGeoModel<T extends SmallfolkEntity>
        extends GeoModel<T>
        implements SmallfolkGeoModelBoneAccessor {

    private static final ResourceLocation MODEL_MALE =
            ResourceLocation.fromNamespaceAndPath("got", "geo/smallfolk_male.geo.json");
    private static final ResourceLocation MODEL_FEMALE =
            ResourceLocation.fromNamespaceAndPath("got", "geo/smallfolk_female.geo.json");
    private static final ResourceLocation ANIMATIONS =
            ResourceLocation.fromNamespaceAndPath("got", "animations/smallfolk.animation.json");

    private final ResourceLocation[] maleTextures;
    private final ResourceLocation[] femaleTextures;

    // ── Cached bones (populated in getBone after each model bake) ─────────────
    private @Nullable GeoBone boneHead;
    private @Nullable GeoBone boneBody;
    private @Nullable GeoBone boneRightArm;
    private @Nullable GeoBone boneLeftArm;
    private @Nullable GeoBone boneRightLeg;
    private @Nullable GeoBone boneLeftLeg;

    public SmallfolkGeoModel(ResourceLocation[] maleTextures, ResourceLocation[] femaleTextures) {
        this.maleTextures = maleTextures;
        this.femaleTextures = femaleTextures;
    }

    @Override
    public ResourceLocation getModelResource(T animatable, GeoRenderer<T> renderer) {
        // Babies use the same adult model — the renderer halves the scale for them.
        // The dedicated child geo had broken UV mapping so the skin didn't fit.
        return animatable.getGender() == NpcGender.FEMALE ? MODEL_FEMALE : MODEL_MALE;
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return ANIMATIONS;
    }

    @Override
    public ResourceLocation getTextureResource(T animatable, GeoRenderer<T> renderer) {
        boolean female = animatable.getGender() == NpcGender.FEMALE;
        if (female) {
            int idx = animatable.getVariant() - animatable.getVariantsPerGender();
            return femaleTextures[Math.abs(idx) % femaleTextures.length];
        }
        return maleTextures[animatable.getVariant() % maleTextures.length];
    }


    /**
     * Called by GeckoLib after it bakes (or re-bakes) the model.
     * We cache references to the six main skeleton bones here so
     * {@link SmallfolkArmorLayer} can read them cheaply each frame.
     */
    @Override
    public void handleAnimations(T animatable, long instanceId,
                                 software.bernie.geckolib.animation.AnimationState<T> animationState,
                                 float partialTick) {
        super.handleAnimations(animatable, instanceId, animationState, partialTick);
        cacheBones(this.getBakedModel(getModelResource(animatable, null)));
    }

    private void cacheBones(@Nullable BakedGeoModel model) {
        if (model == null) return;
        boneHead     = model.getBone("head").orElse(null);
        boneBody     = model.getBone("body").orElse(null);
        boneRightArm = model.getBone("rightArm").orElse(null);
        boneLeftArm  = model.getBone("leftArm").orElse(null);
        boneRightLeg = model.getBone("rightLeg").orElse(null);
        boneLeftLeg  = model.getBone("leftLeg").orElse(null);
    }

    // ── SmallfolkGeoModelBoneAccessor ─────────────────────────────────────────

    @Override public @Nullable GeoBone getBoneHead()     { return boneHead; }
    @Override public @Nullable GeoBone getBoneBody()     { return boneBody; }
    @Override public @Nullable GeoBone getBoneRightArm() { return boneRightArm; }
    @Override public @Nullable GeoBone getBoneLeftArm()  { return boneLeftArm; }
    @Override public @Nullable GeoBone getBoneRightLeg() { return boneRightLeg; }
    @Override public @Nullable GeoBone getBoneLeftLeg()  { return boneLeftLeg; }
}