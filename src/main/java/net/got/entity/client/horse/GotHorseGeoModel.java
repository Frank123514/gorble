package net.got.entity.client.horse;

import net.got.entity.horse.GotHorseEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Items;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class GotHorseGeoModel extends GeoModel<GotHorseEntity> {

    private static final ResourceLocation MODEL =
            ResourceLocation.fromNamespaceAndPath("got", "geo/got_horse.geo.json");
    private static final ResourceLocation ANIMATIONS =
            ResourceLocation.fromNamespaceAndPath("got", "animations/got_horse.animation.json");

    /** Indexed by {@link GotHorseEntity#getCoatVariant()} (0-5). */
    private static final ResourceLocation[] COAT_TEXTURES = {
        ResourceLocation.fromNamespaceAndPath("got", "textures/entity/horse/horse_black.png"),
        ResourceLocation.fromNamespaceAndPath("got", "textures/entity/horse/horse_brown.png"),
        ResourceLocation.fromNamespaceAndPath("got", "textures/entity/horse/horse_chestnut.png"),
        ResourceLocation.fromNamespaceAndPath("got", "textures/entity/horse/horse_creamy.png"),
        ResourceLocation.fromNamespaceAndPath("got", "textures/entity/horse/horse_darkbrown.png"),
        ResourceLocation.fromNamespaceAndPath("got", "textures/entity/horse/horse_gray.png"),
    };

    private static final String[] SADDLE_BONES = {
        "saddle", "saddleb", "saddlec", "saddlel", "saddlel2", "saddler", "saddler2",
        "headsaddle", "saddlemouthl", "saddlemouthr", "saddlemouthline", "saddlemouthliner"
    };
    private static final String[] ARMOR_BONES = { "bag1", "bag2" };
    private static final String[] MULE_EARS   = { "muleearl", "muleearr" };

    @Override
    public ResourceLocation getModelResource(GotHorseEntity animatable, GeoRenderer<GotHorseEntity> renderer) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(GotHorseEntity animatable, GeoRenderer<GotHorseEntity> renderer) {
        int id = animatable.getCoatVariant();
        if (id < 0 || id >= COAT_TEXTURES.length) id = 0;
        return COAT_TEXTURES[id];
    }

    @Override
    public ResourceLocation getAnimationResource(GotHorseEntity animatable) {
        return ANIMATIONS;
    }

    @Override
    public void handleAnimations(GotHorseEntity animatable, long instanceId,
                                 AnimationState<GotHorseEntity> animationState, float partialTick) {
        super.handleAnimations(animatable, instanceId, animationState, partialTick);

        BakedGeoModel baked = getBakedModel(MODEL);
        if (baked == null) return;

        boolean saddled  = animatable.isSaddled();
        boolean hasArmor = !animatable.getItemBySlot(EquipmentSlot.BODY).isEmpty()
                        && animatable.getItemBySlot(EquipmentSlot.BODY).getItem() != Items.AIR;

        for (String name : SADDLE_BONES) baked.getBone(name).ifPresent(b -> b.setHidden(!saddled));
        for (String name : ARMOR_BONES)  baked.getBone(name).ifPresent(b -> b.setHidden(!hasArmor));
        for (String name : MULE_EARS)    baked.getBone(name).ifPresent(b -> b.setHidden(true));
    }
}
