package dev.galiev.sc.items.custom

import dev.galiev.sc.SummerCottage.RANDOM
import dev.galiev.sc.events.SeedHarvestEvent
import dev.galiev.sc.mixin.CropBlockMixin
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.Blocks
import net.minecraft.block.CropBlock
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.HoeItem
import net.minecraft.item.ItemUsageContext
import net.minecraft.item.ToolMaterials
import net.minecraft.registry.tag.BlockTags
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult
import net.minecraft.util.math.BlockPos


class Rake : HoeItem(ToolMaterials.IRON, 1, 1F, FabricItemSettings()) {
    override fun useOnBlock(context: ItemUsageContext?): ActionResult {
        val world = context?.world
        val pos = context?.blockPos
        val blockState = world?.getBlockState(pos)
        val player = context?.player

        if (blockState?.isIn(BlockTags.DIRT) == true) {
            if (pos != null) {
                for (targetPos in BlockPos.iterate(pos.add(-1, -1, -1), pos.add(1, 1, 1))) {
                    if (world.getBlockState(targetPos.up()).isIn(BlockTags.REPLACEABLE_PLANTS)) {
                        world.breakBlock(targetPos.up(), true)
                    }
                    if (world.getBlockState(targetPos).isIn(BlockTags.DIRT) && world.isAir(targetPos.up())) {
                        world.setBlockState(targetPos, Blocks.FARMLAND.defaultState)
                    }
                }

                world.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F)

                if (!world.isClient && player != null) {
                    context.stack.damage(1, player) { p: PlayerEntity -> p.sendToolBreakStatus(context.hand) }
                }

                ActionResult.success(world.isClient)
            }
        }

        else if (blockState?.isIn(BlockTags.CROPS) == true) {
            if (pos != null) {
                for (targetPos in BlockPos.iterate(pos.add(-1, 0, -1), pos.add(1, 0, 1))) {
                    if ((blockState.block as CropBlockMixin).getAgeInvoke(blockState) == CropBlock.MAX_AGE) {
                        world.breakBlock(targetPos, true)
                    }
                }

                if (!world.isClient && player != null) {
                    context.stack.damage(RANDOM.nextInt(2, 9), player) { p: PlayerEntity ->
                        p.sendToolBreakStatus(context.hand)
                    }
                    SeedHarvestEvent.afterBlockBreak(world, player, pos, blockState, null)
                }

                return ActionResult.success(world.isClient)
            }
        }

        return ActionResult.PASS
    }
}