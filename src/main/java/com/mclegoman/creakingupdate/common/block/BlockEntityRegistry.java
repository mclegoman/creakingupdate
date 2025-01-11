package com.mclegoman.creakingupdate.common.block;

import com.mclegoman.creakingupdate.common.CreakingUpdate;
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
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BlockEntityRegistry {
	public static final BlockEntityType<OakCreakingHeartBlockEntity> oakCreakingHeart;
	public static final BlockEntityType<SpruceCreakingHeartBlockEntity> spruceCreakingHeart;
	public static final BlockEntityType<BirchCreakingHeartBlockEntity> birchCreakingHeart;
	public static final BlockEntityType<JungleCreakingHeartBlockEntity> jungleCreakingHeart;
	public static final BlockEntityType<AcaciaCreakingHeartBlockEntity> acaciaCreakingHeart;
	public static final BlockEntityType<DarkOakCreakingHeartBlockEntity> darkOakCreakingHeart;
	public static final BlockEntityType<MangroveCreakingHeartBlockEntity> mangroveCreakingHeart;
	public static final BlockEntityType<CherryCreakingHeartBlockEntity> cherryCreakingHeart;
	public static final BlockEntityType<BambooCreakingHeartBlockEntity> bambooCreakingHeart;
	public static void init() {}
	public static <T extends BlockEntityType<?>> T register(Identifier id, T blockEntityType) {
		return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, blockEntityType);
	}
	public static BlockEntityType<? extends BlockEntity> registerCreakingHeartBlockEntity(String type, FabricBlockEntityTypeBuilder.Factory<? extends BlockEntity> factory, Block... blocks) {
		return register(
				Identifier.of(CreakingUpdate.modID, type + "_creaking_heart"),
				FabricBlockEntityTypeBuilder.create(factory, blocks).build()
		);
	}
	static {
		oakCreakingHeart = (BlockEntityType<OakCreakingHeartBlockEntity>) registerCreakingHeartBlockEntity("oak", OakCreakingHeartBlockEntity::new, BlockRegistry.oakCreakingHeart.block());
		spruceCreakingHeart = (BlockEntityType<SpruceCreakingHeartBlockEntity>) registerCreakingHeartBlockEntity("spruce", SpruceCreakingHeartBlockEntity::new, BlockRegistry.spruceCreakingHeart.block());
		birchCreakingHeart = (BlockEntityType<BirchCreakingHeartBlockEntity>) registerCreakingHeartBlockEntity("birch", BirchCreakingHeartBlockEntity::new, BlockRegistry.birchCreakingHeart.block());
		jungleCreakingHeart = (BlockEntityType<JungleCreakingHeartBlockEntity>) registerCreakingHeartBlockEntity("jungle", JungleCreakingHeartBlockEntity::new, BlockRegistry.jungleCreakingHeart.block());
		acaciaCreakingHeart = (BlockEntityType<AcaciaCreakingHeartBlockEntity>) registerCreakingHeartBlockEntity("acacia", AcaciaCreakingHeartBlockEntity::new, BlockRegistry.acaciaCreakingHeart.block());
		darkOakCreakingHeart = (BlockEntityType<DarkOakCreakingHeartBlockEntity>) registerCreakingHeartBlockEntity("dark_oak", DarkOakCreakingHeartBlockEntity::new, BlockRegistry.darkOakCreakingHeart.block());
		mangroveCreakingHeart = (BlockEntityType<MangroveCreakingHeartBlockEntity>) registerCreakingHeartBlockEntity("mangrove", MangroveCreakingHeartBlockEntity::new, BlockRegistry.mangroveCreakingHeart.block());
		cherryCreakingHeart = (BlockEntityType<CherryCreakingHeartBlockEntity>) registerCreakingHeartBlockEntity("cherry", CherryCreakingHeartBlockEntity::new, BlockRegistry.cherryCreakingHeart.block());
		bambooCreakingHeart = (BlockEntityType<BambooCreakingHeartBlockEntity>) registerCreakingHeartBlockEntity("bamboo", BambooCreakingHeartBlockEntity::new, BlockRegistry.bambooCreakingHeart.block());
	}
}
