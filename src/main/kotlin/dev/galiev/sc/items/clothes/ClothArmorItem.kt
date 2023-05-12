package dev.galiev.sc.items.clothes

import dev.galiev.sc.items.materials.Materials
import net.minecraft.item.ArmorItem

open class ClothArmorItem(type: Type?, settings: Settings?) : ArmorItem(Materials.CLOTH_ARMOR_MATERIAL, type, settings) {
}