
package com.brandon.goldencrops.item;

import com.brandon.goldencrops.block.ModBlocks;
import com.brandon.goldencrops.GoldenCrops;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;
import java.util.function.Function;

public class ModItems {

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

    ;public static final Item GOLDEN_BREAD = registerItem(
            "golden_bread",
            properties -> new Item(
                    properties.food(
                            ModFoods.GOLDEN_BREAD,
                            ModFoods.GOLDEN_BREAD_CONSUMABLE
                    )
            )
    );

    private static Item registerItem(
            String name,
            Function<Item.Properties, Item> itemFactory
    ) {
        ResourceKey<Item> key = ResourceKey.create(
                Registries.ITEM,
                GoldenCrops.id(name)
        );

        Item.Properties properties = new Item.Properties().setId(key);

        Item item = itemFactory.apply(properties);

        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }

    public static void registerModItems() {
        GoldenCrops.LOGGER.info("Registering Golden Crops items");
    }
}
