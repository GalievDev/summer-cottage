package dev.galiev.sc.blocks.custom

import dev.galiev.sc.helper.EntityTypeRegistry
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Material
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
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
        if (world?.isClient!!) {
            return ActionResult.PASS
        }
        if (!player?.isSneaking!!) {
            return spawnChair(world, player, pos)
        }
        return ActionResult.PASS
    }

    fun spawnChair(world: World?, player: PlayerEntity?, pos: BlockPos?): ActionResult {
        val chair = EntityTypeRegistry.CHAIR_ENTITY.create(world)
        if (chair != null) {
            val newPos = Vec3d(pos?.x!! + 0.5, pos.y + 0.5, pos.z + 0.5)
            chair.updatePosition(newPos.x, newPos.y, newPos.z)
            world?.spawnEntity(chair)
            player?.startRiding(chair)

            return ActionResult.SUCCESS
        }

        return ActionResult.PASS
    }
}