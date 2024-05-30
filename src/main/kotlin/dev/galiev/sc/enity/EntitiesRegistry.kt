package dev.galiev.sc.enity

import dev.galiev.sc.SummerCottage
import dev.galiev.sc.blocks.custom.entity.ChairEntity
import dev.galiev.sc.enity.custom.DartsEntity
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object EntitiesRegistry {
    private val ENTITIES: MutableMap<EntityType<*>, Identifier> = LinkedHashMap()

    val DARTS_ENTITY: EntityType<DartsEntity> =
        FabricEntityTypeBuilder.create(SpawnGroup.MISC, ::DartsEntity)
            .dimensions(EntityDimensions.fixed(0.2F, 0.2F))
            .build()
            .create("darts_entity")

    val CHAIR_ENTITY: EntityType<ChairEntity> =
        FabricEntityTypeBuilder.create(SpawnGroup.MISC, ::ChairEntity)
            .dimensions(EntityDimensions.fixed(0.001F, 0.001F))
            .build()
            .create("chair_entity")

    init {
        ENTITIES.keys.forEach { entityType ->
            Registry.register(Registries.ENTITY_TYPE, ENTITIES[entityType], entityType)
        }
    }

    private fun <T : EntityType<*>> T.create(id: String): T = this.apply {
        ENTITIES[this] = Identifier(SummerCottage.MOD_ID, id)
    }
}