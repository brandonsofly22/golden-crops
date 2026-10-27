
package com.brandon.goldencrops.block.custom;

import com.brandon.goldencrops.item.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class GoldenWheatCropBlock extends CropBlock {

    public GoldenWheatCropBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected Item getBaseSeedId() {
        return ModItems.GOLDEN_WHEAT_SEEDS;
    }
}
