package dev.galiev.sc.items.custom

import dev.galiev.sc.mixin.CropBlockMixin
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.HoeItem
import net.minecraft.item.ItemUsageContext
import net.minecraft.item.ToolMaterial
import net.minecraft.registry.tag.BlockTags
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class Rake(material: ToolMaterial?, attackDamage: Int, attackSpeed: Float, settings: Settings?) : HoeItem(material, attackDamage, attackSpeed, settings) {
    override fun useOnBlock(context: ItemUsageContext?): ActionResult {
        val world = context?.world
        val pos = context?.blockPos
        val blockState = world?.getBlockState(pos)
        val player = context?.player

        if (blockState?.isIn(BlockTags.DIRT) == true) {
            world.setBlockState(pos, Blocks.FARMLAND.defaultState)
            setBlocks(world, pos)

            world.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F)

            if (!world.isClient && player != null){
                context.stack.damage(1, player) { p: PlayerEntity -> p.sendToolBreakStatus(context.hand) }
            }
            ActionResult.success(world.isClient)

        } else if (blockState?.isIn(BlockTags.CROPS) == true) {
            return if ((blockState.block as CropBlockMixin).getAgeInvoke(blockState) == 7) {
                world.breakBlock(pos, true)
                breakBlocks(pos, world)
                if (!world.isClient && player != null){
                    context.stack.damage(1, player) { p: PlayerEntity -> p.sendToolBreakStatus(context.hand) }
                }
                ActionResult.SUCCESS
            } else {
                ActionResult.PASS
            }
        }

        return ActionResult.PASS
    }

    fun breakBlocks(pos: BlockPos?, world: World): ActionResult {
        var pos2: BlockPos.Mutable
        var blockState2: BlockState
        if (pos != null) {
            pos2 = BlockPos.Mutable(pos.x+1, pos.y, pos.z)
            blockState2 = world.getBlockState(pos2)
            if (blockState2.isIn(BlockTags.CROPS) && (blockState2.block as CropBlockMixin).getAgeInvoke(blockState2) == 7){
                world.breakBlock(pos2, true)
            } else {
                return ActionResult.PASS
            }

            pos2 = BlockPos.Mutable(pos.x-1, pos.y, pos.z)
            blockState2 = world.getBlockState(pos2)
            if (blockState2.isIn(BlockTags.CROPS) && (blockState2.block as CropBlockMixin).getAgeInvoke(blockState2) == 7){
                world.breakBlock(pos2, true)
            } else {
                return ActionResult.PASS
            }

            pos2 = BlockPos.Mutable(pos.x, pos.y, pos.z+1)
            blockState2 = world.getBlockState(pos2)
            if (blockState2.isIn(BlockTags.CROPS) && (blockState2.block as CropBlockMixin).getAgeInvoke(blockState2) == 7){
                world.breakBlock(pos2, true)
            } else {
                return ActionResult.PASS
            }

            pos2 = BlockPos.Mutable(pos.x, pos.y, pos.z-1)
            blockState2 = world.getBlockState(pos2)
            if (blockState2.isIn(BlockTags.CROPS) && (blockState2.block as CropBlockMixin).getAgeInvoke(blockState2) == 7){
                world.breakBlock(pos2, true)
            } else {
                return ActionResult.PASS
            }

            pos2 = BlockPos.Mutable(pos.x+1, pos.y, pos.z-1)
            blockState2 = world.getBlockState(pos2)
            if (blockState2.isIn(BlockTags.CROPS) && (blockState2.block as CropBlockMixin).getAgeInvoke(blockState2) == 7){
                world.breakBlock(pos2, true)
            } else {
                return ActionResult.PASS
            }

            pos2 = BlockPos.Mutable(pos.x+1, pos.y, pos.z+1)
            blockState2 = world.getBlockState(pos2)
            if (blockState2.isIn(BlockTags.CROPS) && (blockState2.block as CropBlockMixin).getAgeInvoke(blockState2) == 7){
                world.breakBlock(pos2, true)
            } else {
                return ActionResult.PASS
            }

            pos2 = BlockPos.Mutable(pos.x-1, pos.y, pos.z+1)
            blockState2 = world.getBlockState(pos2)
            if (blockState2.isIn(BlockTags.CROPS) && (blockState2.block as CropBlockMixin).getAgeInvoke(blockState2) == 7){
                world.breakBlock(pos2, true)
            } else {
                return ActionResult.PASS
            }

            pos2 = BlockPos.Mutable(pos.x-1, pos.y, pos.z-1)
            blockState2 = world.getBlockState(pos2)
            if (blockState2.isIn(BlockTags.CROPS) && (blockState2.block as CropBlockMixin).getAgeInvoke(blockState2) == 7){
                world.breakBlock(pos2, true)
            } else {
                return ActionResult.PASS
            }
        }
        return ActionResult.PASS
    }

    fun setBlocks(world: World, pos: BlockPos?): ActionResult {
        var pos2: BlockPos.Mutable
        var blockState2: BlockState
        if (pos != null) {
            pos2 = BlockPos.Mutable(pos.x+1, pos.y, pos.z)
            blockState2 = world.getBlockState(pos2)
            if (blockState2.isIn(BlockTags.DIRT)){
                world.setBlockState(pos2, Blocks.FARMLAND.defaultState)
            } else {
                return ActionResult.PASS
            }

            pos2 = BlockPos.Mutable(pos.x-1, pos.y, pos.z)
            blockState2 = world.getBlockState(pos2)
            if (blockState2.isIn(BlockTags.DIRT)){
                world.setBlockState(pos2, Blocks.FARMLAND.defaultState)
            } else {
                return ActionResult.PASS
            }

            pos2 = BlockPos.Mutable(pos.x, pos.y, pos.z+1)
            blockState2 = world.getBlockState(pos2)
            if (blockState2.isIn(BlockTags.DIRT)){
                world.setBlockState(pos2, Blocks.FARMLAND.defaultState)
            } else {
                return ActionResult.PASS
            }

            pos2 = BlockPos.Mutable(pos.x, pos.y, pos.z-1)
            blockState2 = world.getBlockState(pos2)
            if (blockState2.isIn(BlockTags.DIRT)){
                world.setBlockState(pos2, Blocks.FARMLAND.defaultState)
            } else {
                return ActionResult.PASS
            }

            pos2 = BlockPos.Mutable(pos.x+1, pos.y, pos.z-1)
            blockState2 = world.getBlockState(pos2)
            if (blockState2.isIn(BlockTags.DIRT)){
                world.setBlockState(pos2, Blocks.FARMLAND.defaultState)
            } else {
                return ActionResult.PASS
            }

            pos2 = BlockPos.Mutable(pos.x+1, pos.y, pos.z+1)
            blockState2 = world.getBlockState(pos2)
            if (blockState2.isIn(BlockTags.DIRT)){
                world.setBlockState(pos2, Blocks.FARMLAND.defaultState)
            } else {
                return ActionResult.PASS
            }

            pos2 = BlockPos.Mutable(pos.x-1, pos.y, pos.z+1)
            blockState2 = world.getBlockState(pos2)
            if (blockState2.isIn(BlockTags.DIRT)){
                world.setBlockState(pos2, Blocks.FARMLAND.defaultState)
            } else {
                return ActionResult.PASS
            }

            pos2 = BlockPos.Mutable(pos.x-1, pos.y, pos.z-1)
            blockState2 = world.getBlockState(pos2)
            if (blockState2.isIn(BlockTags.DIRT)){
                world.setBlockState(pos2, Blocks.FARMLAND.defaultState)
            } else {
                return ActionResult.PASS
            }
        }
        return ActionResult.PASS
    }
}