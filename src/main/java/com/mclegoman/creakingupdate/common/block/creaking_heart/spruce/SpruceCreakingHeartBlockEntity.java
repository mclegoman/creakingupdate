package com.mclegoman.creakingupdate.common.block.creaking_heart.spruce;

import com.mclegoman.creakingupdate.common.block.BlockEntityRegistry;
import com.mclegoman.creakingupdate.common.block.BlockRegistry;
import com.mclegoman.creakingupdate.common.block.creaking_heart.CreakingVariantHeartBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;

public class SpruceCreakingHeartBlockEntity extends CreakingVariantHeartBlockEntity {
	public SpruceCreakingHeartBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegistry.spruceCreakingHeart, pos, state, "spruce");
	}
	public static Block getBlock() {
		return BlockRegistry.spruceCreakingHeart.block();
	}
	public static TagKey<Block> getLogs() {
		return SpruceCreakingHeartBlock.getLogs();
	}
}
