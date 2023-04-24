package dev.galiev.sc.events

import dev.galiev.sc.items.IRegistry
import net.fabricmc.fabric.api.event.player.AttackBlockCallback
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import kotlin.random.Random

object CropBreak: AttackBlockCallback {
    override fun interact(
        player: PlayerEntity?,
        world: World?,
        hand: Hand?,
        pos: BlockPos?,
        direction: Direction?
    ): ActionResult {
        if (!world?.isClient!! && player?.isSpectator!!){
            if (player.getEquippedStack(EquipmentSlot.HEAD) == IRegistry.GARDENER_HAT?.defaultStack &&
                player.getEquippedStack(EquipmentSlot.CHEST) == IRegistry.GARDENER_SHIRT?.defaultStack &&
                player.getEquippedStack(EquipmentSlot.LEGS) == IRegistry.GARDENER_LEGGINGS?.defaultStack){
                val blockState = world.getBlockState(pos)
                val items = Items.EGG
                val stack = ItemStack(items, Random.nextInt(1, 7))
                player.giveItemStack(stack)

                return ActionResult.SUCCESS
            }
        }
        return ActionResult.PASS
    }
}