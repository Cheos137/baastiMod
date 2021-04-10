package dev.cheos.baastimod.block.tileentity;

import java.util.ArrayList;
import java.util.List;

import dev.cheos.baastimod.Tags;
import dev.cheos.baastimod.effect.CustomEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tileentity.ConduitTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

/**
 * @see {@link ConduitTileEntity}
 */
public class UndyingCoreTileEntity extends TileEntity implements ITickableTileEntity {
	public final List<BlockPos> surroundings = new ArrayList<>();
	private int tier;
	
	public UndyingCoreTileEntity() {
		super(CustomTileEntityTypes.UNDYING_CORE);
	}

	@Override
	public void tick() {
		tier = checkTier(this.worldPosition);
		if (this.level.getGameTime() % 100L == 0L)
			if (!this.level.isClientSide)
				applyEffects();
	}
	
	public int getTier() {
		return tier;
	}
	
	private int checkTier(BlockPos pos) {
		surroundings.clear();
		
		int surrounding = 0;
		for (int x = -2; x <= 2; x++)
			for (int y = -2; y <= 2; y++)
				for (int z = -2; z <= 2; z++)
					if (Math.abs(x) == 2 || Math.abs(y) == 2 || Math.abs(z) == 2) {
						BlockPos offsetPos = pos.offset(x, y, z);
						if (this.level.getBlockState(offsetPos).is(Tags.Blocks.UNDYING_CORE_SURROUNDING.get())) {
							surroundings.add(offsetPos);
							surrounding++;
						}
					}
		return Math.min(surrounding / 16, 3);
	}
	
	private void applyEffects() {
		if (!this.level.isClientSide && tier > 0) {
			int x = this.worldPosition.getX(), y = this.worldPosition.getY(), z = this.worldPosition.getZ();
			int radius = this.tier * 16;
			AxisAlignedBB aabb = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1).inflate(radius);
			List<PlayerEntity> players = this.level.getEntitiesOfClass(PlayerEntity.class, aabb);
			
			for (PlayerEntity player : players)
				player.addEffect(
						new EffectInstance(
								CustomEffects.UNDYING,
								200 + 40 * tier /* duration is tier dependant */,
								0 /* amplifier: 0, level => 1                 */,
								true /* yes, this is an ambient effect        */,
								false /* no, please no particles ('visible')  */,
								true /* yes, show the icon in the top left    */));

		}
	}
	
	private void playSound(SoundEvent sound) {
		this.level.playSound(null, this.worldPosition, sound, SoundCategory.BLOCKS, 1.0F, 1.0F);
	}
	
	@Override
	public void setRemoved() {
		// option to play deactivate sound here
		super.setRemoved();
	}
	
	@Override
	public double getViewDistance() {
		return 256.0D;
	}
	
//	@Override
//	public void load(BlockState state, CompoundNBT nbttag) {
//		super.load(state, nbttag);
//	}
//
//	@Override
//	public CompoundNBT save(CompoundNBT nbttag) {
//		super.save(nbttag);
//		nbttag.putInt("Tier", tier);
//		return nbttag;
//	}
}
