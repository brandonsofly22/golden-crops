
package com.brandon.goldencrops.loot;

import com.brandon.goldencrops.item.ModItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.advancements.predicates.StatePropertiesPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;

public class ModLootTables {

    public static void registerModLootTables() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {

            if (source.isBuiltin()
                    && Blocks.WHEAT.getLootTable().isPresent()
                    && Blocks.WHEAT.getLootTable().get().equals(key)) {

                var fortune = registries
                        .lookupOrThrow(Registries.ENCHANTMENT)
                        .getOrThrow(Enchantments.FORTUNE);

                LootPool.Builder pool = LootPool.lootPool()
                        .add(LootItem.lootTableItem(ModItems.GOLDEN_WHEAT_SEEDS))

                        // Only fully grown vanilla wheat.
                        .when(
                                LootItemBlockStatePropertyCondition
                                        .hasBlockStateProperties(Blocks.WHEAT)
                                        .setProperties(
                                                StatePropertiesPredicate.Builder.properties()
                                                        .hasProperty(CropBlock.AGE, 7)
                                        )
                        )

                        // Fortune levels 0, 1, 2, and 3.
                        .when(
                        BonusLevelTableCondition.bonusLevelFlatChance(
                                fortune,
                                1.0F / 450.0F,
                                1.0F / 375.0F,
                                1.0F / 300.0F,
                                1.0F / 250.0F
                        )
                );

                tableBuilder.withPool(pool);
            }
        });
    }
}
