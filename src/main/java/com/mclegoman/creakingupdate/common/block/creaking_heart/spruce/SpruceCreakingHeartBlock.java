package com.mclegoman.creakingupdate.common.block.creaking_heart.spruce;

import com.mclegoman.creakingupdate.common.block.BlockEntityRegistry;
import com.mclegoman.creakingupdate.common.block.creaking_heart.CreakingVariantHeartBlock;
import com.mclegoman.creakingupdate.common.block.creaking_heart.CreakingVariantHeartBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SpruceCreakingHeartBlock extends CreakingVariantHeartBlock {
	public static final MapCodec<CreakingVariantHeartBlock> CODEC = createCodec(SpruceCreakingHeartBlock::new);
	public MapCodec<CreakingVariantHeartBlock> getCodec() {
		return CODEC;
	}
	public SpruceCreakingHeartBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(AXIS, Direction.Axis.Y).with(ACTIVE, false).with(NATURAL, false));
	}
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new SpruceCreakingHeartBlockEntity(pos, state);
	}
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		if (world.isClient) {
			return null;
		} else {
			return state.get(ACTIVE) ? validateTicker(type, BlockEntityRegistry.spruceCreakingHeart, CreakingVariantHeartBlockEntity::tick) : null;
		}
	}
	public static TagKey<Block> getLogs() {
		return BlockTags.SPRUCE_LOGS;
	}
}