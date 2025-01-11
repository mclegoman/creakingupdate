package com.mclegoman.creakingupdate.common.block.creaking_heart.mangrove;

import com.mclegoman.creakingupdate.common.block.BlockEntityRegistry;
import com.mclegoman.creakingupdate.common.block.BlockRegistry;
import com.mclegoman.creakingupdate.common.block.creaking_heart.CreakingVariantHeartBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;

public class MangroveCreakingHeartBlockEntity extends CreakingVariantHeartBlockEntity {
	public MangroveCreakingHeartBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegistry.mangroveCreakingHeart, pos, state, "mangrove");
	}
	public static Block getBlock() {
		return BlockRegistry.mangroveCreakingHeart.block();
	}
	public static TagKey<Block> getLogs() {
		return MangroveCreakingHeartBlock.getLogs();
	}
}
