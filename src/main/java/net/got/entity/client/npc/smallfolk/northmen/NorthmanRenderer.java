package net.got.entity.client.npc.smallfolk.northmen;

import net.got.GotMod;
import net.got.entity.client.npc.smallfolk.SmallfolkRenderer;
import net.got.entity.npc.smallfolk.northmen.NorthmanEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.layer.ItemArmorGeoLayer;

/**
 * GeckoLib renderer for the Northman civilian NPC.
 *
 * <h3>Bone names — match your Blockbench geometry exactly</h3>
 * GeckoLib's {@link ItemArmorGeoLayer} automatically maps armor items to bones
 * by slot name. Name your Blockbench bones:
 * <pre>
 *   armorHead, armorChest, armorLeftArm, armorRightArm,
 *   armorLeftLeg, armorRightLeg, armorLeftBoot, armorRightBoot
 * </pre>
 * (exact naming convention depends on your GeckoLib version — check the
 * ItemArmorGeoLayer source if armor doesn't appear.)
 *
 * <h3>Texture convention</h3>
 * Files must live at {@code assets/got/textures/entity/northmen/}:
 * <pre>
 *   northman_male_1.png   northman_male_2.png
 *   northman_male_3.png   northman_male_4.png
 *   northman_female_1.png northman_female_2.png
 *   northman_female_3.png northman_female_4.png
 * </pre>
 */
public class NorthmanRenderer extends SmallfolkRenderer<NorthmanEntity> {

    private static final String TEX_ROOT = "textures/entity/northmen/";

    private static final ResourceLocation[] MALE_TEXTURES = {
            ResourceLocation.fromNamespaceAndPath(GotMod.MODID, TEX_ROOT + "northman_male_1.png"),
            ResourceLocation.fromNamespaceAndPath(GotMod.MODID, TEX_ROOT + "northman_male_2.png"),
            ResourceLocation.fromNamespaceAndPath(GotMod.MODID, TEX_ROOT + "northman_male_3.png"),
            ResourceLocation.fromNamespaceAndPath(GotMod.MODID, TEX_ROOT + "northman_male_4.png"),
    };

    private static final ResourceLocation[] FEMALE_TEXTURES = {
            ResourceLocation.fromNamespaceAndPath(GotMod.MODID, TEX_ROOT + "northman_female_1.png"),
            ResourceLocation.fromNamespaceAndPath(GotMod.MODID, TEX_ROOT + "northman_female_2.png"),
            ResourceLocation.fromNamespaceAndPath(GotMod.MODID, TEX_ROOT + "northman_female_3.png"),
            ResourceLocation.fromNamespaceAndPath(GotMod.MODID, TEX_ROOT + "northman_female_4.png"),
    };

    public NorthmanRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new NorthmanModel());
        // Armor rendering — auto-maps to bones by slot name convention.
        // Pass the equipment renderer from the context, not the context itself.
        addRenderLayer(new ItemArmorGeoLayer<>(this, ctx.getEquipmentRenderer()));
    }

    @Override
    protected ResourceLocation[] getMaleTextures()   { return MALE_TEXTURES;   }

    @Override
    protected ResourceLocation[] getFemaleTextures() { return FEMALE_TEXTURES; }
}