package com.mclegoman.creakingupdate.common.block.creaking_heart.jungle;

import com.mclegoman.creakingupdate.common.block.BlockEntityRegistry;
import com.mclegoman.creakingupdate.common.block.BlockRegistry;
import com.mclegoman.creakingupdate.common.block.creaking_heart.CreakingVariantHeartBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;

public class JungleCreakingHeartBlockEntity extends CreakingVariantHeartBlockEntity {
	public JungleCreakingHeartBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegistry.jungleCreakingHeart, pos, state, "jungle");
	}
	public static Block getBlock() {
		return BlockRegistry.jungleCreakingHeart.block();
	}
	public static TagKey<Block> getLogs() {
		return JungleCreakingHeartBlock.getLogs();
	}
}
