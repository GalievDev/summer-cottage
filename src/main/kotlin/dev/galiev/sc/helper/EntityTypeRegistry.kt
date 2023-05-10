package dev.galiev.sc.helper

import dev.galiev.sc.SummerCottage
import dev.galiev.sc.blocks.custom.entity.ChairEntity
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object EntityTypeRegistry {
    val CHAIR_ENTITY: EntityType<ChairEntity> =
        Registry.register(
            Registries.ENTITY_TYPE,
            Identifier(SummerCottage.MOD_ID, "chair_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, ::ChairEntity)
                .dimensions(EntityDimensions.fixed(0.001F, 0.001F)).build()
        )
}