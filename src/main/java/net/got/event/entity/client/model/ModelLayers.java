package net.got.event.entity.client.model;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;

public final class ModelLayers {

    private ModelLayers() {}

    public static final ModelLayerLocation SMALLFOLK =
            location("smallfolk");

    public static final ModelLayerLocation GOT_STAG =
            location("got_stag");

    public static final ModelLayerLocation GOT_HERON =
            location("got_heron");

    public static final ModelLayerLocation GOT_DIREWOLF =
            location("got_direwolf");

    public static final ModelLayerLocation GOT_CROW =
            location("got_crow");

    public static final ModelLayerLocation GOT_MAMMOTH =
            location("got_mammoth");

    public static final ModelLayerLocation GOT_BROWN_BEAR =
            location("got_brown_bear");

    public static final ModelLayerLocation GOT_GIANT =
            location("got_giant");

    public static final ModelLayerLocation BELLOWS =
            location("bellows");

    private static ModelLayerLocation location(String name) {
        return new ModelLayerLocation(
                Identifier.fromNamespaceAndPath("got", name), "main");
    }
}