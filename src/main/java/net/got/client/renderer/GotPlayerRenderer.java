package net.got.client.renderer;

import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;

/**
 * GOT player renderer.
 *
 * <p>Swaps in GotPlayerModel so the mixin targets the right class hierarchy,
 * but does NOT manage animation state — that is handled entirely by
 * PlayerRendererMixin injecting into PlayerModel.setupAnim(). All the old
 * per-renderer animation tracking (animStartTick, syncAnimatorState, etc.)
 * has been removed: it was dead code that also caused double-application of
 * animations when the fields it wrote to still existed on GotPlayerModel.
 */
public class GotPlayerRenderer extends PlayerRenderer {

    public GotPlayerRenderer(EntityRendererProvider.Context context, boolean slim) {
        super(context, slim);
        EntityModelSet models = context.getModelSet();
        this.model = new GotPlayerModel(
                models.bakeLayer(slim ? ModelLayers.PLAYER_SLIM : ModelLayers.PLAYER),
                slim
        );
    }
}