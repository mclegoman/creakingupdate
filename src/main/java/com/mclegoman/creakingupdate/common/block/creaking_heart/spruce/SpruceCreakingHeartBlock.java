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
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
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
			return state.get(ACTIVE) ? validateTicker(type, BlockEntityRegistry.spruceCreakingHeart, SpruceCreakingHeartBlockEntity::tick) : null;
		}
	}
	public static TagKey<Block> getLogs() {
		return BlockTags.SPRUCE_LOGS;
	}

	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (isNightAndNatural()) {
			if (state.get(ACTIVE)) {
				if (random.nextInt(16) == 0 && isSurroundedByLogs(world, pos)) {
					world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_CREAKING_HEART_IDLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
				}

			}
		}
	}
	protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
		BlockState blockState = super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
		return enableIfValid(blockState, world, pos);
	}
	private static BlockState enableIfValid(BlockState state, WorldView world, BlockPos pos) {
		boolean bl = shouldBeEnabled(state, world, pos);
		boolean bl2 = !(Boolean)state.get(ACTIVE);
		return bl && bl2 ? state.with(ACTIVE, true) : state;
	}
	public static boolean shouldBeEnabled(BlockState state, WorldView world, BlockPos pos) {
		Direction.Axis axis = state.get(AXIS);
		Direction[] var4 = axis.getDirections();

		for (Direction direction : var4) {
			BlockState blockState = world.getBlockState(pos.offset(direction));
			if (!blockState.isIn(getLogs()) || blockState.get(AXIS) != axis) {
				return false;
			}
		}

		return true;
	}
	private static boolean isSurroundedByLogs(WorldAccess world, BlockPos pos) {
		for (Direction direction : Direction.values()) {
			BlockPos blockPos = pos.offset(direction);
			BlockState blockState = world.getBlockState(blockPos);
			if (!blockState.isIn(getLogs())) {
				return false;
			}
		}

		return true;
	}
	@Nullable
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return enableIfValid(this.getDefaultState().with(AXIS, ctx.getSide().getAxis()), ctx.getWorld(), ctx.getBlockPos());
	}
}