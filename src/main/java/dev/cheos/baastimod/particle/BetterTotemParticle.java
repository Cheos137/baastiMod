package dev.cheos.baastimod.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.world.ClientWorld;

public class BetterTotemParticle extends SimpleAnimatedParticle {
	   private final double xStart;
	   private final double yStart;
	   private final double zStart;

	protected BetterTotemParticle(ClientWorld world, double x, double y, double z, double xd, double yd, double zd, IAnimatedSprite sprite) {
		super(world, x, y, z, sprite, -0.05F);
		this.xd = xd;
		this.yd = yd;
		this.zd = zd;
		this.xStart = x;
		this.yStart = y;
		this.zStart = z;
		this.xo = x + xd;
		this.yo = y + yd;
		this.zo = z + zd;
		this.x = this.xo;
		this.y = this.yo;
		this.z = this.zo;
		this.quadSize *= 0.75F;
		this.lifetime = 60 + this.random.nextInt(12);
		this.setSpriteFromAge(sprite);
		this.setBaseAirFriction(0.6F);
		if (this.random.nextInt(4) == 0)
			this.setColor(0.6F + this.random.nextFloat() * 0.2F, 0.6F + this.random.nextFloat() * 0.3F, this.random.nextFloat() * 0.2F);
		else
			this.setColor(0.1F + this.random.nextFloat() * 0.2F, 0.4F + this.random.nextFloat() * 0.3F, this.random.nextFloat() * 0.2F);
	}

	@Override
	public void setColor(int rgb) {
		float r = ((rgb & 0xFF0000) >> 16) / 255.0F;
		float g = ((rgb & 0xFF00) >> 8) / 255.0F;
		float b = ((rgb & 0xFF) >> 0) / 255.0F;
		float a = 1.0F;
		this.setColor(r * a, g * a, b * a);
	}
	
	@Override
	public void setFadeColor(int rgb) {
		this.fadeR = ((rgb & 0xFF0000) >> 16) / 255.0F;
		this.fadeG = ((rgb & 0xFF00) >> 8) / 255.0F;
		this.fadeB = ((rgb & 0xFF) >> 0) / 255.0F;
		this.hasFade = true;
	}

	@Override
	public void move(double x, double y, double z) {
		this.setBoundingBox(this.getBoundingBox().move(x, y, z));
		this.setLocationFromBoundingbox();
	}
	
	@Override
	public void tick() {
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		if (this.age++ >= this.lifetime)
			this.remove();
		else {
			this.setSpriteFromAge(this.sprites);
			if (this.age > this.lifetime / 2) {
				this.setAlpha(1.0F - ((float)this.age - (float)(this.lifetime / 2)) / this.lifetime);
				if (super.hasFade) {
					this.rCol += (this.fadeR - this.rCol) * 0.2F;
					this.gCol += (this.fadeG - this.gCol) * 0.2F;
					this.bCol += (this.fadeB - this.bCol) * 0.2F;
				}
			}
			
			float f = (float)this.age / (float)this.lifetime;
			f = 1.0F - f;
			float f1 = 1.0F - f;
			f1 = f1 * f1;
			f1 = f1 * f1;
			this.x = this.xStart + this.xd * f;
			this.y = this.yStart + this.yd * f - f1 * 1.2F;
			this.z = this.zStart + this.zd * f;
		}
	}
}
