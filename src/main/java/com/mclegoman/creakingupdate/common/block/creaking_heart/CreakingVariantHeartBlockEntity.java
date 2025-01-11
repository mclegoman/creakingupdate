package com.mclegoman.creakingupdate.common.block.creaking_heart;

import com.mclegoman.creakingupdate.common.entity.CreakingVariant;
import com.mojang.datafixers.util.Either;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MultifaceBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LargeEntitySpawnHelper;
import net.minecraft.entity.LargeEntitySpawnHelper.Requirements;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.CreakingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.TrailParticleEffect;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.IterationState;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.GameEvent.Emitter;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;

public class CreakingVariantHeartBlockEntity extends BlockEntity {
	@Nullable
	private Either<CreakingEntity, UUID> creakingPuppet;
	private long ticks;
	private int creakingUpdateTimer;
	private int trailParticlesSpawnTimer;
	@Nullable private Vec3d lastCreakingPuppetPos;
	private int comparatorOutput;
	private final String variant;
	public CreakingVariantHeartBlockEntity(BlockEntityType<? extends CreakingVariantHeartBlockEntity> blockEntityType, BlockPos pos, BlockState state, String variant) {
		super(blockEntityType, pos, state);
		this.variant = variant;
	}
	public static Block getBlock() {
		return null;
	}
	public static TagKey<Block> getLogs() {
		return CreakingVariantHeartBlock.getLogs();
	}
	public static void tick(World world, BlockPos pos, BlockState state, CreakingVariantHeartBlockEntity blockEntity) {
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
					if (!CreakingVariantHeartBlock.shouldBeEnabled(state, world, pos)) {
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

						if (!CreakingVariantHeartBlock.shouldBeEnabled(state, world, pos) && blockEntity.creakingPuppet == null) {
							world.setBlockState(pos, state.with(CreakingVariantHeartBlock.ACTIVE, false), 3);
						}
					}

				}
			}
		}
	}

	private double getDistanceToPuppet() {
		return this.getCreakingPuppet().map((creaking) -> Math.sqrt(creaking.squaredDistanceTo(Vec3d.ofBottomCenter(this.getPos())))).orElse(0.0);
	}

	private void clearCreakingPuppet() {
		this.creakingPuppet = null;
		this.markDirty();
	}

	public void setCreakingPuppet(CreakingEntity creakingPuppet) {
		this.creakingPuppet = Either.left(creakingPuppet);
		this.markDirty();
	}

	public void setCreakingPuppetFromUuid(UUID creakingPuppetUuid) {
		this.creakingPuppet = Either.right(creakingPuppetUuid);
		this.ticks = 0L;
		this.markDirty();
	}

	private Optional<CreakingEntity> getCreakingPuppet() {
		if (this.creakingPuppet != null) {
			if (this.creakingPuppet.left().isPresent()) {
				CreakingEntity creakingEntity = this.creakingPuppet.left().get();
				if (!creakingEntity.isRemoved()) {
					return Optional.of(creakingEntity);
				}

				this.setCreakingPuppetFromUuid(creakingEntity.getUuid());
			}

			World var2 = this.world;
			if (var2 instanceof ServerWorld serverWorld) {
				if (this.creakingPuppet.right().isPresent()) {
					UUID uUID = this.creakingPuppet.right().get();
					Entity entity = serverWorld.getEntity(uUID);
					if (entity instanceof CreakingEntity creakingEntity2) {
						this.setCreakingPuppet(creakingEntity2);
						return Optional.of(creakingEntity2);
					}

					if (this.ticks >= 30L) {
						this.clearCreakingPuppet();
					}

					return Optional.empty();
				}
			}

		}
		return Optional.empty();
	}

	@Nullable
	private static CreakingEntity spawnCreakingPuppet(ServerWorld world, CreakingVariantHeartBlockEntity blockEntity) {
		BlockPos blockPos = blockEntity.getPos();
		Optional<CreakingEntity> optional = LargeEntitySpawnHelper.trySpawnAt(EntityType.CREAKING, SpawnReason.SPAWNER, world, blockPos, 5, 16, 8, Requirements.CREAKING, true);
		if (optional.isEmpty()) {
			return null;
		} else {
			CreakingEntity creakingEntity = optional.get();
			world.emitGameEvent(creakingEntity, GameEvent.ENTITY_PLACE, creakingEntity.getPos());
			world.sendEntityStatus(creakingEntity, (byte)60);
			creakingEntity.initHomePos(blockPos);
			((CreakingVariant)creakingEntity).atnmm$setVariant(blockEntity.variant);
			return creakingEntity;
		}
	}

	public BlockEntityUpdateS2CPacket toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}

	public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
		return this.createComponentlessNbt(registries);
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
							this.world.emitGameEvent(GameEvent.BLOCK_PLACE, pos, Emitter.of(this.world.getBlockState(pos)));
						});
					}

					this.trailParticlesSpawnTimer = 100;
					this.lastCreakingPuppetPos = creakingEntity.getBoundingBox().getCenter();
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
				if (!this.world.getBlockState(pos).isIn(getLogs())) return IterationState.ACCEPT;
				else {
					Iterator<Direction> var3 = Util.copyShuffled(Direction.values(), this.world.random).iterator();

					BlockPos blockPos;
					BlockState blockState;
					Direction direction2;
					do {
						if (!var3.hasNext()) return IterationState.ACCEPT;
						Direction direction = var3.next();
						blockPos = pos.offset(direction);
						blockState = this.world.getBlockState(blockPos);
						direction2 = direction.getOpposite();
						if (blockState.isAir()) blockState = Blocks.RESIN_CLUMP.getDefaultState();
						else if (blockState.isOf(Blocks.WATER) && blockState.getFluidState().isStill()) blockState = Blocks.RESIN_CLUMP.getDefaultState().with(MultifaceBlock.WATERLOGGED, true);
					} while (!blockState.isOf(Blocks.RESIN_CLUMP) || MultifaceBlock.hasDirection(blockState, direction2));
					this.world.setBlockState(blockPos, blockState.with(MultifaceBlock.getProperty(direction2), true), 3);
					mutable.setValue(blockPos);
					return IterationState.STOP;
				}
			});
		}
		return Optional.ofNullable(mutable.getValue());
	}

	private void spawnTrailParticles(ServerWorld world, int count, boolean towardsPuppet) {
		Object var5 = this.getCreakingPuppet().orElse(null);
		if (var5 instanceof CreakingEntity creakingEntity) {
			int i = towardsPuppet ? 16545810 : 6250335;
			Random random = world.random;
			for(double d = 0.0; d < (double)count; ++d) {
				Box box = creakingEntity.getBoundingBox();
				Vec3d vec3d = box.getMinPos().add(random.nextDouble() * box.getLengthX(), random.nextDouble() * box.getLengthY(), random.nextDouble() * box.getLengthZ());
				Vec3d vec3d2 = Vec3d.of(this.getPos()).add(random.nextDouble(), random.nextDouble(), random.nextDouble());
				if (towardsPuppet) {
					Vec3d vec3d3 = vec3d;
					vec3d = vec3d2;
					vec3d2 = vec3d3;
				}
				TrailParticleEffect trailParticleEffect = new TrailParticleEffect(vec3d2, i, random.nextInt(40) + 10);
				world.spawnParticles(trailParticleEffect, true, true, vec3d.x, vec3d.y, vec3d.z, 1, 0.0, 0.0, 0.0, 0.0);
			}
		}
	}

	public void killPuppet(@Nullable DamageSource damageSource) {
		Object var3 = this.getCreakingPuppet().orElse(null);
		if (var3 instanceof CreakingEntity creakingEntity) {
			if (damageSource == null) creakingEntity.finishCrumbling();
			else {
				creakingEntity.killFromHeart(damageSource);
				creakingEntity.setCrumbling();
				creakingEntity.setHealth(0.0F);
			}
			this.clearCreakingPuppet();
		}

	}

	public boolean isPuppet(CreakingEntity creaking) {
		return this.getCreakingPuppet().map((puppet) -> puppet == creaking).orElse(false);
	}

	public int getComparatorOutput() {
		return this.comparatorOutput;
	}

	public int calcComparatorOutput() {
		if (this.creakingPuppet != null && this.getCreakingPuppet().isPresent()) {
			double d = this.getDistanceToPuppet();
			double e = Math.clamp(d, 0.0, 32.0) / 32.0;
			return 15 - (int)Math.floor(e * 15.0);
		} else return 0;
	}

	protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
		super.readNbt(nbt, registries);
		if (nbt.contains("creaking")) this.setCreakingPuppetFromUuid(nbt.getUuid("creaking"));
		else this.clearCreakingPuppet();
	}

	protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
		super.writeNbt(nbt, registries);
		if (this.creakingPuppet != null) nbt.putUuid("creaking", this.creakingPuppet.map(Entity::getUuid, (uuid) -> uuid));
	}
}
