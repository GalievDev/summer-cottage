package dev.galiev.sc.events

import net.fabricmc.fabric.api.event.player.UseItemCallback
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

object FishingEvent: UseItemCallback {
    override fun interact(player: PlayerEntity?, world: World?, hand: Hand?): TypedActionResult<ItemStack> {
        val stack = player?.mainHandStack
/*        if (stack == ItemStack.EMPTY) return TypedActionResult.fail(stack)

        if (stack?.item is FishingRodItem && player.fishHook != null) {
            val helmet = player.getEquippedStack(EquipmentSlot.HEAD)?.item
            val chest = player.getEquippedStack(EquipmentSlot.CHEST)?.item
            val legs = player.getEquippedStack(EquipmentSlot.LEGS)?.item

            if (helmet is FishermanHat && chest is FishermanShirt && legs is FishermanLeggings) {
                player.addStatusEffect(StatusEffectInstance(StatusEffects.LUCK))
            }
            return TypedActionResult.pass(stack)
        }*/
        return TypedActionResult.pass(stack)
    }
}