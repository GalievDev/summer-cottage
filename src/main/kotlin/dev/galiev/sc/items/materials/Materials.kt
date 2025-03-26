package dev.galiev.sc.items.materials

import dev.galiev.sc.SummerCottage
import net.minecraft.block.Blocks
import net.minecraft.item.ArmorItem
import net.minecraft.item.ArmorMaterial
import net.minecraft.recipe.Ingredient
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.util.Identifier
import java.util.function.Supplier


object Materials {
    val GARDENER_CLOTH_ARMOR_MATERIAL: RegistryEntry<ArmorMaterial> = registerMaterial( "gardener_cloth_material",
        mapOf(
            ArmorItem.Type.HELMET to 2,
            ArmorItem.Type.CHESTPLATE to 3,
            ArmorItem.Type.LEGGINGS to 2,
            ArmorItem.Type.BOOTS to 1,
        ),
        5,
        SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
        { Ingredient.ofItems(Blocks.WHITE_WOOL.asItem()) },
        0F,
        0F
    )

    val FISHERMAN_CLOTH_ARMOR_MATERIAL: RegistryEntry<ArmorMaterial> = registerMaterial( "fisherman_cloth_material",
        mapOf(
            ArmorItem.Type.HELMET to 2,
            ArmorItem.Type.CHESTPLATE to 3,
            ArmorItem.Type.LEGGINGS to 2,
            ArmorItem.Type.BOOTS to 1,
        ),
        5,
        SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
        { Ingredient.ofItems(Blocks.WHITE_WOOL.asItem()) },
        0F,
        0F
    )

    private fun registerMaterial(
        id: String?,
        defensePoints: Map<ArmorItem.Type?, Int?>?,
        enchantability: Int,
        equipSound: RegistryEntry<SoundEvent?>?,
        repairIngredientSupplier: Supplier<Ingredient?>?,
        toughness: Float,
        knockbackResistance: Float,
    ): RegistryEntry<ArmorMaterial> {
        val layers =
            listOf(
                ArmorMaterial.Layer(Identifier.of(SummerCottage.MOD_ID, id), "", false)
            )

        var material = ArmorMaterial(
            defensePoints,
            enchantability,
            equipSound,
            repairIngredientSupplier,
            layers,
            toughness,
            knockbackResistance
        )
        material = Registry.register(Registries.ARMOR_MATERIAL, Identifier.of(SummerCottage.MOD_ID, id), material)

        return RegistryEntry.of(material)
    }
}