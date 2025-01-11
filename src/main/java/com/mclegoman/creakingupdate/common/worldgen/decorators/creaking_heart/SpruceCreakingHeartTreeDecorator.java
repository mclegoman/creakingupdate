package com.mclegoman.creakingupdate.common.worldgen.decorators.creaking_heart;

import com.mclegoman.creakingupdate.common.block.BlockRegistry;
import com.mclegoman.creakingupdate.common.block.creaking_heart.oak.OakCreakingHeartBlock;
import com.mclegoman.creakingupdate.common.worldgen.decorators.DecoratorRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.PillarBlock;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpruceCreakingHeartTreeDecorator extends TreeDecorator {
	public static final MapCodec<SpruceCreakingHeartTreeDecorator> CODEC = Codec.floatRange(0.0F, 1.0F).fieldOf("probability").xmap(SpruceCreakingHeartTreeDecorator::new, (treeDecorator) -> treeDecorator.probability);
	private final float probability;

	public SpruceCreakingHeartTreeDecorator(float probability) {
		this.probability = probability;
	}

	protected TreeDecoratorType<?> getType() {
		return DecoratorRegistry.Tree.oakCreakingHeart;
	}

	public void generate(Generator generator) {
		Random random = generator.getRandom();
		List<BlockPos> list = generator.getLogPositions();
		if (!list.isEmpty()) {
			if (!(random.nextFloat() >= this.probability)) {
				List<BlockPos> list2 = new ArrayList<>(list);
				Util.shuffle(list2, random);
				Optional<BlockPos> optional = list2.stream().filter((pos) -> {
					Direction[] verticalDirections = new Direction[]{Direction.UP, Direction.DOWN};
					for (Direction direction : verticalDirections) {
						if (!generator.matches(pos.offset(direction), (state) -> state.isIn(BlockTags.SPRUCE_LOGS) && state.get(PillarBlock.AXIS).isVertical())) return false;
					}
					return true;
				}).findFirst();
				optional.ifPresent(blockPos -> generator.replace(blockPos, BlockRegistry.spruceCreakingHeart.block().getDefaultState().with(OakCreakingHeartBlock.ACTIVE, true).with(OakCreakingHeartBlock.NATURAL, true)));
			}
		}
	}
}
