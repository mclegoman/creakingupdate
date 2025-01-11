package com.mclegoman.creakingupdate.common.block.creaking_heart.cherry;

import com.mclegoman.creakingupdate.common.block.BlockEntityRegistry;
import com.mclegoman.creakingupdate.common.block.BlockRegistry;
import com.mclegoman.creakingupdate.common.block.creaking_heart.CreakingVariantHeartBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;

public class CherryCreakingHeartBlockEntity extends CreakingVariantHeartBlockEntity {
	public CherryCreakingHeartBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegistry.cherryCreakingHeart, pos, state, "cherry");
	}
	public static Block getBlock() {
		return BlockRegistry.cherryCreakingHeart.block();
	}
	public static TagKey<Block> getLogs() {
		return CherryCreakingHeartBlock.getLogs();
	}
}
