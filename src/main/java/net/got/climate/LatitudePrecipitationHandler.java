package net.got.climate;

/**
 * Removed — snow placement and water-to-ice are now handled by
 * {@link net.got.mixin.ServerLevelPrecipitationMixin}, which redirects
 * vanilla's own per-chunk precipitation tick ({@code ServerLevel.tickChunk})
 * to use latitude temperature instead of biome temperature.
 *
 * <p>The old hand-rolled player-centric loop in this class caused the robotic,
 * sped-up appearance: it ran every tick against an 11×11 chunk ring per player
 * rather than using the global chunk-tick system vanilla already provides.
 */
final class LatitudePrecipitationHandler {
    private LatitudePrecipitationHandler() {}
}