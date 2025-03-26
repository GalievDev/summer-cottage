package dev.galiev.sc.items.custom

import dev.galiev.sc.SummerCottage.RANDOM
import dev.galiev.sc.enity.custom.DartsEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.PersistentProjectileEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.consume.UseAction
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.stat.Stats
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.world.World


class Darts(settings: Settings = Settings()) : Item(settings) {

    override fun onStoppedUsing(stack: ItemStack, world: World, user: LivingEntity, remainingUseTicks: Int): Boolean {
        if (user is PlayerEntity) {
            val i = getMaxUseTime(stack, user) - remainingUseTicks
            if (i >= 10) {
                if (!world.isClient) {
                    val darts = DartsEntity(world, user, stack, null)
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
                user.itemCooldownManager.set(stack, 20)
                user.incrementStat(Stats.USED.getOrCreateStat(this))
                return true
            }
        }
        return false
    }

    override fun use(world: World?, user: PlayerEntity, hand: Hand?): ActionResult {
        user.setCurrentHand(hand)
        return ActionResult.CONSUME
    }

    override fun usageTick(world: World?, user: LivingEntity?, stack: ItemStack?, remainingUseTicks: Int) {
        if (user is PlayerEntity) {
            if(user.itemUseTime >= 41) {
                if (RANDOM.nextInt(0, 100) <= 89) {
                    user.yaw += RANDOM.nextInt(-1, 1).toFloat()
                    user.pitch += RANDOM.nextInt(-1, 1).toFloat()
                }
            } else {
                if (RANDOM.nextInt(0, 100) <= 49) {
                    user.yaw += RANDOM.nextInt(-1, 1).toFloat()
                    user.pitch += RANDOM.nextInt(-1, 1).toFloat()
                }
            }
        }
        super.usageTick(world, user, stack, remainingUseTicks)
    }

    override fun getUseAction(stack: ItemStack?): UseAction {
        return UseAction.SPEAR
    }

    override fun getMaxUseTime(stack: ItemStack?, user: LivingEntity?): Int {
        return 92000
    }
}