package dev.galiev.sc.blocks.custom.entity

import dev.galiev.sc.enity.EntitiesRegistry
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.listener.ClientPlayPacketListener
import net.minecraft.network.packet.Packet
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World


class ChairEntity(type: EntityType<out Entity>?, world: World?) : Entity(EntitiesRegistry.CHAIR_ENTITY, world) {
    constructor(world: World?) : this(EntitiesRegistry.CHAIR_ENTITY, world) {
        noClip = true
    }

    override fun createSpawnPacket(): Packet<ClientPlayPacketListener> {
        return EntitySpawnS2CPacket(this)
    }

    override fun updatePassengerForDismount(passenger: LivingEntity?): Vec3d {
        if (passenger is PlayerEntity) {
            val x = this.blockPos.x
            val y = this.blockPos.y
            val z = this.blockPos.z
            val pos = OCCUPIED.remove(Vec3d(x.toDouble(), y.toDouble(), z.toDouble()))
            if (pos != null) {
                remove(RemovalReason.DISCARDED)
                return Vec3d(x + 0.5, y + 0.5, z + 0.5)
            }
        }
        remove(RemovalReason.DISCARDED)
        return super.updatePassengerForDismount(passenger)
    }

    override fun remove(reason: RemovalReason?) {
        OCCUPIED.remove(pos)
        super.remove(reason)
    }

    override fun initDataTracker() {
    }

    override fun readCustomDataFromNbt(nbt: NbtCompound?) {
    }

    override fun writeCustomDataToNbt(nbt: NbtCompound?) {
    }

    companion object {
        val OCCUPIED = HashMap<Vec3d, BlockPos>()
    }
}