package com.mclegoman.creakingupdate.common.block.creaking_heart.oak;

import com.mclegoman.creakingupdate.common.block.BlockEntityRegistry;
import com.mclegoman.creakingupdate.common.block.BlockRegistry;
import com.mclegoman.creakingupdate.common.block.creaking_heart.CreakingVariantHeartBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;

public class OakCreakingHeartBlockEntity extends CreakingVariantHeartBlockEntity {
	public OakCreakingHeartBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegistry.oakCreakingHeart, pos, state, "oak");
	}
	public static Block getBlock() {
		return BlockRegistry.oakCreakingHeart.block();
	}
	public static TagKey<Block> getLogs() {
		return OakCreakingHeartBlock.getLogs();
	}
}
