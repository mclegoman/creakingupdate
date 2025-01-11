package com.mclegoman.creakingupdate.common.block.creaking_heart.birch;

import com.mclegoman.creakingupdate.common.block.BlockEntityRegistry;
import com.mclegoman.creakingupdate.common.block.BlockRegistry;
import com.mclegoman.creakingupdate.common.block.creaking_heart.CreakingVariantHeartBlock;
import com.mclegoman.creakingupdate.common.block.creaking_heart.CreakingVariantHeartBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MultifaceBlock;
import net.minecraft.entity.mob.CreakingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.Iterator;
import java.util.Optional;

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
	public static void tick(World world, BlockPos pos, BlockState state, BirchCreakingHeartBlockEntity blockEntity) {
		++blockEntity.ticks;
		if (world instanceof ServerWorld serverWorld) {
			int i = blockEntity.calcComparatorOutput();
			if (blockEntity.comparatorOutput != i) {
				blockEntity.comparatorOutput = i;
				if (getBlock() != null) world.updateComparators(pos, getBlock());
			}

			if (blockEntity.trailParticlesSpawnTimer > 0) {
				if (blockEntity.trailParticlesSpawnTimer > 50) {
					blockEntity.spawnTrailParticles(serverWorld, 1, true);
					blockEntity.spawnTrailParticles(serverWorld, 1, false);
				}

				if (blockEntity.trailParticlesSpawnTimer % 10 == 0 && blockEntity.lastCreakingPuppetPos != null) {
					blockEntity.getCreakingPuppet().ifPresent((creaking) -> {
						blockEntity.lastCreakingPuppetPos = creaking.getBoundingBox().getCenter();
					});
					Vec3d vec3d = Vec3d.ofCenter(pos);
					float f = 0.2F + 0.8F * (float)(100 - blockEntity.trailParticlesSpawnTimer) / 100.0F;
					Vec3d vec3d2 = vec3d.subtract(blockEntity.lastCreakingPuppetPos).multiply(f).add(blockEntity.lastCreakingPuppetPos);
					BlockPos blockPos = BlockPos.ofFloored(vec3d2);
					float g = (float)blockEntity.trailParticlesSpawnTimer / 2.0F / 100.0F + 0.5F;
					serverWorld.playSound(null, blockPos, SoundEvents.BLOCK_CREAKING_HEART_HURT, SoundCategory.BLOCKS, g, 1.0F);
				}

				--blockEntity.trailParticlesSpawnTimer;
			}

			if (blockEntity.creakingUpdateTimer-- < 0) {
				blockEntity.creakingUpdateTimer = blockEntity.world == null ? 20 : blockEntity.world.random.nextInt(5) + 20;
				CreakingEntity creakingEntity;
				if (blockEntity.creakingPuppet == null) {
					if (!BirchCreakingHeartBlock.shouldBeEnabled(state, world, pos)) {
						world.setBlockState(pos, state.with(CreakingVariantHeartBlock.ACTIVE, false), 3);
					} else if (state.get(CreakingVariantHeartBlock.ACTIVE)) {
						if (CreakingVariantHeartBlock.isNightAndNatural()) {
							if (world.getDifficulty() != Difficulty.PEACEFUL) {
								if (serverWorld.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)) {
									PlayerEntity playerEntity = world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 32.0, false);
									if (playerEntity != null) {
										creakingEntity = spawnCreakingPuppet(serverWorld, blockEntity);
										if (creakingEntity != null) {
											blockEntity.setCreakingPuppet(creakingEntity);
											creakingEntity.playSound(SoundEvents.ENTITY_CREAKING_SPAWN);
											world.playSound(null, blockEntity.getPos(), SoundEvents.BLOCK_CREAKING_HEART_SPAWN, SoundCategory.BLOCKS, 1.0F, 1.0F);
										}
									}

								}
							}
						}
					}
				} else {
					Optional<CreakingEntity> optional = blockEntity.getCreakingPuppet();
					if (optional.isPresent()) {
						creakingEntity = optional.get();
						if (!CreakingVariantHeartBlock.isNightAndNatural() || blockEntity.getDistanceToPuppet() > 34.0 || creakingEntity.isStuckWithPlayer()) {
							blockEntity.killPuppet(null);
							return;
						}

						if (!BirchCreakingHeartBlock.shouldBeEnabled(state, world, pos) && blockEntity.creakingPuppet == null) {
							world.setBlockState(pos, state.with(CreakingVariantHeartBlock.ACTIVE, false), 3);
						}
					}

				}
			}
		}
	}

	private Optional<BlockPos> findResinGenerationPos() {
		Mutable<BlockPos> mutable = new MutableObject<>(null);
		if (this.world != null) {
			BlockPos.iterateRecursively(this.pos, 2, 64, (pos, consumer) -> {
				for (Direction direction : Util.copyShuffled(Direction.values(), this.world.random)) {
					BlockPos blockPos = pos.offset(direction);
					if (this.world.getBlockState(blockPos).isIn(getLogs())) consumer.accept(blockPos);
				}

			}, (pos) -> {
				if (!this.world.getBlockState(pos).isIn(getLogs())) return BlockPos.IterationState.ACCEPT;
				else {
					Iterator<Direction> var3 = Util.copyShuffled(Direction.values(), this.world.random).iterator();

					BlockPos blockPos;
					BlockState blockState;
					Direction direction2;
					do {
						if (!var3.hasNext()) return BlockPos.IterationState.ACCEPT;
						Direction direction = var3.next();
						blockPos = pos.offset(direction);
						blockState = this.world.getBlockState(blockPos);
						direction2 = direction.getOpposite();
						if (blockState.isAir()) blockState = Blocks.RESIN_CLUMP.getDefaultState();
						else if (blockState.isOf(Blocks.WATER) && blockState.getFluidState().isStill()) blockState = Blocks.RESIN_CLUMP.getDefaultState().with(MultifaceBlock.WATERLOGGED, true);
					} while (!blockState.isOf(Blocks.RESIN_CLUMP) || MultifaceBlock.hasDirection(blockState, direction2));
					this.world.setBlockState(blockPos, blockState.with(MultifaceBlock.getProperty(direction2), true), 3);
					mutable.setValue(blockPos);
					return BlockPos.IterationState.STOP;
				}
			});
		}
		return Optional.ofNullable(mutable.getValue());
	}

	public void onPuppetDamage() {
		Object var2 = this.getCreakingPuppet().orElse(null);
		if (var2 instanceof CreakingEntity creakingEntity) {
			World var3 = this.world;
			if (var3 instanceof ServerWorld serverWorld) {
				if (this.trailParticlesSpawnTimer <= 0) {
					this.spawnTrailParticles(serverWorld, 20, false);
					int i = this.world.getRandom().nextBetween(2, 3);

					for(int j = 0; j < i; ++j) {
						this.findResinGenerationPos().ifPresent((pos) -> {
							this.world.playSound(null, pos, SoundEvents.BLOCK_RESIN_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
							this.world.emitGameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Emitter.of(this.world.getBlockState(pos)));
						});
					}

					this.trailParticlesSpawnTimer = 100;
					this.lastCreakingPuppetPos = creakingEntity.getBoundingBox().getCenter();
				}
			}
		}
	}
}
