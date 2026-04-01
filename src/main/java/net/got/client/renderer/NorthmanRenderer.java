package net.got.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.got.entity.NorthmanEntity;
import net.got.entity.NpcGender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

/**
 * Renderer for the Northman smallfolk NPC.
 *
 * <p><b>Model sizes</b>: males use {@link ModelLayers#PLAYER} (Steve — 4-px arms),
 * females use {@link ModelLayers#PLAYER_SLIM} (Alex — 3-px arms).
 * Both models are baked at construction; the active one is swapped in
 * {@link #extractRenderState} based on the entity's gender.
 *
 * <p><b>Name tags</b>: the NPC's assigned name fades in when a player is
 * within {@value #NAME_VISIBLE_RANGE} blocks.
 */
public class NorthmanRenderer
        extends HumanoidMobRenderer<NorthmanEntity, NorthmanRenderer.State, HumanoidModel<NorthmanRenderer.State>> {

    private static final double NAME_VISIBLE_RANGE = 8.0;

    public static class State extends HumanoidRenderState {
        public int       variant     = 0;
        public NpcGender gender      = NpcGender.MALE;
        public String    npcName     = "";
        public boolean   nameVisible = false;
    }

    // ── Textures ───────────────────────────────────────────────────────────

    private static final ResourceLocation[] MALE_TEXTURES = {
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/northman_male_1.png"),
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/northman_male_2.png"),
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/northman_male_3.png"),
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/northman_male_4.png"),
    };

    private static final ResourceLocation[] FEMALE_TEXTURES = {
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/northman_female_1.png"),
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/northman_female_2.png"),
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/northman_female_3.png"),
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/northman_female_4.png"),
    };

    // ── Two models: Steve (non-slim) and Alex (slim) ───────────────────────

    /** Steve model — 4-px wide arms, used for male NPCs. */
    private final HumanoidModel<State> steveModel;
    /** Alex model — 3-px slim arms, used for female NPCs. */
    private final HumanoidModel<State> alexModel;

    // ── Constructor ────────────────────────────────────────────────────────

    public NorthmanRenderer(EntityRendererProvider.Context ctx) {
        super(ctx,
                new HumanoidModel<>(ctx.bakeLayer(ModelLayers.PLAYER)),
                0.5f);
        this.steveModel = this.model; // same object we passed above
        this.alexModel  = new HumanoidModel<>(ctx.bakeLayer(ModelLayers.PLAYER_SLIM));

        this.addLayer(new HumanoidArmorLayer<State, HumanoidModel<State>, HumanoidModel<State>>(
                this,
                new HumanoidModel<>(ctx.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidModel<>(ctx.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                ctx.getEquipmentRenderer()
        ));
    }

    // ── State ──────────────────────────────────────────────────────────────

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(NorthmanEntity entity, State state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.variant  = entity.getVariant();
        state.gender   = entity.getGender();
        // Use the synced Minecraft custom name (set via setCustomName on the server)
        state.npcName  = entity.getCustomName() != null ? entity.getCustomName().getString() : "";
        // Swap to the appropriate body model before rendering
        this.model = (state.gender == NpcGender.FEMALE) ? alexModel : steveModel;
        // Name visibility: check distance to local player (client-side safe)
        Player localPlayer = Minecraft.getInstance().player;
        state.nameVisible = localPlayer != null
                && entity.distanceToSqr(localPlayer) <= NAME_VISIBLE_RANGE * NAME_VISIBLE_RANGE;
    }

    // ── Texture ────────────────────────────────────────────────────────────

    @Override
    public ResourceLocation getTextureLocation(State state) {
        if (state.gender == NpcGender.FEMALE) {
            int idx = state.variant - NorthmanEntity.VARIANTS_PER_GENDER;
            return FEMALE_TEXTURES[Math.abs(idx) % FEMALE_TEXTURES.length];
        }
        return MALE_TEXTURES[state.variant % MALE_TEXTURES.length];
    }

    // ── Name tag ───────────────────────────────────────────────────────────

    @Override
    protected boolean shouldShowName(NorthmanEntity entity, double distSq) {
        return false; // suppress default always-on tag; we render conditionally
    }

    @Override
    public void render(State state, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(state, poseStack, bufferSource, packedLight);
        if (state.nameVisible && !state.npcName.isEmpty()) {
            renderNpcNameTag(state.npcName, poseStack, bufferSource, packedLight);
        }
    }

    static void renderNpcNameTag(String name, PoseStack poseStack,
                                 MultiBufferSource buffer, int light) {
        poseStack.pushPose();
        poseStack.translate(0.0, 2.1, 0.0);
        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0f));
        float scale = 0.025f;
        poseStack.scale(-scale, -scale, scale);

        var font = Minecraft.getInstance().font;
        int  tw  = font.width(name);
        float ox = -tw / 2.0f;

        // Semi-transparent dark background
        var mat   = poseStack.last().pose();
        var bgBuf = buffer.getBuffer(net.minecraft.client.renderer.RenderType.textBackgroundSeeThrough());
        bgBuf.addVertex(mat, ox - 1,      -1, 0).setColor(0, 0, 0, 70).setLight(light);
        bgBuf.addVertex(mat, ox - 1,       9, 0).setColor(0, 0, 0, 70).setLight(light);
        bgBuf.addVertex(mat, ox + tw + 1,  9, 0).setColor(0, 0, 0, 70).setLight(light);
        bgBuf.addVertex(mat, ox + tw + 1, -1, 0).setColor(0, 0, 0, 70).setLight(light);

        // White text, see-through
        font.drawInBatch(name, ox, 0, 0xFFFFFF, false,
                poseStack.last().pose(), buffer,
                net.minecraft.client.gui.Font.DisplayMode.SEE_THROUGH, 0, light);

        poseStack.popPose();
    }
}