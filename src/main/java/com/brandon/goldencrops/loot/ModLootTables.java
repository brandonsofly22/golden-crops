
package com.brandon.goldencrops.loot;

import com.brandon.goldencrops.item.ModItems;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.advancements.predicates.StatePropertiesPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.BeetrootBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;

public class ModLootTables {

    public static void registerModLootTables() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {

            var fortune = registries
                    .lookupOrThrow(Registries.ENCHANTMENT)
                    .getOrThrow(Enchantments.FORTUNE);

            // Rare Golden Wheat Seeds from mature vanilla wheat
            if (source.isBuiltin()
                    && Blocks.WHEAT.getLootTable().isPresent()
                    && Blocks.WHEAT.getLootTable().get().equals(key)) {

                LootPool.Builder wheatPool = LootPool.lootPool()
                        .add(LootItem.lootTableItem(ModItems.GOLDEN_WHEAT_SEEDS))
                        .when(
                                LootItemBlockStatePropertyCondition
                                        .hasBlockStateProperties(Blocks.WHEAT)
                                        .setProperties(
                                                StatePropertiesPredicate.Builder.properties()
                                                        .hasProperty(CropBlock.AGE, 7)
                                        )
                        )
                        .when(
                                BonusLevelTableCondition.bonusLevelFlatChance(
                                        fortune,
                                        1.0F / 450.0F,
                                        1.0F / 375.0F,
                                        1.0F / 300.0F,
                                        1.0F / 250.0F
                                )
                        );

                tableBuilder.withPool(wheatPool);
            }

            // Rare Golden Beetroot Seeds from mature vanilla beetroot
            if (source.isBuiltin()
                    && Blocks.BEETROOTS.getLootTable().isPresent()
                    && Blocks.BEETROOTS.getLootTable().get().equals(key)) {

                LootPool.Builder beetrootPool = LootPool.lootPool()
                        .add(LootItem.lootTableItem(ModItems.GOLDEN_BEETROOT_SEEDS))
                        .when(
                                LootItemBlockStatePropertyCondition
                                        .hasBlockStateProperties(Blocks.BEETROOTS)
                                        .setProperties(
                                                StatePropertiesPredicate.Builder.properties()
                                                        .hasProperty(BeetrootBlock.AGE, 3)
                                        )
                        )
                        .when(
                                BonusLevelTableCondition.bonusLevelFlatChance(
                                        fortune,
                                        1.0F / 200.0F,
                                        1.0F / 175.0F,
                                        1.0F / 150.0F,
                                        1.0F / 125.0F
                                )
                        );

                tableBuilder.withPool(beetrootPool);
            }

            // Rare Golden Potato from mature vanilla potatoes
            if (source.isBuiltin()
                    && Blocks.POTATOES.getLootTable().isPresent()
                    && Blocks.POTATOES.getLootTable().get().equals(key)) {

                LootPool.Builder potatoPool = LootPool.lootPool()
                        .add(LootItem.lootTableItem(ModItems.GOLDEN_POTATO))
                        .when(
                                LootItemBlockStatePropertyCondition
                                        .hasBlockStateProperties(Blocks.POTATOES)
                                        .setProperties(
                                                StatePropertiesPredicate.Builder.properties()
                                                        .hasProperty(CropBlock.AGE, 7)
                                        )
                        )
                        .when(
                                BonusLevelTableCondition.bonusLevelFlatChance(
                                        fortune,
                                        1.0F / 300.0F,
                                        1.0F / 250.0F,
                                        1.0F / 200.0F,
                                        1.0F / 150.0F
                                )
                        );

                tableBuilder.withPool(potatoPool);
            }
        });
    }
}
