package dev.cheos.baastimod;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import dev.cheos.baastimod.block.CustomBlocks;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = BaastiMod.MODID)
public class LootTableLoadHandler {
	private static final List<ResourceLocation> inject = Arrays.stream(new String[] {
			"abandoned_mineshaft",
			"bastion_treasure",
			"buried_treasure",
			"desert_pyramid",
			"jungle_temple",
			"ruined_portal",
			"shipwreck_treasure"
			}).map(s -> new ResourceLocation("minecraft:chests/" + s)).collect(Collectors.toList());
	
	@SubscribeEvent
	public static void onLootTableLoad(LootTableLoadEvent event) {
		if (!inject.contains(event.getName())) return;
		
		float e = 0.0F,
			  f = 3.0F,
			  g = 1.0F,
			  h = 5.0F;
		
		switch (inject.indexOf(event.getName())) {
			case 4:
				e = 2.0F;
				f = 6.0F;
				break;
			case 5:
				f = 1.0F;
				g = 0.0F;
				h = 2.0F;
				break;
			default: break;
		}
		
		LootPool injectPool = LootPool.lootPool()
				.name("baastimod:gilded_emerald")
				.setRolls(new RandomValueRange(e, f))
				.add(ItemLootEntry.lootTableItem(CustomBlocks.GILDED_EMERALD::asItem)
						.setWeight(1)
						.apply(SetCount.setCount(new RandomValueRange(g, h))))
				.build();
		
		LootTable table = event.getTable();
		table.addPool(injectPool);
	}
}
