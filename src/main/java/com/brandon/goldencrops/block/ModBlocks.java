
package com.brandon.goldencrops.block;

import com.brandon.goldencrops.GoldenCrops;
import com.brandon.goldencrops.block.custom.GoldenWheatCropBlock;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Function;

public class ModBlocks {

    private static Block registerBlock(
            String name,
            Function<BlockBehaviour.Properties, Block> factory,
            BlockBehaviour.Properties properties
    ) {
        ResourceKey<Block> key = ResourceKey.create(
                Registries.BLOCK,
                GoldenCrops.id(name)
        );

        Block block = factory.apply(properties.setId(key));

        return Registry.register(
                BuiltInRegistries.BLOCK,
                key,
                block
        );
    }

    public static final Block GOLDEN_WHEAT_CROP = registerBlock(
            "golden_wheat_crop",
            GoldenWheatCropBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .noCollision()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.CROP)
                    .pushReaction(PushReaction.DESTROY)
    );

    public static void registerModBlocks() {
        GoldenCrops.LOGGER.info("Registering Golden Crops blocks");
    }
}
