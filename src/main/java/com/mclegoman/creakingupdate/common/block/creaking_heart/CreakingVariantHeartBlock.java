package com.mclegoman.creakingupdate.common.block.creaking_heart;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionImpl;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

public class CreakingVariantHeartBlock extends BlockWithEntity {
	public static final MapCodec<CreakingVariantHeartBlock> CODEC = createCodec(CreakingVariantHeartBlock::new);
	public static final EnumProperty<Direction.Axis> AXIS;
	public static final BooleanProperty ACTIVE;
	public static final BooleanProperty NATURAL;
	public MapCodec<CreakingVariantHeartBlock> getCodec() {
		return CODEC;
	}
	public CreakingVariantHeartBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(AXIS, Direction.Axis.Y).with(ACTIVE, false).with(NATURAL, false));
	}
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new CreakingVariantHeartBlockEntity(null, pos, state, "");
	}
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return null;
	}
	public static boolean isNightAndNatural() {
		return true;
	}
	protected BlockState rotate(BlockState state, BlockRotation rotation) {
		return PillarBlock.changeRotation(state, rotation);
	}
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(AXIS, ACTIVE, NATURAL);
	}
	protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		BlockEntity var7 = world.getBlockEntity(pos);
		if (var7 instanceof CreakingVariantHeartBlockEntity creakingHeartBlockEntity) {
			creakingHeartBlockEntity.killPuppet(null);
		}

		super.onStateReplaced(state, world, pos, newState, moved);
	}
	protected void onExploded(BlockState state, ServerWorld world, BlockPos pos, Explosion explosion, BiConsumer<ItemStack, BlockPos> stackMerger) {
		BlockEntity var8 = world.getBlockEntity(pos);
		if (var8 instanceof CreakingVariantHeartBlockEntity creakingHeartBlockEntity) {
			if (explosion instanceof ExplosionImpl explosionImpl) {
				if (explosion.getDestructionType().destroysBlocks()) {
					creakingHeartBlockEntity.killPuppet(explosionImpl.getDamageSource());
					LivingEntity var9 = explosion.getCausingEntity();
					if (var9 instanceof PlayerEntity playerEntity) {
						if (explosion.getDestructionType().destroysBlocks()) {
							this.dropExperienceOnBreak(playerEntity, state, world, pos);
						}
					}
				}
			}
		}
		super.onExploded(state, world, pos, explosion, stackMerger);
	}
	public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		BlockEntity var6 = world.getBlockEntity(pos);
		if (var6 instanceof CreakingVariantHeartBlockEntity creakingHeartBlockEntity) {
			creakingHeartBlockEntity.killPuppet(player.getDamageSources().playerAttack(player));
			this.dropExperienceOnBreak(player, state, world, pos);
		}
		return super.onBreak(world, pos, state, player);
	}
	private void dropExperienceOnBreak(PlayerEntity player, BlockState state, World world, BlockPos pos) {
		if (!player.isCreative() && !player.isSpectator() && state.get(NATURAL) && world instanceof ServerWorld serverWorld) {
			this.dropExperience(serverWorld, pos, world.random.nextBetween(20, 24));
		}
	}
	protected boolean hasComparatorOutput(BlockState state) {
		return true;
	}
	protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		if (!(Boolean)state.get(ACTIVE)) {
			return 0;
		} else {
			BlockEntity var5 = world.getBlockEntity(pos);
			if (var5 instanceof CreakingVariantHeartBlockEntity creakingHeartBlockEntity) {
				return creakingHeartBlockEntity.getComparatorOutput();
			} else {
				return 0;
			}
		}
	}
	static {
		AXIS = Properties.AXIS;
		ACTIVE = Properties.ACTIVE;
		NATURAL = Properties.NATURAL;
	}
}