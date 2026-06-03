package net.got.entity.client.model;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

/**
 * Central registry of all GOT entity {@link ModelLayerLocation}s.
 *
 * <p>Register each in {@code ClientSetup.registerLayerDefinitions}:
 * <pre>{@code
 * event.registerLayerDefinition(GotModelLayers.SMALLFOLK, GotSmallfolkModel::createBodyLayer);
 * event.registerLayerDefinition(GotModelLayers.GOT_STAG,  GotStagModel::createBodyLayer);
 * }</pre>
 *
 * <p>And register the renderer in {@code ClientSetup.registerRenderers} (or equivalent):
 * <pre>{@code
 * event.registerEntityRenderer(GotEntityTypes.STAG.get(), GotStagRenderer::new);
 * }</pre>
 */
public final class GotModelLayers {

    private GotModelLayers() {}

    // ── Smallfolk NPCs ────────────────────────────────────────────────────────

    public static final ModelLayerLocation SMALLFOLK =
            location("smallfolk");

    // ── Animals ───────────────────────────────────────────────────────────────

    /**
     * Layer location for the custom deer model ({@code GotStagModel}).
     * Matches {@code GotStagModel#LAYER_LOCATION} — both resolve to
     * {@code got:got_stag#main}.
     */
    public static final ModelLayerLocation GOT_STAG =
            location("got_stag");

    /**
     * Layer location for the custom heron model ({@code GotHeronModel}).
     * Resolves to {@code got:got_heron#main}.
     */
    public static final ModelLayerLocation GOT_HERON =
            location("got_heron");

    /**
     * Layer location for the custom direwolf model ({@code GotDirewolfModel}).
     * Resolves to {@code got:got_direwolf#main}.
     */
    public static final ModelLayerLocation GOT_DIREWOLF =
            location("got_direwolf");

    /**
     * Layer location for the custom crow model ({@code GotCrowModel}).
     * Resolves to {@code got:got_crow#main}.
     */
    public static final ModelLayerLocation GOT_CROW =
            location("got_crow");

    /**
     * Layer location for the custom mammoth model ({@code GotMammothModel}).
     * Resolves to {@code got:got_mammoth#main}.
     */
    public static final ModelLayerLocation GOT_MAMMOTH =
            location("got_mammoth");

    // ── Helpers ───────────────────────────────────────────────────────────────

    private static ModelLayerLocation location(String name) {
        return new ModelLayerLocation(
                ResourceLocation.fromNamespaceAndPath("got", name), "main");
    }
}