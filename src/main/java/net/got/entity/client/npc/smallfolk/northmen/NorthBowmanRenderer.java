package net.got.entity.client.npc.smallfolk.northmen;

import net.got.GotMod;
import net.got.entity.client.npc.smallfolk.SmallfolkRenderer;
import net.got.entity.npc.smallfolk.northmen.NorthBowmanEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.layer.ItemArmorGeoLayer;

/**
 * GeckoLib renderer for the North Bowman NPC.
 *
 * <h3>Texture convention</h3>
 * Files must live at {@code assets/got/textures/entity/northmen/}:
 * <pre>
 *   north_bowman_male_1.png   north_bowman_male_2.png
 *   north_bowman_female_1.png north_bowman_female_2.png
 * </pre>
 */
public class NorthBowmanRenderer extends SmallfolkRenderer<NorthBowmanEntity> {

    private static final String TEX_ROOT = "textures/entity/northmen/";

    private static final ResourceLocation[] MALE_TEXTURES = {
            ResourceLocation.fromNamespaceAndPath(GotMod.MODID, TEX_ROOT + "north_bowman_male_1.png"),
            ResourceLocation.fromNamespaceAndPath(GotMod.MODID, TEX_ROOT + "north_bowman_male_2.png"),
    };

    private static final ResourceLocation[] FEMALE_TEXTURES = {
            ResourceLocation.fromNamespaceAndPath(GotMod.MODID, TEX_ROOT + "north_bowman_female_1.png"),
            ResourceLocation.fromNamespaceAndPath(GotMod.MODID, TEX_ROOT + "north_bowman_female_2.png"),
    };

    public NorthBowmanRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new NorthBowmanModel());
        addRenderLayer(new ItemArmorGeoLayer<>(this, ctx.getEquipmentRenderer()));
    }

    @Override
    protected ResourceLocation[] getMaleTextures()   { return MALE_TEXTURES;   }

    @Override
    protected ResourceLocation[] getFemaleTextures() { return FEMALE_TEXTURES; }
}