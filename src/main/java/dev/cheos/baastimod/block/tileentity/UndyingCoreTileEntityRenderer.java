package dev.cheos.baastimod.block.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import dev.cheos.baastimod.BaastiMod;
import dev.cheos.baastimod.block.CustomBlocks;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.ConduitTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

/**
 * @see {@link ConduitTileEntityRenderer}
 */
public class UndyingCoreTileEntityRenderer extends TileEntityRenderer<UndyingCoreTileEntity> {
	@SuppressWarnings("deprecation")
	private final RenderMaterial mat = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS, new ResourceLocation(BaastiMod.MODID, "entity/undying_core"));
	@SuppressWarnings("deprecation")
	private final RenderMaterial TOTEM_MATERIAL = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS, new ResourceLocation("item/totem_of_undying"));
	private final ModelRenderer TEX_RENDER = new ModelRenderer(32, 16, 0, 0);
	private final ModelRenderer TOTEM_MODEL = new ModelRenderer(16, 16, 0, 0);
	
	public UndyingCoreTileEntityRenderer(TileEntityRendererDispatcher terd) {
		super(terd);
		this.TEX_RENDER.addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F);
//		this.TOTEM_MODEL.addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F);
		this.TOTEM_MODEL.addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F);
	}

	@Override
	@SuppressWarnings("deprecation")
	public void render(UndyingCoreTileEntity tileentity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
		if (tileentity == null) return; // glitched TE?
		if (tileentity.hasLevel() && !tileentity.getBlockState().is(CustomBlocks.UNDYING_CORE)) return;
		
		int tier = tileentity.getTier();
		
		if (!tileentity.hasLevel() || tier < 1) { // probably item to be rendered -> no animation
			IVertexBuilder vb = mat.buffer(buffer, RenderType::entityTranslucent);
			matrixStack.pushPose();
			matrixStack.translate(0.5D, 0.5D, 0.5D);
			TEX_RENDER.render(matrixStack, vb, combinedLight, combinedOverlay);
			matrixStack.popPose();
			return;
		}
		
		// what do i really need for this to work like i want it to? custom textures and magic? idk
		
		ModelRenderer MODEL = new ModelRenderer(16, 16, 0, 0);
		MODEL.addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F);
		
		final long ticks = tileentity.getLevel().getGameTime();
		final float RTSF = 0.4F; // Rotation speed factor ... lower -> faster
//		RenderSystem.pushMatrix();
//		RenderSystem.enableAlphaTest();
//		IVertexBuilder vb = mat.buffer(buffer, RenderType::entityTranslucent);
		IVertexBuilder vb = TOTEM_MATERIAL.buffer(buffer, RenderType::entityTranslucent);
		matrixStack.pushPose();
		matrixStack.translate(0.5D, 0.5D, 0.5D);
		matrixStack.translate(0.15D * MathHelper.cos(ticks / 100F), 0.05D * MathHelper.sin(ticks / 25F), 0.15D * MathHelper.sin(ticks / 100F));
		matrixStack.mulPose(Vector3f.XP.rotationDegrees((ticks % (360 * RTSF)) / RTSF));
		matrixStack.mulPose(Vector3f.YP.rotationDegrees((ticks % (360 * RTSF)) / RTSF));
		matrixStack.mulPose(Vector3f.ZP.rotationDegrees((ticks % (360 * RTSF)) / RTSF));
//		TEX_RENDER.render(matrixStack, vb, combinedLight, combinedOverlay);
//		matrixStack.scale(1F, 1F, 1F);
		matrixStack.scale(0.5F, 0.5F, 0.5F);
//		TOTEM_MODEL.render(matrixStack, vb, combinedLight, combinedOverlay);
		MODEL.render(matrixStack, vb, combinedLight, combinedOverlay);
		
		matrixStack.popPose();
//		RenderSystem.popMatrix();
	}
}
