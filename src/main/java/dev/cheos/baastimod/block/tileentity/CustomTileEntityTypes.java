package dev.cheos.baastimod.block.tileentity;

import dev.cheos.baastimod.BaastiMod;
import dev.cheos.baastimod.block.CustomBlocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.IForgeRegistry;

public class CustomTileEntityTypes {
	public static final TileEntityType<UndyingCoreTileEntity> UNDYING_CORE = TileEntityType.Builder.of(UndyingCoreTileEntity::new, CustomBlocks.UNDYING_CORE).build(null);
	
	public static void registerTo(IForgeRegistry<TileEntityType<?>> registry) {
		UNDYING_CORE.setRegistryName(BaastiMod.MODID, "tileentity_undying_core");
		registry.register(UNDYING_CORE);
	}
}
