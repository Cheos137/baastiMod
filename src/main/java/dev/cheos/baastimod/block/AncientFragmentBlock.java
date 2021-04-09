package dev.cheos.baastimod.block;

import dev.cheos.baastimod.BaastiMod;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraftforge.common.ToolType;

public class AncientFragmentBlock extends Block implements BlockItemConvertible {

	public AncientFragmentBlock() {
		super(AbstractBlock.Properties.of(Material.METAL, MaterialColor.EMERALD).strength(3.0F).harvestTool(ToolType.PICKAXE).harvestLevel(2));
		setRegistryName(BaastiMod.MODID, "ancient_fragment");
	}

	@Override
	public BlockItem asBlockItem() {
		return toBlockItem(this);
	}

	@Override
	public Item.Properties getBlockItemProperties() {
		return new Item.Properties().rarity(Rarity.UNCOMMON).tab(ItemGroup.TAB_BUILDING_BLOCKS);
	}
}
