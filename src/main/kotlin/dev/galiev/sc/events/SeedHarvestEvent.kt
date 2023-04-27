package dev.galiev.sc.events

import dev.galiev.sc.items.IRegistry
import dev.galiev.sc.mixin.CropBlockMixin
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.CropBlock
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.ItemEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

object SeedHarvestEvent: PlayerBlockBreakEvents.After {
    override fun afterBlockBreak(
        world: World?,
        player: PlayerEntity?,
        pos: BlockPos?,
        state: BlockState?,
        blockEntity: BlockEntity?
    ) {

        val block = state?.block

        if (block is CropBlock && (block as CropBlockMixin).getAgeInvoke(state) == 7) {
            val helmet = player?.getEquippedStack(EquipmentSlot.HEAD)?.item
            val chest = player?.getEquippedStack(EquipmentSlot.CHEST)?.item
            val legs = player?.getEquippedStack(EquipmentSlot.LEGS)?.item

            if (helmet == IRegistry.GARDENER_HAT && chest == IRegistry.GARDENER_SHIRT && legs == IRegistry.GARDENER_LEGGINGS) {
                val drops = Block.getDroppedStacks(state, world as ServerWorld, pos, null, player, player?.mainHandStack)

                for (drop in drops) {
                    val count = drop.count
                    drop.count = count * 2

                    world.spawnEntity(ItemEntity(world, pos?.x?.toDouble()!!, pos.y.toDouble(), pos.z.toDouble(), drop))
                }

                world.breakBlock(pos, false, player)
            }
        }
    }
}