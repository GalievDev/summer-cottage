package dev.galiev.sc.enity

import dev.galiev.sc.SummerCottage
import dev.galiev.sc.SummerCottage.MOD_ID
import dev.galiev.sc.blocks.custom.entity.ChairEntity
import dev.galiev.sc.enity.custom.DartsEntity
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier

object EntitiesRegistry {
    private val ENTITIES: MutableMap<EntityType<*>, Identifier> = LinkedHashMap()

    val DARTS_ENTITY: EntityType<DartsEntity> =
        EntityType.Builder.create(::DartsEntity, SpawnGroup.MISC)
            .dimensions(0.2F, 0.2F)
            .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(MOD_ID, "darts")))

    val CHAIR_ENTITY: EntityType<ChairEntity> =
        EntityType.Builder.create(::ChairEntity, SpawnGroup.MISC)
            .dimensions(0.001F, 0.001F)
            .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(MOD_ID, "chair_entity")))

    init {
        ENTITIES.keys.forEach { entityType ->
            Registry.register(Registries.ENTITY_TYPE, ENTITIES[entityType], entityType)
        }
    }

    private fun <T : EntityType<*>> T.create(id: String): T = this.apply {
        ENTITIES[this] = Identifier.of(SummerCottage.MOD_ID, id)
    }
}