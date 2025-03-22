package dev.galiev.sc.events

import dev.galiev.sc.SummerCottage.RANDOM
import dev.galiev.sc.items.ItemsRegistry
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

        if (block is CropBlock && block.getAge(state) == CropBlock.MAX_AGE) {
            val helmet = player?.getEquippedStack(EquipmentSlot.HEAD)?.item
            val chest = player?.getEquippedStack(EquipmentSlot.CHEST)?.item
            val legs = player?.getEquippedStack(EquipmentSlot.LEGS)?.item
            val boots = player?.getEquippedStack(EquipmentSlot.FEET)?.item

            if (helmet == ItemsRegistry.GARDENER_HAT &&
                chest == ItemsRegistry.GARDENER_SHIRT &&
                legs == ItemsRegistry.GARDENER_LEGGINGS &&
                boots == ItemsRegistry.GARDENER_BOOTS) {
                if (RANDOM.nextInt(1, 100) <= 49) {
                    val drops =
                        Block.getDroppedStacks(state, world as ServerWorld, pos, null, player, player.mainHandStack)
                    for (drop in drops) {
                        val count = drop.count
                        drop.count = count * 2

                        world.spawnEntity(
                            ItemEntity(
                                world,
                                pos?.x?.toDouble()!!,
                                pos.y.toDouble(),
                                pos.z.toDouble(),
                                drop
                            )
                        )
                    }
                }
            }
        }
    }
}