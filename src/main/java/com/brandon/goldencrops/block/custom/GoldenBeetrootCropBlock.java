
package com.brandon.goldencrops.block.custom;

import com.brandon.goldencrops.item.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.BeetrootBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class GoldenBeetrootCropBlock extends BeetrootBlock {

    public GoldenBeetrootCropBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected Item getBaseSeedId() {
        return ModItems.GOLDEN_BEETROOT_SEEDS;
    }
}
