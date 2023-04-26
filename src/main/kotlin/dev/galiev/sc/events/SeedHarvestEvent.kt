package dev.galiev.sc.events

import dev.galiev.sc.items.IRegistry
import net.fabricmc.fabric.api.event.player.UseBlockCallback
import net.minecraft.block.Block
import net.minecraft.block.CropBlock
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.ItemEntity
import net.minecraft.item.BlockItem
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand

object SeedHarvestEvent {
    fun register(){
        UseBlockCallback.EVENT.register{ player, world, hand, hitResult ->
            if (player != null && hand == Hand.MAIN_HAND){
                val state = world.getBlockState(hitResult.blockPos)
                val block =  state.block

                if (block is CropBlock){
                    val helmet = player.getEquippedStack(EquipmentSlot.HEAD)
                    val chest = player.getEquippedStack(EquipmentSlot.CHEST)
                    val legs = player.getEquippedStack(EquipmentSlot.LEGS)

                    if (helmet == IRegistry.GARDENER_HAT?.defaultStack && chest == IRegistry.GARDENER_SHIRT?.defaultStack && legs == IRegistry.GARDENER_LEGGINGS?.defaultStack){
                        val drops = Block.getDroppedStacks(state, world as ServerWorld, hitResult.blockPos, null, player, player.mainHandStack)

                        for (drop in drops){
                            if (drop.item is BlockItem) {
                                val count = drop.count
                                drop.count = count * 2
                            }

                            world.spawnEntity(ItemEntity(world, hitResult.blockPos.x.toDouble(), hitResult.blockPos.y.toDouble(), hitResult.blockPos.z.toDouble(), drop))
                        }

                        world.breakBlock(hitResult.blockPos, false, player)
                        return@register ActionResult.SUCCESS
                    }
                }
            }

            ActionResult.PASS
        }
    }
}