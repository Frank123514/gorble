package net.got.entity.client.npc.smallfolk;

import net.got.entity.npc.NpcGender;
import net.got.entity.npc.smallfolk.SmallfolkEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/**
 * Abstract GeckoLib renderer shared by all smallfolk NPC sub-types.
 *
 * <p>Handles texture routing per-variant via
 * {@link #getMaleTextures()} / {@link #getFemaleTextures()}.
 *
 * <p>Name tags are handled automatically by vanilla because every
 * smallfolk entity calls {@code setCustomName()} and
 * {@code setCustomNameVisible(true)} inside {@code finalizeSpawn()}.
 *
 * @param <T> the concrete smallfolk entity type
 */
public abstract class SmallfolkRenderer<T extends SmallfolkEntity>
        extends GeoEntityRenderer<T> {

    protected SmallfolkRenderer(EntityRendererProvider.Context ctx, GeoModel<T> model) {
        super(ctx, model);
        this.shadowRadius = 0.5f;
    }

    // ── Texture routing ────────────────────────────────────────────────────────

    protected abstract ResourceLocation[] getMaleTextures();
    protected abstract ResourceLocation[] getFemaleTextures();

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        if (entity.getGender() == NpcGender.FEMALE) {
            int idx = entity.getVariant() - entity.getVariantsPerGender();
            ResourceLocation[] arr = getFemaleTextures();
            return arr[Math.abs(idx) % arr.length];
        }
        ResourceLocation[] arr = getMaleTextures();
        return arr[entity.getVariant() % arr.length];
    }
}