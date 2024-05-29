package dev.galiev.sc.items.custom

import dev.galiev.sc.enity.custom.DartsEntity
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.PersistentProjectileEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.stat.Stats
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.UseAction
import net.minecraft.world.World


class Darts(settings: Settings = FabricItemSettings()) : Item(settings) {
    override fun onStoppedUsing(stack: ItemStack, world: World, user: LivingEntity, remainingUseTicks: Int) {
        if (user is PlayerEntity) {
            val i = getMaxUseTime(stack) - remainingUseTicks
            if (i >= 10) {
                if (!world.isClient) {
                    val darts = DartsEntity(world, user, stack)
                    darts.setVelocity(
                        user,
                        user.getPitch(),
                        user.getYaw(),
                        0.0f,
                        1.5f,
                        1.0f
                    )
                    if (user.abilities.creativeMode) {
                        darts.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY
                    }
                    world.spawnEntity(darts)
                    world.playSoundFromEntity(
                        null as PlayerEntity?,
                        darts,
                        SoundEvents.ENTITY_ARROW_SHOOT,
                        SoundCategory.PLAYERS,
                        1.0f,
                        1.0f
                    )
                    if (!user.abilities.creativeMode) user.inventory.removeOne(stack)
                }
                user.itemCooldownManager[this] = 20
                user.incrementStat(Stats.USED.getOrCreateStat(this))
            }
        }
    }

    override fun use(world: World?, user: PlayerEntity, hand: Hand?): TypedActionResult<ItemStack> {
        val stack = user.getStackInHand(hand)
        user.setCurrentHand(hand)
        return TypedActionResult.consume(stack)
    }

    override fun getUseAction(stack: ItemStack?): UseAction {
        return UseAction.SPEAR
    }

    override fun getMaxUseTime(stack: ItemStack?): Int {
        return 72000
    }
}