package com.mclegoman.creakingupdate.common.block.creaking_heart.bamboo;

import com.mclegoman.creakingupdate.common.block.BlockEntityRegistry;
import com.mclegoman.creakingupdate.common.block.BlockRegistry;
import com.mclegoman.creakingupdate.common.block.creaking_heart.CreakingVariantHeartBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;

public class BambooCreakingHeartBlockEntity extends CreakingVariantHeartBlockEntity {
	public BambooCreakingHeartBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegistry.bambooCreakingHeart, pos, state, "bamboo");
	}
	public static Block getBlock() {
		return BlockRegistry.bambooCreakingHeart.block();
	}
	public static TagKey<Block> getLogs() {
		return BambooCreakingHeartBlock.getLogs();
	}
}
