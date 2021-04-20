package dev.cheos.baastimod.enchantment;

import dev.cheos.baastimod.BaastiMod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

public class RocketboostEnchantment extends Enchantment {

	public RocketboostEnchantment() {
		super(Rarity.RARE, EnchantmentType.ARMOR_FEET, new EquipmentSlotType[] { EquipmentSlotType.CHEST, EquipmentSlotType.FEET });
		setRegistryName(new ResourceLocation(BaastiMod.MODID, "rocketboost"));
	}
	
	@Override
	public int getMinCost(int level) {
		return getMaxCost(level) - 11;
	}
	
	@Override
	public int getMaxCost(int level) {
		return 1 + 11 * level * level;
	}
	
	@Override
	public int getMaxLevel() {
		return 5;
	}
	
	@Override
	public boolean canEnchant(ItemStack stack) {
		return super.canEnchant(stack) || stack.getItem().getItem() == Items.ELYTRA;
	}

	@Override
	protected boolean checkCompatibility(Enchantment ench) {
		return this != ench && ench != Enchantments.FROST_WALKER;
	}
}