package dev.galiev.sc.events

import dev.galiev.sc.SummerCottage.RANDOM
import dev.galiev.sc.items.ItemsRegistry
import net.fabricmc.fabric.api.event.player.UseItemCallback
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

object FishingEvent: UseItemCallback {
    override fun interact(player: PlayerEntity?, world: World?, hand: Hand?): TypedActionResult<ItemStack> {
        val stack = player?.mainHandStack
        if (stack == ItemStack.EMPTY) return TypedActionResult.fail(stack)

        if (stack?.item == Items.FISHING_ROD) {
            val helmet = player?.getEquippedStack(EquipmentSlot.HEAD)?.item
            val chest = player?.getEquippedStack(EquipmentSlot.CHEST)?.item
            val legs = player?.getEquippedStack(EquipmentSlot.LEGS)?.item

            if (helmet == ItemsRegistry.FISHERMAN_HAT && chest == ItemsRegistry.FISHERMAN_SHIRT && legs == ItemsRegistry.FISHERMAN_LEGGINGS) {
                if (RANDOM.nextInt(1, 100) <= 49) {

                }
            }

            return TypedActionResult.success(stack)
        }
        return TypedActionResult.pass(stack)
    }
}