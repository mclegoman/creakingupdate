package com.mclegoman.creakingupdate.common.block.creaking_heart.birch;

import com.mclegoman.creakingupdate.common.block.BlockEntityRegistry;
import com.mclegoman.creakingupdate.common.block.BlockRegistry;
import com.mclegoman.creakingupdate.common.block.creaking_heart.CreakingVariantHeartBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;

public class BirchCreakingHeartBlockEntity extends CreakingVariantHeartBlockEntity {
	public BirchCreakingHeartBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegistry.birchCreakingHeart, pos, state, "birch");
	}
	public static Block getBlock() {
		return BlockRegistry.birchCreakingHeart.block();
	}
	public static TagKey<Block> getLogs() {
		return BirchCreakingHeartBlock.getLogs();
	}
}
