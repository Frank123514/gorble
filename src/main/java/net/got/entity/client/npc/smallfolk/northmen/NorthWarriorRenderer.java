package net.got.entity.client.npc.smallfolk.northmen;

import net.got.GotMod;
import net.got.entity.client.npc.smallfolk.SmallfolkRenderer;
import net.got.entity.npc.smallfolk.northmen.NorthWarriorEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.layer.ItemArmorGeoLayer;

/**
 * GeckoLib renderer for the North Warrior NPC.
 *
 * <h3>Texture convention</h3>
 * Files must live at {@code assets/got/textures/entity/northmen/}:
 * <pre>
 *   north_warrior_male_1.png   north_warrior_male_2.png
 *   north_warrior_female_1.png north_warrior_female_2.png
 * </pre>
 */
public class NorthWarriorRenderer extends SmallfolkRenderer<NorthWarriorEntity> {

    private static final String TEX_ROOT = "textures/entity/northmen/";

    private static final ResourceLocation[] MALE_TEXTURES = {
            ResourceLocation.fromNamespaceAndPath(GotMod.MODID, TEX_ROOT + "north_warrior_male_1.png"),
            ResourceLocation.fromNamespaceAndPath(GotMod.MODID, TEX_ROOT + "north_warrior_male_2.png"),
    };

    private static final ResourceLocation[] FEMALE_TEXTURES = {
            ResourceLocation.fromNamespaceAndPath(GotMod.MODID, TEX_ROOT + "north_warrior_female_1.png"),
            ResourceLocation.fromNamespaceAndPath(GotMod.MODID, TEX_ROOT + "north_warrior_female_2.png"),
    };

    public NorthWarriorRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new NorthWarriorModel());
        addRenderLayer(new ItemArmorGeoLayer<>(this, ctx.getEquipmentRenderer()));
    }

    @Override
    protected ResourceLocation[] getMaleTextures()   { return MALE_TEXTURES;   }

    @Override
    protected ResourceLocation[] getFemaleTextures() { return FEMALE_TEXTURES; }
}