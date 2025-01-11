package com.mclegoman.creakingupdate.common.block;

import com.mclegoman.creakingupdate.common.CreakingUpdate;
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
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BlockEntityRegistry {
	public static final BlockEntityType<? extends CreakingVariantHeartBlockEntity> oakCreakingHeart;
	public static final BlockEntityType<? extends CreakingVariantHeartBlockEntity> spruceCreakingHeart;
	public static final BlockEntityType<? extends CreakingVariantHeartBlockEntity> birchCreakingHeart;
	public static final BlockEntityType<? extends CreakingVariantHeartBlockEntity> jungleCreakingHeart;
	public static final BlockEntityType<? extends CreakingVariantHeartBlockEntity> acaciaCreakingHeart;
	public static final BlockEntityType<? extends CreakingVariantHeartBlockEntity> darkOakCreakingHeart;
	public static final BlockEntityType<? extends CreakingVariantHeartBlockEntity> mangroveCreakingHeart;
	public static final BlockEntityType<? extends CreakingVariantHeartBlockEntity> cherryCreakingHeart;
	public static final BlockEntityType<? extends CreakingVariantHeartBlockEntity> bambooCreakingHeart;
	public static void init() {}
	public static <T extends BlockEntityType<?>> T register(Identifier id, T blockEntityType) {
		return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, blockEntityType);
	}
	public static BlockEntityType<? extends CreakingVariantHeartBlockEntity> registerCreakingHeartBlockEntity(String type, FabricBlockEntityTypeBuilder.Factory<? extends CreakingVariantHeartBlockEntity> factory, Block... blocks) {
		return register(
				Identifier.of(CreakingUpdate.modID, type + "_creaking_heart"),
				FabricBlockEntityTypeBuilder.create(factory, blocks).build()
		);
	}
	static {
		oakCreakingHeart = registerCreakingHeartBlockEntity("oak", OakCreakingHeartBlockEntity::new, BlockRegistry.oakCreakingHeart.block());
		spruceCreakingHeart = registerCreakingHeartBlockEntity("spruce", SpruceCreakingHeartBlockEntity::new, BlockRegistry.spruceCreakingHeart.block());
		birchCreakingHeart = registerCreakingHeartBlockEntity("birch", BirchCreakingHeartBlockEntity::new, BlockRegistry.birchCreakingHeart.block());
		jungleCreakingHeart = registerCreakingHeartBlockEntity("jungle", JungleCreakingHeartBlockEntity::new, BlockRegistry.jungleCreakingHeart.block());
		acaciaCreakingHeart = registerCreakingHeartBlockEntity("acacia", AcaciaCreakingHeartBlockEntity::new, BlockRegistry.acaciaCreakingHeart.block());
		darkOakCreakingHeart = registerCreakingHeartBlockEntity("dark_oak", DarkOakCreakingHeartBlockEntity::new, BlockRegistry.darkOakCreakingHeart.block());
		mangroveCreakingHeart = registerCreakingHeartBlockEntity("mangrove", MangroveCreakingHeartBlockEntity::new, BlockRegistry.mangroveCreakingHeart.block());
		cherryCreakingHeart = registerCreakingHeartBlockEntity("cherry", CherryCreakingHeartBlockEntity::new, BlockRegistry.cherryCreakingHeart.block());
		bambooCreakingHeart = registerCreakingHeartBlockEntity("bamboo", BambooCreakingHeartBlockEntity::new, BlockRegistry.bambooCreakingHeart.block());
	}
}
