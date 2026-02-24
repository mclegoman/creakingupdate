package com.mclegoman.creakingupdate.common.block.creaking_heart;

import com.mclegoman.creakingupdate.common.entity.CreakingVariant;
import com.mojang.datafixers.util.Either;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LargeEntitySpawnHelper;
import net.minecraft.entity.LargeEntitySpawnHelper.Requirements;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.CreakingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.TrailParticleEffect;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class CreakingVariantHeartBlockEntity extends BlockEntity {
	@Nullable
	public Either<CreakingEntity, UUID> creakingPuppet;
	public long ticks;
	public int creakingUpdateTimer;
	public int trailParticlesSpawnTimer;
	@Nullable public Vec3d lastCreakingPuppetPos;
	public int comparatorOutput;
	private final String variant;
	public CreakingVariantHeartBlockEntity(BlockEntityType<? extends CreakingVariantHeartBlockEntity> blockEntityType, BlockPos pos, BlockState state, String variant) {
		super(blockEntityType, pos, state);
		this.variant = variant;
	}
	public static Block getBlock() {
		return null;
	}

	protected double getDistanceToPuppet() {
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

	protected Optional<CreakingEntity> getCreakingPuppet() {
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
	protected static CreakingEntity spawnCreakingPuppet(ServerWorld world, CreakingVariantHeartBlockEntity blockEntity) {
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

	protected void spawnTrailParticles(ServerWorld world, int count, boolean towardsPuppet) {
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
