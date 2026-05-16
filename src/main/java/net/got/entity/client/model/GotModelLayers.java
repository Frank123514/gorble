package net.got.entity.client.model;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

/**
 * Central registry of all GOT entity {@link ModelLayerLocation}s.
 *
 * <p>Register each in {@code ClientSetup.registerLayerDefinitions}:
 * <pre>{@code
 * event.registerLayerDefinition(GotModelLayers.SMALLFOLK, GotSmallfolkModel::createBodyLayer);
 * event.registerLayerDefinition(GotModelLayers.GOT_HORSE,  GotHorseModel::createBodyLayer);
 * event.registerLayerDefinition(GotModelLayers.GOT_STAG,   GotStagModel::createBodyLayer);
 * }</pre>
 */
public final class GotModelLayers {

    private GotModelLayers() {}

    // ── Smallfolk NPCs ────────────────────────────────────────────────────────

    public static final ModelLayerLocation SMALLFOLK =
            location("smallfolk");

    // ── Animals ───────────────────────────────────────────────────────────────

    public static final ModelLayerLocation GOT_HORSE =
            location("got_horse");

    public static final ModelLayerLocation GOT_STAG =
            location("got_stag");

    // ── Helpers ───────────────────────────────────────────────────────────────

    private static ModelLayerLocation location(String name) {
        return new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("got", name), "main");
    }
}