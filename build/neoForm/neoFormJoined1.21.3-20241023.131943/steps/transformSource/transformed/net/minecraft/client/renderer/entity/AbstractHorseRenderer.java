package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.state.EquineRenderState;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractHorseRenderer<T extends AbstractHorse, S extends EquineRenderState, M extends EntityModel<? super S>>
    extends AgeableMobRenderer<T, S, M> {
    private final float scale;

    public AbstractHorseRenderer(EntityRendererProvider.Context p_173906_, M p_360303_, M p_363195_, float p_173908_) {
        super(p_173906_, p_360303_, p_363195_, 0.75F);
        this.scale = p_173908_;
    }

    protected void scale(S p_363015_, PoseStack p_113751_) {
        p_113751_.scale(this.scale, this.scale, this.scale);
        super.scale(p_363015_, p_113751_);
    }

    public void extractRenderState(T p_362148_, S p_362644_, float p_361007_) {
        super.extractRenderState(p_362148_, p_362644_, p_361007_);
        p_362644_.isSaddled = p_362148_.isSaddled();
        p_362644_.isRidden = p_362148_.isVehicle();
        p_362644_.eatAnimation = p_362148_.getEatAnim(p_361007_);
        p_362644_.standAnimation = p_362148_.getStandAnim(p_361007_);
        p_362644_.feedingAnimation = p_362148_.getMouthAnim(p_361007_);
        p_362644_.animateTail = p_362148_.tailCounter > 0;
    }
}
