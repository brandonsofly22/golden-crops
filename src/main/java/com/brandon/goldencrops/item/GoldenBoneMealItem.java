
package com.brandon.goldencrops.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

public class GoldenBoneMealItem extends Item {

    public GoldenBoneMealItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);

        // Supports vanilla and Golden Crops based on CropBlock.
        if (!(state.getBlock() instanceof CropBlock crop)) {
            return InteractionResult.PASS;
        }

        // Don't consume Golden Bone Meal on mature crops.
        if (crop.isMaxAge(state)) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide()) {

            // Instantly grow the crop to its maximum age.
            level.setBlockAndUpdate(
                    pos,
                    crop.getStateForAge(crop.getMaxAge())
            );

            // Play bone meal growth particles.
            level.levelEvent(1505, pos, 0);

            // Consume one item unless the player is in Creative.
            Player player = context.getPlayer();

            if (player == null || !player.getAbilities().instabuild) {
                context.getItemInHand().shrink(1);
            }
        }

        return InteractionResult.SUCCESS;
    }
}
