package dev.cheos.baastimod;

import com.mojang.blaze3d.matrix.MatrixStack;

import dev.cheos.baastimod.block.tileentity.UndyingCoreTileEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ClientOnly {
	private final static TileEntity CORE = new UndyingCoreTileEntity();
	
	public static void setISTERUndyingCore(Item.Properties prop) {
		prop.setISTER(() -> () -> new ItemStackTileEntityRenderer() {
			@Override
			public void renderByItem(ItemStack stack, TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
				super.renderByItem(stack, transformType, matrixStack, buffer, combinedLight, combinedOverlay);
				TileEntityRendererDispatcher.instance.renderItem(CORE, matrixStack, buffer, combinedLight, combinedOverlay);
			}
		});
	}
}
