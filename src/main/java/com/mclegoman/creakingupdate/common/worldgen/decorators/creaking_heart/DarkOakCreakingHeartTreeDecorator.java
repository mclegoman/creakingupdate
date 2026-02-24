package com.mclegoman.creakingupdate.common.worldgen.decorators.creaking_heart;

import com.mclegoman.creakingupdate.common.block.BlockRegistry;
import com.mclegoman.creakingupdate.common.worldgen.decorators.DecoratorRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.CreakingHeartBlock;
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

public class DarkOakCreakingHeartTreeDecorator extends TreeDecorator {
	public static final MapCodec<DarkOakCreakingHeartTreeDecorator> CODEC = Codec.floatRange(0.0F, 1.0F).fieldOf("probability").xmap(DarkOakCreakingHeartTreeDecorator::new, (treeDecorator) -> treeDecorator.probability);
	private final float probability;
	public DarkOakCreakingHeartTreeDecorator(float probability) {
		this.probability = probability;
	}
	protected TreeDecoratorType<?> getType() {
		return DecoratorRegistry.Tree.darkOakCreakingHeart;
	}

	public void generate(TreeDecorator.Generator generator) {
		Random random = generator.getRandom();
		List<BlockPos> list = generator.getLogPositions();
		if (!list.isEmpty()) {
			if (!(random.nextFloat() >= this.probability)) {
				List<BlockPos> list2 = new ArrayList<>(list);
				Util.shuffle(list2, random);
				Optional<BlockPos> optional = list2.stream().filter((pos) -> {
					Direction[] var2 = Direction.values();
					for (Direction direction : var2) {
						if (!generator.matches(pos.offset(direction), (state) -> state.isIn(BlockTags.DARK_OAK_LOGS))) return false;
					}
					return true;
				}).findFirst();
				optional.ifPresent(blockPos -> generator.replace(blockPos, BlockRegistry.darkOakCreakingHeart.block().getDefaultState().with(CreakingHeartBlock.ACTIVE, true).with(CreakingHeartBlock.NATURAL, true)));
			}
		}
	}
}