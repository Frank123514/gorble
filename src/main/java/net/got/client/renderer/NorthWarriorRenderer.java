package net.got.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.entity.NorthWarriorEntity;
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
 * Renderer for the North Warrior NPC.
 *
 * <p>Males → Steve model (4-px arms). Females → Alex model (3-px slim arms).
 * Name tag appears when a player approaches within 8 blocks.
 */
public class NorthWarriorRenderer
        extends HumanoidMobRenderer<NorthWarriorEntity, NorthWarriorRenderer.State, HumanoidModel<NorthWarriorRenderer.State>> {

    private static final double NAME_VISIBLE_RANGE = 8.0;

    public static class State extends HumanoidRenderState {
        public int       variant     = 0;
        public NpcGender gender      = NpcGender.MALE;
        public String    npcName     = "";
        public boolean   nameVisible = false;
    }

    private static final ResourceLocation[] MALE_TEXTURES = {
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/north_warrior_male_1.png"),
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/north_warrior_male_2.png"),
    };

    private static final ResourceLocation[] FEMALE_TEXTURES = {
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/north_warrior_female_1.png"),
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/north_warrior_female_2.png"),
    };

    private final HumanoidModel<State> steveModel;
    private final HumanoidModel<State> alexModel;

    public NorthWarriorRenderer(EntityRendererProvider.Context ctx) {
        super(ctx,
                new HumanoidModel<>(ctx.bakeLayer(ModelLayers.PLAYER)),
                0.5f);
        this.steveModel = this.model;
        this.alexModel  = new HumanoidModel<>(ctx.bakeLayer(ModelLayers.PLAYER_SLIM));

        this.addLayer(new HumanoidArmorLayer<State, HumanoidModel<State>, HumanoidModel<State>>(
                this,
                new HumanoidModel<>(ctx.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidModel<>(ctx.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                ctx.getEquipmentRenderer()
        ));
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(NorthWarriorEntity entity, State state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.variant  = entity.getVariant();
        state.gender   = entity.getGender();
        state.npcName  = entity.getCustomName() != null ? entity.getCustomName().getString() : "";
        this.model = (state.gender == NpcGender.FEMALE) ? alexModel : steveModel;
        Player localPlayer = Minecraft.getInstance().player;
        state.nameVisible = localPlayer != null
                && entity.distanceToSqr(localPlayer) <= NAME_VISIBLE_RANGE * NAME_VISIBLE_RANGE;
    }

    @Override
    public ResourceLocation getTextureLocation(State state) {
        if (state.gender == NpcGender.FEMALE) {
            int idx = state.variant - NorthWarriorEntity.VARIANTS_PER_GENDER;
            return FEMALE_TEXTURES[Math.abs(idx) % FEMALE_TEXTURES.length];
        }
        return MALE_TEXTURES[state.variant % MALE_TEXTURES.length];
    }

    @Override
    protected boolean shouldShowName(NorthWarriorEntity entity, double distSq) {
        return false;
    }

    @Override
    public void render(State state, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(state, poseStack, bufferSource, packedLight);
        if (state.nameVisible && !state.npcName.isEmpty()) {
            NorthmanRenderer.renderNpcNameTag(state.npcName, poseStack, bufferSource, packedLight);
        }
    }
}