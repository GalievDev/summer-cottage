package dev.galiev.sc.enity.custom

import dev.galiev.sc.enity.EntitiesRegistry
import dev.galiev.sc.items.ItemsRegistry
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.projectile.PersistentProjectileEntity
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.world.World

class DartsEntity: PersistentProjectileEntity {

    companion object {
        var DEFAULT_STACK = ItemStack(ItemsRegistry.DARTS)
    }

    constructor(entityType: EntityType<out DartsEntity?>, world: World): super(entityType, world)

    constructor(world: World, owner: LivingEntity, stack: ItemStack, shorFrom: ItemStack?): super(EntitiesRegistry.DARTS_ENTITY, owner, world, stack, shorFrom) {
        DEFAULT_STACK = stack.copy()
    }

    override fun shouldRender(cameraX: Double, cameraY: Double, cameraZ: Double): Boolean = true

    override fun asItemStack(): ItemStack {
        return DEFAULT_STACK.copy()
    }

    override fun getDefaultItemStack(): ItemStack = DEFAULT_STACK

    override fun getHitSound(): SoundEvent = SoundEvents.BLOCK_WOOD_HIT
}