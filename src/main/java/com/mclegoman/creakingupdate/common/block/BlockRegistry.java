package com.mclegoman.creakingupdate.common.block;

import com.mclegoman.creakingupdate.common.CreakingUpdate;
import com.mclegoman.creakingupdate.common.block.creaking_heart.acacia.AcaciaCreakingHeartBlock;
import com.mclegoman.creakingupdate.common.block.creaking_heart.bamboo.BambooCreakingHeartBlock;
import com.mclegoman.creakingupdate.common.block.creaking_heart.birch.BirchCreakingHeartBlock;
import com.mclegoman.creakingupdate.common.block.creaking_heart.cherry.CherryCreakingHeartBlock;
import com.mclegoman.creakingupdate.common.block.creaking_heart.dark_oak.DarkOakCreakingHeartBlock;
import com.mclegoman.creakingupdate.common.block.creaking_heart.jungle.JungleCreakingHeartBlock;
import com.mclegoman.creakingupdate.common.block.creaking_heart.mangrove.MangroveCreakingHeartBlock;
import com.mclegoman.creakingupdate.common.block.creaking_heart.oak.OakCreakingHeartBlock;
import com.mclegoman.creakingupdate.common.block.creaking_heart.spruce.SpruceCreakingHeartBlock;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class BlockRegistry {
	public static final BlockItemPair oakCreakingHeart;
	public static final BlockItemPair spruceCreakingHeart;
	public static final BlockItemPair birchCreakingHeart;
	public static final BlockItemPair jungleCreakingHeart;
	public static final BlockItemPair acaciaCreakingHeart;
	public static final BlockItemPair darkOakCreakingHeart;
	public static final BlockItemPair mangroveCreakingHeart;
	public static final BlockItemPair cherryCreakingHeart;
	public static final BlockItemPair bambooCreakingHeart;
	public static void init() {
	}
	public static BlockItemPair registerCreakingHeart(String type, Function<AbstractBlock.Settings, Block> factory) {
		return registerBlockWithItem(Identifier.of(CreakingUpdate.modID, type + "_creaking_heart"), factory, AbstractBlock.Settings.create().mapColor(MapColor.ORANGE).instrument(NoteBlockInstrument.BASEDRUM).strength(10.0F).sounds(BlockSoundGroup.CREAKING_HEART));
	}
	public static BlockItemPair registerBlockWithItem(Identifier id, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
		Block registeredBlock = Blocks.register(RegistryKey.of(RegistryKeys.BLOCK, id), factory, settings);
		Item registeredItem = Items.register(registeredBlock, BlockItem::new, new Item.Settings());
		return new BlockItemPair(registeredBlock, registeredItem);
	}
	static {
		oakCreakingHeart = registerCreakingHeart("oak", OakCreakingHeartBlock::new);
		spruceCreakingHeart = registerCreakingHeart("spruce", SpruceCreakingHeartBlock::new);
		birchCreakingHeart = registerCreakingHeart("birch", BirchCreakingHeartBlock::new);
		jungleCreakingHeart = registerCreakingHeart("jungle", JungleCreakingHeartBlock::new);
		acaciaCreakingHeart = registerCreakingHeart("acacia", AcaciaCreakingHeartBlock::new);
		darkOakCreakingHeart = registerCreakingHeart("dark_oak", DarkOakCreakingHeartBlock::new);
		mangroveCreakingHeart = registerCreakingHeart("mangrove", MangroveCreakingHeartBlock::new);
		cherryCreakingHeart = registerCreakingHeart("cherry", CherryCreakingHeartBlock::new);
		bambooCreakingHeart = registerCreakingHeart("bamboo", BambooCreakingHeartBlock::new);
	}
	public record BlockItemPair(Block block, Item item) {}
}
