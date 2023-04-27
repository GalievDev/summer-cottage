package dev.galiev.sc.items.custom

import dev.galiev.sc.events.SeedHarvestEvent
import dev.galiev.sc.mixin.CropBlockMixin
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
import kotlin.random.Random

class Rake(material: ToolMaterial?, attackDamage: Int, attackSpeed: Float, settings: Settings?) : HoeItem(material, attackDamage, attackSpeed, settings) {
    override fun useOnBlock(context: ItemUsageContext?): ActionResult {
        val world = context?.world
        val pos = context?.blockPos
        val blockState = world?.getBlockState(pos)
        val player = context?.player

        if (blockState?.isIn(BlockTags.DIRT) == true) {
            world.setBlockState(pos, Blocks.FARMLAND.defaultState)
            if (pos != null) {
                setBlocks(pos.x, pos.y, pos.z, world)
                setBlocks(pos.x + 1, pos.y, pos.z, world)
                setBlocks(pos.x - 1, pos.y, pos.z, world)
                setBlocks(pos.x, pos.y, pos.z + 1, world)
                setBlocks(pos.x, pos.y, pos.z - 1, world)
                setBlocks(pos.x + 1, pos.y, pos.z - 1, world)
                setBlocks(pos.x + 1, pos.y, pos.z + 1, world)
                setBlocks(pos.x - 1, pos.y, pos.z + 1, world)
                setBlocks(pos.x - 1, pos.y, pos.z - 1, world)
            }

            world.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F)

            if (!world.isClient && player != null){
                context.stack.damage(1, player) { p: PlayerEntity -> p.sendToolBreakStatus(context.hand) }
            }

            ActionResult.success(world.isClient)

        } else if (blockState?.isIn(BlockTags.CROPS) == true) {
            if (pos != null) {
                breakBlocks(pos.x, pos.y, pos.z, world)
                breakBlocks(pos.x + 1, pos.y, pos.z, world)
                breakBlocks(pos.x - 1, pos.y, pos.z, world)
                breakBlocks(pos.x, pos.y, pos.z + 1, world)
                breakBlocks(pos.x, pos.y, pos.z - 1, world)
                breakBlocks(pos.x + 1, pos.y, pos.z - 1, world)
                breakBlocks(pos.x + 1, pos.y, pos.z + 1, world)
                breakBlocks(pos.x - 1, pos.y, pos.z + 1, world)
                breakBlocks(pos.x - 1, pos.y, pos.z - 1, world)
            }

            if (!world.isClient && player != null){
                context.stack.damage(Random.nextInt(2, 9), player) { p: PlayerEntity -> p.sendToolBreakStatus(context.hand) }
                SeedHarvestEvent.afterBlockBreak(world, player, pos, blockState, null)
            }

            return ActionResult.success(world.isClient)
        }

        return ActionResult.PASS
    }

    fun breakBlocks(x: Int, y: Int, z: Int, world: World) {
        val pos: BlockPos.Mutable = BlockPos.Mutable(x,y,z)
        val blockState = world.getBlockState(pos)
        if (blockState.isIn(BlockTags.CROPS) && (blockState.block as CropBlockMixin).getAgeInvoke(blockState) == 7){
            world.breakBlock(pos, true)
        }
    }

    fun setBlocks(x: Int, y: Int, z: Int, world: World) {
        val pos: BlockPos.Mutable = BlockPos.Mutable(x,y,z)
        val blockState = world.getBlockState(pos)
        if(blockState.isIn(BlockTags.DIRT)){
            world.setBlockState(pos, Blocks.FARMLAND.defaultState)
        }
    }
}