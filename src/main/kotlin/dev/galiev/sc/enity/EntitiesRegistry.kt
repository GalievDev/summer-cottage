package dev.galiev.sc.enity

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
    val DARTS_ENTITY: EntityType<DartsEntity> = Registry.register(
        Registries.ENTITY_TYPE,
        Identifier.of(MOD_ID, "darts"),
        EntityType.Builder.create(::DartsEntity, SpawnGroup.MISC)
            .dimensions(0.2F, 0.2F)
            .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(MOD_ID, "darts"))))

    val CHAIR_ENTITY: EntityType<ChairEntity> = Registry.register(
        Registries.ENTITY_TYPE,
        Identifier.of(MOD_ID, "chair_entity"),
        EntityType.Builder.create(::ChairEntity, SpawnGroup.MISC)
            .dimensions(0.001F, 0.001F)
            .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(MOD_ID, "chair_entity"))))
}