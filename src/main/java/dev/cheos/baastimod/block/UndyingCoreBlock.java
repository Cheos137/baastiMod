package dev.cheos.baastimod.block;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;

import dev.cheos.baastimod.BaastiMod;
import dev.cheos.baastimod.block.tileentity.UndyingCoreTileEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class UndyingCoreBlock extends Block implements BlockItemConvertible {
	protected static final VoxelShape SHAPE = box(5.0D, 5.0D, 5.0D, 11.0D, 11.0D, 11.0D);

	protected UndyingCoreBlock() {
		super(AbstractBlock.Properties.of(Material.GLASS, MaterialColor.GOLD).strength(3.0F).noOcclusion().harvestTool(ToolType.AXE)); // .dynamicShape()
		setRegistryName(BaastiMod.MODID, "undying_core");
	}

	@Override
	public BlockItem asBlockItem() {
		return toBlockItem(this);
	}

	@Override
	public Item.Properties getBlockItemProperties() {
		return new Item.Properties().rarity(Rarity.RARE).tab(ItemGroup.TAB_MISC).setISTER(() -> () -> new ItemStackTileEntityRenderer() {
			private final TileEntity CORE = new UndyingCoreTileEntity();
			@Override
			public void renderByItem(ItemStack stack, TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
				super.renderByItem(stack, transformType, matrixStack, buffer, combinedLight, combinedOverlay);
				
				TileEntityRendererDispatcher.instance.renderItem(CORE, matrixStack, buffer, combinedLight, combinedOverlay);
			}
		});
	}
	
	@Override
	public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
		// particle stuff here
	}
	
	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext ctx) {
		return SHAPE;
	}
	
	/*==================== FORGE TE STUFF START ====================*/
	// alternative is extending ContainerBlock
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
