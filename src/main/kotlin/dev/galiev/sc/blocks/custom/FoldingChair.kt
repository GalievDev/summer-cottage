package dev.galiev.sc.blocks.custom

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Material
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class FoldingChair(settings: Settings = FabricBlockSettings.of(Material.WOOD).nonOpaque()) : Block(settings) {
    @Deprecated("Deprecated in Java", ReplaceWith("ActionResult.CONSUME", "net.minecraft.util.ActionResult"))
    override fun onUse(
        state: BlockState?,
        world: World?,
        pos: BlockPos?,
        player: PlayerEntity?,
        hand: Hand?,
        hit: BlockHitResult?
    ): ActionResult {
        return ActionResult.SUCCESS
    }
}