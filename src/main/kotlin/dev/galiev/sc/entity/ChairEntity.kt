package dev.galiev.sc.entity

import dev.galiev.sc.SummerCottage
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.nbt.NbtCompound
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import net.minecraft.world.World

class ChairEntity(type: EntityType<out ChairEntity>?, world: World?) : Entity(type, world) {
    constructor(world: World, x: Double, y: Double, z: Double): this(CHAIR_ENTITY_TYPE, world){
        setPosition(x, y, z)
    }

    override fun collidesWith(other: Entity?): Boolean {
        return true
    }

    override fun isPushedByFluids(): Boolean {
        return false
    }

    override fun tick() {
        super.tick()
        if (firstPassenger == null || firstPassenger!!.isAlive){
            removeFromDimension()
        }
    }

    override fun initDataTracker() {
        TODO("Not yet implemented")
    }

    override fun readCustomDataFromNbt(nbt: NbtCompound?) {
        TODO("Not yet implemented")
    }

    override fun writeCustomDataToNbt(nbt: NbtCompound?) {
        TODO("Not yet implemented")
    }

    companion object {
        val CHAIR_ENTITY_TYPE: EntityType<out ChairEntity> = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier(SummerCottage.MOD_ID, "chair_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, ::ChairEntity).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build()
        )
    }
}