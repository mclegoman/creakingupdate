package com.mclegoman.creakingupdate.common.block.creaking_heart.dark_oak;

import com.mclegoman.creakingupdate.common.block.BlockEntityRegistry;
import com.mclegoman.creakingupdate.common.block.BlockRegistry;
import com.mclegoman.creakingupdate.common.block.creaking_heart.CreakingVariantHeartBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;

public class DarkOakCreakingHeartBlockEntity extends CreakingVariantHeartBlockEntity {
	public DarkOakCreakingHeartBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegistry.darkOakCreakingHeart, pos, state, "dark_oak");
	}
	public static Block getBlock() {
		return BlockRegistry.darkOakCreakingHeart.block();
	}
	public static TagKey<Block> getLogs() {
		return DarkOakCreakingHeartBlock.getLogs();
	}
}
