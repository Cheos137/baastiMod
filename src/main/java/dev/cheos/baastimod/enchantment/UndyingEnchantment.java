package dev.cheos.baastimod.enchantment;

import dev.cheos.baastimod.BaastiMod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;

public class UndyingEnchantment extends Enchantment {

	public UndyingEnchantment() {
		super(Rarity.VERY_RARE, EnchantmentType.ARMOR, new EquipmentSlotType[] {
				EquipmentSlotType.HEAD,
				EquipmentSlotType.CHEST,
				EquipmentSlotType.LEGS,
				EquipmentSlotType.FEET,
		});
		setRegistryName(new ResourceLocation(BaastiMod.MODID, "undying"));
	}

	@Override
	public int getMinCost(int enchantmentLevel) {
		return 21;
	}

	@Override
	public int getMaxCost(int enchantmentLevel) {
		return getMinCost(enchantmentLevel) + 20;
	}

	@Override
	protected boolean checkCompatibility(Enchantment ench) {
		return this != ench && ench != Enchantments.MENDING;
	}
}
