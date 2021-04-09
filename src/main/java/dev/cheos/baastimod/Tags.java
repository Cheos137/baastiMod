package dev.cheos.baastimod;

import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;

public class Tags {
	public static class Blocks {
		public static final ResourceLocation RL_UNDYING_CORE_SURROUNDING = new ResourceLocation(BaastiMod.MODID, "undying_core_surrounding");
		public static final ITagProvider<Block> UNDYING_CORE_SURROUNDING = () -> get(RL_UNDYING_CORE_SURROUNDING);
		
		public static ITag<Block> get(ResourceLocation tagname) {
			return BlockTags.getAllTags().getTagOrEmpty(tagname);
		}
	}
	
	public static interface ITagProvider<T> {
		ITag<T> get();
	}
}
