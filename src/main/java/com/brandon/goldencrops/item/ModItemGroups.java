
package com.brandon.goldencrops.item;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;

public class ModItemGroups {

    public static void registerModItemGroups() {

        // Ingredients
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS)
                .register(output -> {
                    output.insertAfter(Items.WHEAT, ModItems.GOLDEN_WHEAT);
                    output.insertAfter(Items.BONE_MEAL, ModItems.GOLDEN_BONE_MEAL);

                });

        // Natural Blocks
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS)
                .register(output -> {
                    output.insertAfter(Items.WHEAT_SEEDS, ModItems.GOLDEN_WHEAT_SEEDS);
                    output.insertAfter(Items.BEETROOT_SEEDS, ModItems.GOLDEN_BEETROOT_SEEDS);
                });

        // Food & Drinks
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register(output -> {
                    output.insertAfter(Items.BREAD, ModItems.GOLDEN_BREAD);
                    output.insertAfter(Items.BEETROOT, ModItems.GOLDEN_BEETROOT);
                    output.insertAfter(Items.BEETROOT_SOUP, ModItems.GOLDEN_BEETROOT_SOUP);
                    output.insertAfter(Items.POTATO, ModItems.GOLDEN_POTATO);
                    output.insertAfter(Items.BAKED_POTATO, ModItems.GOLDEN_BAKED_POTATO);
                });
    }
}
