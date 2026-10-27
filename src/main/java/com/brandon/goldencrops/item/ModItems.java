
package com.brandon.goldencrops.item;

import com.brandon.goldencrops.GoldenCrops;
import com.brandon.goldencrops.block.ModBlocks;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.function.Function;

public class ModItems {

    // Golden Wheat
    public static final Item GOLDEN_WHEAT = registerItem(
            "golden_wheat",
            Item::new
    );

    public static final Item GOLDEN_WHEAT_SEEDS = registerItem(
            "golden_wheat_seeds",
            properties -> new BlockItem(
                    ModBlocks.GOLDEN_WHEAT_CROP,
                    properties
            )
    );

    // Golden Bread
    public static final Item GOLDEN_BREAD = registerItem(
            "golden_bread",
            properties -> new Item(
                    properties.food(
                            ModFoods.GOLDEN_BREAD,
                            ModFoods.GOLDEN_BREAD_CONSUMABLE
                    )
            )
    );

    // Golden Beetroot
    public static final Item GOLDEN_BEETROOT = registerItem(
            "golden_beetroot",
            properties -> new Item(
                    properties.food(
                            ModFoods.GOLDEN_BEETROOT,
                            ModFoods.GOLDEN_BEETROOT_CONSUMABLE
                    )
            )
    );

    // Golden Beetroot Seeds
    public static final Item GOLDEN_BEETROOT_SEEDS = registerItem(
            "golden_beetroot_seeds",
            properties -> new BlockItem(
                    ModBlocks.GOLDEN_BEETROOT_CROP,
                    properties
            )
    );

    // Golden Beetroot Soup
    public static final Item GOLDEN_BEETROOT_SOUP = registerItem(
            "golden_beetroot_soup",
            properties -> new Item(
                    properties
                            .stacksTo(1)
                            .usingConvertsTo(Items.BOWL)
                            .food(
                                    ModFoods.GOLDEN_BEETROOT_SOUP,
                                    ModFoods.GOLDEN_BEETROOT_SOUP_CONSUMABLE
                            )
            )
    );



    // Golden Potato
    public static final Item GOLDEN_POTATO = registerItem(
            "golden_potato",
            properties -> new BlockItem(
                    ModBlocks.GOLDEN_POTATO_CROP,
                    properties.food(
                            ModFoods.GOLDEN_POTATO,
                            ModFoods.GOLDEN_POTATO_CONSUMABLE
                    )
            )
    );



    // Golden Baked Potato
    public static final Item GOLDEN_BAKED_POTATO = registerItem(
            "golden_baked_potato",
            properties -> new Item(
                    properties.food(
                            ModFoods.GOLDEN_BAKED_POTATO,
                            ModFoods.GOLDEN_BAKED_POTATO_CONSUMABLE
                    )
            )
    );

    // Golden Bone Meal
    public static final Item GOLDEN_BONE_MEAL = registerItem(
            "golden_bone_meal",
            GoldenBoneMealItem::new
    );

    private static Item registerItem(
            String name,
            Function<Item.Properties, Item> itemFactory
    ) {
        ResourceKey<Item> key = ResourceKey.create(
                Registries.ITEM,
                GoldenCrops.id(name)
        );

        Item.Properties properties =
                new Item.Properties().setId(key);

        Item item = itemFactory.apply(properties);

        return Registry.register(
                BuiltInRegistries.ITEM,
                key,
                item
        );
    }

    public static void registerModItems() {
        GoldenCrops.LOGGER.info("Registering Golden Crops items");
    }
}
