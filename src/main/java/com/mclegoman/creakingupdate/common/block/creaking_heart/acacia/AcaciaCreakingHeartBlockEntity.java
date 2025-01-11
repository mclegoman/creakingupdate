package com.mclegoman.creakingupdate.common.block.creaking_heart.acacia;

import com.mclegoman.creakingupdate.common.block.BlockEntityRegistry;
import com.mclegoman.creakingupdate.common.block.BlockRegistry;
import com.mclegoman.creakingupdate.common.block.creaking_heart.CreakingVariantHeartBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;

public class AcaciaCreakingHeartBlockEntity extends CreakingVariantHeartBlockEntity {
	public AcaciaCreakingHeartBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegistry.acaciaCreakingHeart, pos, state, "acacia");
	}
	public static Block getBlock() {
		return BlockRegistry.acaciaCreakingHeart.block();
	}
	public static TagKey<Block> getLogs() {
		return AcaciaCreakingHeartBlock.getLogs();
	}
}
