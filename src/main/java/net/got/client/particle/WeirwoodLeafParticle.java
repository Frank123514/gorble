package net.got.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class WeirwoodLeafParticle extends SingleQuadParticle {

    private float angle;
    private float angleDelta;

    protected WeirwoodLeafParticle(ClientLevel level, double x, double y, double z, TextureAtlasSprite sprite, RandomSource random) {
        super(level, x, y, z, sprite);

        this.xd = (random.nextDouble() - 0.5) * 0.04;
        this.yd = -0.04 - random.nextDouble() * 0.02;
        this.zd = (random.nextDouble() - 0.5) * 0.04;

        this.quadSize = 0.05f + random.nextFloat() * 0.03f;
        this.lifetime = 160 + random.nextInt(80);

        this.angle = random.nextFloat() * (float) Math.PI * 2;
        this.angleDelta = ((random.nextFloat() - 0.5f) * 0.02f);

        this.gravity = 0.003f;
        this.hasPhysics = true;
    }

    @Override
    public void tick() {
        super.tick();
        
        this.xd += Math.sin(this.age * 0.08) * 0.003;
        this.angle += this.angleDelta;
        this.oRoll = this.roll;
        this.roll = this.angle;
    }

    @Override
    public SingleQuadParticle.Layer getLayer() {
        return SingleQuadParticle.Layer.TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double x, double y, double z,
                                       double dx, double dy, double dz, RandomSource random) {
            
            return new WeirwoodLeafParticle(level, x, y, z, sprites.first(), random);
        }
    }
}
