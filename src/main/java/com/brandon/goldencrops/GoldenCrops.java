
package com.brandon.goldencrops;

import com.brandon.goldencrops.loot.ModLootTables;
import com.brandon.goldencrops.item.ModItems;
import com.brandon.goldencrops.block.ModBlocks;
import com.brandon.goldencrops.item.ModItemGroups;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GoldenCrops implements ModInitializer {

	public static final String MOD_ID = "golden-crops";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Golden Crops!");
		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModItemGroups.registerModItemGroups();
		ModLootTables.registerModLootTables();
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
