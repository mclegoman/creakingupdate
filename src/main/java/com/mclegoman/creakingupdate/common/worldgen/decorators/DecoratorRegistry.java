package com.mclegoman.creakingupdate.common.worldgen.decorators;

import com.mclegoman.creakingupdate.common.CreakingUpdate;
import com.mclegoman.creakingupdate.common.worldgen.decorators.creaking_heart.*;
import com.mojang.serialization.MapCodec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class DecoratorRegistry {
	public static void init() {
		Tree.init();
	}
	public static class Tree {
		public static final TreeDecoratorType<OakCreakingHeartTreeDecorator> oakCreakingHeart;
		public static final TreeDecoratorType<SpruceCreakingHeartTreeDecorator> spruceCreakingHeart;
		public static final TreeDecoratorType<BirchCreakingHeartTreeDecorator> birchCreakingHeart;
		public static final TreeDecoratorType<JungleCreakingHeartTreeDecorator> jungleCreakingHeart;
		public static final TreeDecoratorType<AcaciaCreakingHeartTreeDecorator> acaciaCreakingHeart;
		public static final TreeDecoratorType<DarkOakCreakingHeartTreeDecorator> darkOakCreakingHeart;
		public static final TreeDecoratorType<MangroveCreakingHeartTreeDecorator> mangroveCreakingHeart;
		public static final TreeDecoratorType<CherryCreakingHeartTreeDecorator> cherryCreakingHeart;
		public static final TreeDecoratorType<AzaleaCreakingHeartTreeDecorator> azaleaCreakingHeart;
		public static void init() {
		}
		private static <P extends TreeDecorator> TreeDecoratorType<P> register(Identifier id, MapCodec<P> codec) {
			return Registry.register(Registries.TREE_DECORATOR_TYPE, id, new TreeDecoratorType<>(codec));
		}
		static {
			oakCreakingHeart = register(Identifier.of(CreakingUpdate.modID, "oak_creaking_heart"), OakCreakingHeartTreeDecorator.CODEC);
			spruceCreakingHeart = register(Identifier.of(CreakingUpdate.modID, "spruce_creaking_heart"), SpruceCreakingHeartTreeDecorator.CODEC);
			birchCreakingHeart = register(Identifier.of(CreakingUpdate.modID, "birch_creaking_heart"), BirchCreakingHeartTreeDecorator.CODEC);
			jungleCreakingHeart = register(Identifier.of(CreakingUpdate.modID, "jungle_creaking_heart"), JungleCreakingHeartTreeDecorator.CODEC);
			acaciaCreakingHeart = register(Identifier.of(CreakingUpdate.modID, "acacia_creaking_heart"), AcaciaCreakingHeartTreeDecorator.CODEC);
			darkOakCreakingHeart = register(Identifier.of(CreakingUpdate.modID, "dark_oak_creaking_heart"), DarkOakCreakingHeartTreeDecorator.CODEC);
			mangroveCreakingHeart = register(Identifier.of(CreakingUpdate.modID, "mangrove_creaking_heart"), MangroveCreakingHeartTreeDecorator.CODEC);
			cherryCreakingHeart = register(Identifier.of(CreakingUpdate.modID, "cherry_creaking_heart"), CherryCreakingHeartTreeDecorator.CODEC);
			azaleaCreakingHeart = register(Identifier.of(CreakingUpdate.modID, "azalea_creaking_heart"), AzaleaCreakingHeartTreeDecorator.CODEC);
		}
	}
}
