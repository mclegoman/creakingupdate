package com.mclegoman.creakingupdate.mixin.common.creaking;

import com.mclegoman.creakingupdate.common.block.BlockRegistry;
import com.mclegoman.creakingupdate.common.block.creaking_heart.CreakingVariantHeartBlockEntity;
import com.mclegoman.creakingupdate.common.block.creaking_heart.acacia.AcaciaCreakingHeartBlockEntity;
import com.mclegoman.creakingupdate.common.block.creaking_heart.bamboo.BambooCreakingHeartBlockEntity;
import com.mclegoman.creakingupdate.common.block.creaking_heart.birch.BirchCreakingHeartBlockEntity;
import com.mclegoman.creakingupdate.common.block.creaking_heart.cherry.CherryCreakingHeartBlockEntity;
import com.mclegoman.creakingupdate.common.block.creaking_heart.dark_oak.DarkOakCreakingHeartBlockEntity;
import com.mclegoman.creakingupdate.common.block.creaking_heart.jungle.JungleCreakingHeartBlockEntity;
import com.mclegoman.creakingupdate.common.block.creaking_heart.mangrove.MangroveCreakingHeartBlockEntity;
import com.mclegoman.creakingupdate.common.block.creaking_heart.oak.OakCreakingHeartBlockEntity;
import com.mclegoman.creakingupdate.common.block.creaking_heart.spruce.SpruceCreakingHeartBlockEntity;
import com.mclegoman.creakingupdate.common.entity.CreakingVariant;
import com.mclegoman.creakingupdate.common.entity.CreakingVariantComponent;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CreakingHeartBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CreakingHeartBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.CreakingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(priority = 100, value = CreakingEntity.class)
public abstract class CreakingEntityMixin extends HostileEntity implements CreakingVariant {
	protected CreakingEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}
	@Shadow @Nullable public abstract BlockPos getHomePos();
	@Shadow private int invulnerableAnimationTimer;
	@Shadow public abstract PlayerEntity becomeAngryAndGetPlayer(DamageSource damageSource);

	@Shadow public abstract void updateCrumblingEyeFlicker();

	@Shadow protected abstract void tickAttackAnimation();

	@Inject(method = "damage", at = @At("HEAD"))
	private void atnmm$damage(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		BlockPos blockPos = this.getHomePos();
		if (blockPos != null && !source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			if (!this.isInvulnerableTo(world, source) && this.invulnerableAnimationTimer <= 0 && !this.isDead()) {
				PlayerEntity playerEntity = this.becomeAngryAndGetPlayer(source);
				Entity entity = source.getSource();
				if (entity instanceof LivingEntity || entity instanceof ProjectileEntity || playerEntity != null) {
					BlockEntity var8 = this.getWorld().getBlockEntity(blockPos);
					if ((var8 instanceof OakCreakingHeartBlockEntity blockEntity)) {
						if (blockEntity.isPuppet((CreakingEntity) (Object)this)) {
							if (playerEntity != null) blockEntity.onPuppetDamage();
							this.playHurtSound(source);
						}
					} else if ((var8 instanceof SpruceCreakingHeartBlockEntity blockEntity)) {
						if (blockEntity.isPuppet((CreakingEntity) (Object)this)) {
							if (playerEntity != null) blockEntity.onPuppetDamage();
							this.playHurtSound(source);
						}
					} else if ((var8 instanceof BirchCreakingHeartBlockEntity blockEntity)) {
						if (blockEntity.isPuppet((CreakingEntity) (Object)this)) {
							if (playerEntity != null) blockEntity.onPuppetDamage();
							this.playHurtSound(source);
						}
					} else if ((var8 instanceof AcaciaCreakingHeartBlockEntity blockEntity)) {
						if (blockEntity.isPuppet((CreakingEntity) (Object)this)) {
							if (playerEntity != null) blockEntity.onPuppetDamage();
							this.playHurtSound(source);
						}
					} else if ((var8 instanceof BambooCreakingHeartBlockEntity blockEntity)) {
						if (blockEntity.isPuppet((CreakingEntity) (Object)this)) {
							if (playerEntity != null) blockEntity.onPuppetDamage();
							this.playHurtSound(source);
						}
					} else if ((var8 instanceof CherryCreakingHeartBlockEntity blockEntity)) {
						if (blockEntity.isPuppet((CreakingEntity) (Object)this)) {
							if (playerEntity != null) blockEntity.onPuppetDamage();
							this.playHurtSound(source);
						}
					} else if ((var8 instanceof DarkOakCreakingHeartBlockEntity blockEntity)) {
						if (blockEntity.isPuppet((CreakingEntity) (Object)this)) {
							if (playerEntity != null) blockEntity.onPuppetDamage();
							this.playHurtSound(source);
						}
					} else if ((var8 instanceof JungleCreakingHeartBlockEntity blockEntity)) {
						if (blockEntity.isPuppet((CreakingEntity) (Object)this)) {
							if (playerEntity != null) blockEntity.onPuppetDamage();
							this.playHurtSound(source);
						}
					} else if ((var8 instanceof MangroveCreakingHeartBlockEntity blockEntity)) {
						if (blockEntity.isPuppet((CreakingEntity) (Object)this)) {
							if (playerEntity != null) blockEntity.onPuppetDamage();
							this.playHurtSound(source);
						}
					}
				}
			}
		}
	}
	@Inject(method = "finishCrumbling", at = @At("HEAD"), cancellable = true)
	public void atnmm$finishCrumbling(CallbackInfo ci) {
		World var2 = this.getWorld();
		if (var2 instanceof ServerWorld serverWorld) {
			Box box = this.getBoundingBox();
			Vec3d vec3d = box.getCenter();
			double d = box.getLengthX() * 0.3;
			double e = box.getLengthY() * 0.3;
			double f = box.getLengthZ() * 0.3;

			BlockState woodState = Blocks.PALE_OAK_WOOD.getDefaultState();
			BlockState heartState = Blocks.CREAKING_HEART.getDefaultState().with(CreakingHeartBlock.ACTIVE, true);

			if (this.atnmm$getVariant().equalsIgnoreCase("oak")) {
				woodState = Blocks.OAK_WOOD.getDefaultState();
				heartState = BlockRegistry.oakCreakingHeart.block().getDefaultState().with(CreakingHeartBlock.ACTIVE, true);
			} else if (this.atnmm$getVariant().equalsIgnoreCase("spruce")) {
				woodState = Blocks.SPRUCE_WOOD.getDefaultState();
				heartState = BlockRegistry.spruceCreakingHeart.block().getDefaultState().with(CreakingHeartBlock.ACTIVE, true);
			} else if (this.atnmm$getVariant().equalsIgnoreCase("birch")) {
				woodState = Blocks.BIRCH_WOOD.getDefaultState();
				heartState = BlockRegistry.birchCreakingHeart.block().getDefaultState().with(CreakingHeartBlock.ACTIVE, true);
			} else if (this.atnmm$getVariant().equalsIgnoreCase("jungle")) {
				woodState = Blocks.JUNGLE_WOOD.getDefaultState();
				heartState = BlockRegistry.jungleCreakingHeart.block().getDefaultState().with(CreakingHeartBlock.ACTIVE, true);
			} else if (this.atnmm$getVariant().equalsIgnoreCase("acacia")) {
				woodState = Blocks.ACACIA_WOOD.getDefaultState();
				heartState = BlockRegistry.acaciaCreakingHeart.block().getDefaultState().with(CreakingHeartBlock.ACTIVE, true);
			} else if (this.atnmm$getVariant().equalsIgnoreCase("dark_oak")) {
				woodState = Blocks.DARK_OAK_WOOD.getDefaultState();
				heartState = BlockRegistry.darkOakCreakingHeart.block().getDefaultState().with(CreakingHeartBlock.ACTIVE, true);
			} else if (this.atnmm$getVariant().equalsIgnoreCase("mangrove")) {
				woodState = Blocks.MANGROVE_WOOD.getDefaultState();
				heartState = BlockRegistry.mangroveCreakingHeart.block().getDefaultState().with(CreakingHeartBlock.ACTIVE, true);
			} else if (this.atnmm$getVariant().equalsIgnoreCase("cherry")) {
				woodState = Blocks.CHERRY_WOOD.getDefaultState();
				heartState = BlockRegistry.cherryCreakingHeart.block().getDefaultState().with(CreakingHeartBlock.ACTIVE, true);
			} else if (this.atnmm$getVariant().equalsIgnoreCase("bamboo")) {
				woodState = Blocks.BAMBOO_BLOCK.getDefaultState();
				heartState = BlockRegistry.bambooCreakingHeart.block().getDefaultState().with(CreakingHeartBlock.ACTIVE, true);
			}

			serverWorld.spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK_CRUMBLE, woodState), vec3d.x, vec3d.y, vec3d.z, 100, d, e, f, 0.0);
			serverWorld.spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK_CRUMBLE, heartState), vec3d.x, vec3d.y, vec3d.z, 10, d, e, f, 0.0);
		}

		this.playSound(this.getDeathSound());
		this.remove(RemovalReason.DISCARDED);
		ci.cancel();
	}
	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	public void atnmm$tick(CallbackInfo ci) {
		if (!this.getWorld().isClient) {
			BlockPos blockPos = this.getHomePos();
			if (blockPos != null) {
				boolean isPuppet = false;
				BlockEntity blockEntity = this.getWorld().getBlockEntity(blockPos);
				if (blockEntity instanceof CreakingHeartBlockEntity creakingHeartBlockEntity) {
					if (creakingHeartBlockEntity.isPuppet((CreakingEntity) (Object)this)) isPuppet = true;
				} else if (blockEntity instanceof CreakingVariantHeartBlockEntity creakingHeartBlockEntity) {
					if (creakingHeartBlockEntity.isPuppet((CreakingEntity) (Object)this)) isPuppet = true;
				}
				if (!isPuppet) this.setHealth(0.0F);
			}
		}

		super.tick();
		if (this.getWorld().isClient) {
			this.tickAttackAnimation();
			this.updateCrumblingEyeFlicker();
		}
		ci.cancel();
	}
	@Unique
	private CreakingVariantComponent variantComponent;
	@Unique
	private static final TrackedData<String> variant;
	@Inject(method = "<init>", at = @At("TAIL"))
	private void atnmm$init(EntityType<? extends CreakingEntity> entityType, World world, CallbackInfo ci) {
		this.variantComponent = new CreakingVariantComponent(this.dataTracker, variant);
	}
	@Inject(method = "initDataTracker", at = @At("TAIL"))
	private void atnmm$initDataTracker(DataTracker.Builder builder, CallbackInfo ci) {
		builder.add(variant, atnmm$getVariant());
	}
	@Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
	private void atnmm$writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
		this.variantComponent.writeNbt(nbt);
	}
	@Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
	private void atnmm$readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
		this.variantComponent.readNbt(nbt);
	}
	public String atnmm$getVariant() {
		return this.variantComponent != null ? this.variantComponent.getVariant() : "";
	}
	public void atnmm$setVariant(String value) {
		this.variantComponent.setVariant(value);
	}
	static {
		variant = DataTracker.registerData(CreakingEntityMixin.class, TrackedDataHandlerRegistry.STRING);
	}
}