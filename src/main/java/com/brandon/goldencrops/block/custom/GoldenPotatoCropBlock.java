
package com.brandon.goldencrops.block.custom;

import com.brandon.goldencrops.item.ModItems;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class GoldenPotatoCropBlock extends CropBlock {

    public GoldenPotatoCropBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected Item getBaseSeedId() {
        return ModItems.GOLDEN_POTATO;
    }
}
