package dev.galiev.sc.items.custom

import dev.galiev.sc.SummerCottage.RANDOM
import dev.galiev.sc.events.SeedHarvestEvent
import net.minecraft.block.Blocks
import net.minecraft.block.CropBlock
import net.minecraft.entity.LivingEntity
import net.minecraft.item.HoeItem
import net.minecraft.item.ItemUsageContext
import net.minecraft.item.ToolMaterial
import net.minecraft.registry.tag.BlockTags
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult
import net.minecraft.util.math.BlockPos


class Rake : HoeItem(ToolMaterial.IRON, 2.0F, 3.0F, Settings()) {
    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        val world = context.world
        val pos = context.blockPos
        val blockState = world.getBlockState(pos)
        val player = context.player

        if ((blockState.isOf(Blocks.DIRT) || blockState.isOf(Blocks.GRASS_BLOCK)) &&
            (world.isAir(pos.up()) || world.getBlockState(pos.up()).isReplaceable)) {
            if (pos != null) {
                for (targetPos in BlockPos.iterate(pos.add(-1, 0, -1), pos.add(1, 0, 1))) {
                    val targetState = world.getBlockState(targetPos)

                    if (world.getBlockState(targetPos.up()).isReplaceable) {
                        world.breakBlock(targetPos.up(), true)
                    }

                    if ((targetState.isOf(Blocks.DIRT) || targetState.isOf(Blocks.GRASS_BLOCK)) && world.isAir(targetPos.up())) {
                        world.setBlockState(targetPos, Blocks.FARMLAND.defaultState)
                    }
                }

                world.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F)

                if (!world.isClient && player != null) {
                    context.stack.damage(RANDOM.nextInt(2, 9), player, LivingEntity.getSlotForHand(context.hand))
                }

                return ActionResult.SUCCESS
            }
        }

        else if (blockState.isIn(BlockTags.CROPS)) {
            if (pos != null) {
                for (targetPos in BlockPos.iterate(pos.add(-1, 0, -1), pos.add(1, 0, 1))) {
                    if (world.getBlockState(targetPos).block is CropBlock && (blockState.block as CropBlock).getAge(blockState) == CropBlock.MAX_AGE) {
                        world.breakBlock(targetPos, true)
                    }
                }

                if (!world.isClient && player != null) {
                    context.stack.damage(RANDOM.nextInt(2, 9), player, LivingEntity.getSlotForHand(context.hand))
                    SeedHarvestEvent.afterBlockBreak(world, player, pos, blockState, null)
                }

                return ActionResult.SUCCESS
            }
        }

        return ActionResult.PASS
    }
}