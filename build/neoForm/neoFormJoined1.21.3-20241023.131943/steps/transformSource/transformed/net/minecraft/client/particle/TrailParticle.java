package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.TargetColorParticleOption;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TrailParticle extends TextureSheetParticle {
    private final Vec3 target;

    TrailParticle(
        ClientLevel level,
        double x,
        double y,
        double z,
        double xSpeed,
        double ySpeed,
        double zSpeed,
        Vec3 target,
        int color
    ) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        color = ARGB.scaleRGB(
            color, 0.875F + this.random.nextFloat() * 0.25F, 0.875F + this.random.nextFloat() * 0.25F, 0.875F + this.random.nextFloat() * 0.25F
        );
        this.rCol = (float)ARGB.red(color) / 255.0F;
        this.gCol = (float)ARGB.green(color) / 255.0F;
        this.bCol = (float)ARGB.blue(color) / 255.0F;
        this.quadSize = 0.26F;
        this.target = target;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            int i = this.lifetime - this.age;
            double d0 = 1.0 / (double)i;
            this.x = Mth.lerp(d0, this.x, this.target.x());
            this.y = Mth.lerp(d0, this.y, this.target.y());
            this.z = Mth.lerp(d0, this.z, this.target.z());
        }
    }

    @Override
    public int getLightColor(float partialTick) {
        return 15728880;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<TargetColorParticleOption> {
        private final SpriteSet sprite;

        public Provider(SpriteSet sprite) {
            this.sprite = sprite;
        }

        public Particle createParticle(
            TargetColorParticleOption type,
            ClientLevel level,
            double x,
            double y,
            double z,
            double xSpeed,
            double ySpeed,
            double zSpeed
        ) {
            TrailParticle trailparticle = new TrailParticle(
                level, x, y, z, xSpeed, ySpeed, zSpeed, type.target(), type.color()
            );
            trailparticle.pickSprite(this.sprite);
            trailparticle.setLifetime(level.random.nextInt(40) + 10);
            return trailparticle;
        }
    }
}
