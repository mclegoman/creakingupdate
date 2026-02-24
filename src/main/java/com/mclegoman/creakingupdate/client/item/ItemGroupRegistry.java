package com.mclegoman.creakingupdate.client.item;

import com.mclegoman.creakingupdate.common.block.BlockRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;

public class ItemGroupRegistry {
	public static void init() {
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> {
			content.addBefore(Items.CREAKING_HEART, BlockRegistry.oakCreakingHeart.item());
			content.addAfter(BlockRegistry.oakCreakingHeart.item(), BlockRegistry.spruceCreakingHeart.item());
			content.addAfter(BlockRegistry.spruceCreakingHeart.item(), BlockRegistry.birchCreakingHeart.item());
			content.addAfter(BlockRegistry.birchCreakingHeart.item(), BlockRegistry.jungleCreakingHeart.item());
			content.addAfter(BlockRegistry.jungleCreakingHeart.item(), BlockRegistry.acaciaCreakingHeart.item());
			content.addAfter(BlockRegistry.acaciaCreakingHeart.item(), BlockRegistry.darkOakCreakingHeart.item());
			content.addAfter(BlockRegistry.darkOakCreakingHeart.item(), BlockRegistry.mangroveCreakingHeart.item());
			content.addAfter(BlockRegistry.mangroveCreakingHeart.item(), BlockRegistry.cherryCreakingHeart.item());
			content.addAfter(Items.CREAKING_HEART, BlockRegistry.bambooCreakingHeart.item());
		});
	}
}
