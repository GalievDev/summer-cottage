package dev.galiev.sc.blocks.custom

import dev.galiev.sc.blocks.custom.entity.ChairEntity
import dev.galiev.sc.blocks.custom.entity.ChairEntity.Companion.OCCUPIED
import dev.galiev.sc.helper.EntityTypeRegistry
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.BlockState
import net.minecraft.block.HorizontalFacingBlock
import net.minecraft.block.Material
import net.minecraft.block.Waterloggable
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import net.minecraft.world.event.GameEvent


class FoldingChair(settings: Settings = FabricBlockSettings.of(Material.WOOD).nonOpaque()) : HorizontalFacingBlock(settings), Waterloggable {
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
        if (!player?.isSneaking!! && player.getStackInHand(hand).isEmpty) {
            val comparePos = Vec3d(hit?.blockPos?.x?.toDouble()!!, hit.blockPos.y.toDouble(), hit.blockPos.z.toDouble())

            return spawnChair(world, player, hit.blockPos, 0.3, comparePos)
        }
        return ActionResult.PASS
    }

    override fun onBreak(world: World?, pos: BlockPos?, state: BlockState?, player: PlayerEntity?) {
        if (pos != null) {
            val x = pos.x.toDouble()
            val y = pos.y.toDouble()
            val z = pos.z.toDouble()

            val entities: MutableList<ChairEntity>? = world?.getEntitiesByClass(
                ChairEntity::class.java,
                Box(x, y, z, x + 1.0, y + 1.0, z + 1.0)
            ) { entity -> entity.hasPassengers() }

            if (entities != null) {
                for (entity in entities) {
                    entity.remove(Entity.RemovalReason.DISCARDED)
                }
            }
            this.spawnBreakParticles(world, player, pos, state)
            world?.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos)

            ChairEntity.OCCUPIED.remove(Vec3d(x,y,z))
        }
    }

    private fun spawnChair(world: World?, player: PlayerEntity?, pos: BlockPos?, yOffset: Double, comparePos: Vec3d): ActionResult {
        val chair = EntityTypeRegistry.CHAIR_ENTITY.create(world)
        if (chair != null) {
            val newPos = Vec3d(pos?.x!! + 0.5, pos.y + yOffset, pos.z + 0.5)
            OCCUPIED.put(comparePos, player?.blockPos!!)
            chair.updatePosition(newPos.x, newPos.y, newPos.z)
            world?.spawnEntity(chair)
            player.startRiding(chair)

            return ActionResult.SUCCESS
        }

        return ActionResult.PASS
    }
}