package dev.cheos.baastimod.block;

import java.util.Random;

import dev.cheos.baastimod.BaastiMod;
import dev.cheos.baastimod.ClientOnly;
import dev.cheos.baastimod.block.tileentity.UndyingCoreTileEntity;
import dev.cheos.baastimod.particle.CustomParticleTypes;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.DistExecutor;

public class UndyingCoreBlock extends Block implements BlockItemConvertible {
	private static final VoxelShape SHAPE = box(3.2D, 3.2D, 3.2D, 12.8D, 12.8D, 12.8D);

	protected UndyingCoreBlock() {
		super(AbstractBlock.Properties.of(Material.STONE, MaterialColor.NONE)
				.strength(3.0F)
				.noOcclusion()
				.harvestTool(ToolType.PICKAXE)
				.sound(SoundType.GLASS)
				.lightLevel(state -> 12));
		setRegistryName(BaastiMod.MODID, "undying_core");
	}

	@Override
	public BlockItem asBlockItem() {
		return toNewBlockItem(this);
	}

	@Override
	public Item.Properties getBlockItemProperties() {
		Item.Properties prop = new Item.Properties().rarity(Rarity.RARE).tab(ItemGroup.TAB_MISC);
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientOnly.setISTERUndyingCore(prop));
		return prop;
	}
	
	@Override
	public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
		TileEntity tileentity = world.getBlockEntity(pos);
		if (!(tileentity instanceof UndyingCoreTileEntity)) return;
		
		UndyingCoreTileEntity uctileentity = (UndyingCoreTileEntity) tileentity;
		if (uctileentity.getTier() < 1) return;
		
		double yo = MathHelper.sin((world.getGameTime() + 35) * 0.1F) / 2.0F + 0.5F;
		yo = (yo * yo + yo) * 0.3F;
		Vector3d baseVec = new Vector3d(pos.getX() + 0.5D, pos.getY() + 1.5D + yo, pos.getZ() + 0.5D);
		
		for(BlockPos blockpos : uctileentity.surroundings)
			if (random.nextInt(120 / uctileentity.getTier()) == 0) {
				float xd = -0.5F + random.nextFloat();
				float yd = -2.0F + random.nextFloat();
				float zd = -0.5F + random.nextFloat();
				blockpos = blockpos.subtract(pos);
				Vector3d offsVec = new Vector3d(xd, yd, zd).add(blockpos.getX(), blockpos.getY(), blockpos.getZ());
				world.addParticle(CustomParticleTypes.BETTER_TOTEM, baseVec.x, baseVec.y, baseVec.z, offsVec.x, offsVec.y, offsVec.z);
			}
	}
	
	@Override
	public void destroy(IWorld world, BlockPos pos, BlockState state) {
		Random random = world.getRandom();
		for(int i = 0; i < 50; i++) {
			if (random.nextInt(2) == 0)
				world.addParticle(
						ParticleTypes.TOTEM_OF_UNDYING,
						0.5D + pos.getX(),
						0.5D + pos.getY(),
						0.5D + pos.getZ(),
						2.0D * (-0.5D + random.nextDouble()),
						3.0D * (-0.5D + random.nextDouble()),
						2.0D * (-0.5D + random.nextDouble()));
			world.addParticle(
					ParticleTypes.SMOKE,
					0.5D + pos.getX(),
					0.5D + pos.getY(),
					0.5D + pos.getZ(),
					0.5D * (-0.5D + random.nextDouble()),
					0.5D * (-0.5D + random.nextDouble()),
					0.5D * (-0.5D + random.nextDouble()));
		}
	}
	
	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext ctx) {
		return SHAPE;
	}
	
	@Override
	public PushReaction getPistonPushReaction(BlockState state) {
		return PushReaction.BLOCK;
	}
	
	/*==================== FORGE TE STUFF START ====================*/
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new UndyingCoreTileEntity();
	}
	/*==================== FORGE TE STUFF  END  ====================*/
}
