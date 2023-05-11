package dev.galiev.sc.items.materials

import dev.galiev.sc.SummerCottage.MOD_ID
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object Materials {
    val CLOTH_MATERIAL = ClothMaterial()

    init {
        Registry.register(Registries.MATERIAL_CONDITION, Identifier(MOD_ID, "cloth_material"), CLOTH_MATERIAL)
    }
}