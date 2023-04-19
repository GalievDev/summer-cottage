package dev.galiev.sc.items.custom

import net.minecraft.block.BlockState
import net.minecraft.block.Fertilizable
import net.minecraft.item.Item
import net.minecraft.item.ItemUsageContext
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.ActionResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import net.minecraft.world.WorldEvents

class WaterCan(settings: Settings?) : Item(settings) {
    override fun useOnBlock(context: ItemUsageContext?): ActionResult {
        val world: World? = context?.world
        val blockPos: BlockPos? = context?.blockPos
        val blockPos2: BlockPos? = blockPos?.offset(context.side)

        if (useOn(world, blockPos, context?.side)) {
            if (!world?.isClient!!){
                world.syncWorldEvent(WorldEvents.BONE_MEAL_USED, blockPos2, 0)
            }
            return ActionResult.success(world.isClient)
        }

        return ActionResult.PASS
    }

    fun useOn(world: World?, blockPos: BlockPos?, facing: Direction?): Boolean{
        blockPos?.multiply(3)
        blockPos?.y?.minus(3)

        val blockState: BlockState? = world?.getBlockState(blockPos)
        if (blockState?.block is Fertilizable && (blockState.block as Fertilizable).isFertilizable(world, blockPos, blockState, world.isClient)){
            if (world is ServerWorld){
                if ((blockState.block as Fertilizable).canGrow(world, world.random, blockPos, blockState)){
                    (blockState.block as Fertilizable).grow(world, world.random, blockPos, blockState)
                }
            }
            return true
        }

        return false
    }
}