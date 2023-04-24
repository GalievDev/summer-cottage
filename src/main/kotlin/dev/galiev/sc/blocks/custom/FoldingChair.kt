package dev.galiev.sc.blocks.custom

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.vehicle.BoatEntity
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class FoldingChair(settings: Settings?) : Block(settings) {
    @Deprecated("Deprecated in Java", ReplaceWith("ActionResult.CONSUME", "net.minecraft.util.ActionResult"))
    override fun onUse(
        state: BlockState?,
        world: World?,
        pos: BlockPos?,
        player: PlayerEntity?,
        hand: Hand?,
        hit: BlockHitResult?
    ): ActionResult {
        if (!world?.isClient!!){
            player?.startRiding(BoatEntity(world, pos?.x?.toDouble()!!, pos.y.toDouble(), pos.z.toDouble()))
        }
        return ActionResult.CONSUME
    }
}