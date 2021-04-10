package dev.cheos.baastimod.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;

public class BetterTotemParticleFactory implements IParticleFactory<BasicParticleType> {
	private final IAnimatedSprite sprite;

    public BetterTotemParticleFactory(IAnimatedSprite sprite) {
       this.sprite = sprite;
    }
	
	@Override
	public Particle createParticle(BasicParticleType type, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
		BetterTotemParticle particle = new BetterTotemParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, sprite);
		return particle;
	}
}
