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
	private static final RenderMaterial GLASS_MATERIAL = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS, new ResourceLocation(BaastiMod.MODID, "entity/undying_core_box"));
	@SuppressWarnings("deprecation")
	private static final RenderMaterial TOTEM_MATERIAL = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS, new ResourceLocation(BaastiMod.MODID, "entity/undying_core_totem"));
	private final ModelRenderer GLASS_MODEL = new ModelRenderer(64, 32, 0, 0);
	private final ModelRenderer TOTEM_MODEL = new ModelRenderer(32, 16, 0, 0);
	
	public UndyingCoreTileEntityRenderer(TileEntityRendererDispatcher terd) {
		super(terd);
		this.GLASS_MODEL    .addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F);
		this.TOTEM_MODEL    .addBox(-4.0F, -5.0F, -0.5F,  8.0F, 11.0F,  1.0F)  // CENTER BOX
			.texOffs( 0, 12).addBox(-3.0F,  6.0F, -0.5F,  6.0F,  1.0F,  1.0F)  // TOP BIT
			.texOffs(18,  0).addBox(-3.0F, -7.0F, -0.5F,  6.0F,  2.0F,  1.0F)  // BOTTOM PART
			.texOffs( 0, 14).addBox(-2.0F, -8.0F, -0.5F,  4.0F,  1.0F,  1.0F)  // BOTTOM BIT
			.texOffs(18,  3).addBox(-6.0F, -3.0F, -0.5F,  2.0F,  3.0F,  1.0F)  // LEFT ARM
			.texOffs(18,  7).addBox(-7.0F, -2.0F, -0.5F,  1.0F,  2.0F,  1.0F)  // LEFT ARM BIT
			.texOffs(24,  3).addBox( 4.0F, -3.0F, -0.5F,  2.0F,  3.0F,  1.0F)  // RIGHT ARM
			.texOffs(24,  7).addBox( 6.0F, -2.0F, -0.5F,  1.0F,  2.0F,  1.0F); // RIGHT ARM BIT
	}

	@Override
	public void render(UndyingCoreTileEntity tileentity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
		if (tileentity == null) return; // glitched TE?
		if (!tileentity.hasLevel()) { // probably item to be rendered -> no animation
			IVertexBuilder vb = TOTEM_MATERIAL.buffer(buffer, RenderType::entitySolid);
			matrixStack.pushPose();
			matrixStack.translate(0.5D, 0.5D, 0.5D);
			matrixStack.scale(0.5F, 0.5F, 0.5F);
			TOTEM_MODEL.render(matrixStack, vb, combinedLight, combinedOverlay);
			matrixStack.popPose();
			renderGlassBox(matrixStack, buffer, combinedLight, combinedOverlay);
			return;
		}
		if (!tileentity.getBlockState().is(CustomBlocks.UNDYING_CORE)) return;
		
		final int tier = tileentity.getTier();
		final long ticks = tileentity.getLevel().getGameTime();
		
		IVertexBuilder vb = TOTEM_MATERIAL.buffer(buffer, RenderType::entitySolid);
		matrixStack.pushPose();
		matrixStack.translate(0.5D, 0.5D, 0.5D);
		matrixStack.scale(0.5F, 0.5F, 0.5F);
		
		if (tier < 1)
			matrixStack.translate(0.0D, 0.1D * MathHelper.sin(ticks / 50F), 0.0D);
		else {
			matrixStack.translate(0.05D * MathHelper.cos(ticks / 100F), 0.1D * MathHelper.sin(ticks / 50F), 0.05D * MathHelper.sin(ticks / 100F));
			matrixStack.mulPose(Vector3f.YP.rotationDegrees(ticks % 360));
		}
		if (tier > 1)
			matrixStack.mulPose(Vector3f.XP.rotationDegrees(15 * MathHelper.sin((ticks / 75F) % 360)));
		if (tier > 2)
			matrixStack.mulPose(Vector3f.ZP.rotationDegrees(15 * MathHelper.sin((ticks / 100F) % 360)));
		
		TOTEM_MODEL.render(matrixStack, vb, combinedLight, combinedOverlay);
		matrixStack.popPose();
		
		renderGlassBox(matrixStack, buffer, combinedLight, combinedOverlay);
	}
	
	private void renderGlassBox(MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
		IVertexBuilder vb = GLASS_MATERIAL.buffer(buffer, RenderType::entityTranslucent);
		matrixStack.pushPose();
		matrixStack.translate(0.5D, 0.5D, 0.5D);
		matrixStack.scale(0.6F, 0.6F, 0.6F);
//		matrixStack.scale(0.75F, 0.75F, 0.75F);
		GLASS_MODEL.render(matrixStack, vb, combinedLight, combinedOverlay);
		matrixStack.popPose();
	}
}
