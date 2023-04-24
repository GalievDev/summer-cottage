package dev.galiev.sc.items

import dev.galiev.sc.SummerCottage.MOD_ID
import dev.galiev.sc.SummerCottage.SUMMER_COTTAGE
import dev.galiev.sc.items.custom.Rake
import dev.galiev.sc.items.custom.WaterCan
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents.ModifyEntries
import net.minecraft.item.*
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier


object IRegistry {

    val WATER_CAN = registerItem("water_can", WaterCan(FabricItemSettings()))
    val RAKE = registerItem("rake", Rake(ToolMaterials.DIAMOND, 1, 1F, FabricItemSettings()))

    val GARDENER_HAT = registerItem("gardener_hat",
        ArmorItem(ArmorMaterials.LEATHER, ArmorItem.Type.HELMET, FabricItemSettings()))

    val GARDENER_SHIRT = registerItem("gardener_shirt",
        ArmorItem(ArmorMaterials.LEATHER, ArmorItem.Type.CHESTPLATE, FabricItemSettings()))

    val GARDENER_LEGGINGS = registerItem("gardener_leggings",
        ArmorItem(ArmorMaterials.LEATHER, ArmorItem.Type.LEGGINGS, FabricItemSettings()))

    private fun registerItem(name: String, item: Item): Item? {
        return Registry.register(Registries.ITEM, Identifier(MOD_ID, name), item)
    }

    fun addItemsToItemGroup() {
        addToItemGroup(SUMMER_COTTAGE, WATER_CAN)
        addToItemGroup(SUMMER_COTTAGE, RAKE)
        addToItemGroup(SUMMER_COTTAGE, GARDENER_HAT)
        addToItemGroup(SUMMER_COTTAGE, GARDENER_SHIRT)
        addToItemGroup(SUMMER_COTTAGE, GARDENER_LEGGINGS)
    }

    private fun addToItemGroup(group: ItemGroup, item: Item?) {
        ItemGroupEvents.modifyEntriesEvent(group).register(ModifyEntries { entries -> entries.add(item) })
    }

    fun registerModItems() {
        addItemsToItemGroup()
    }
}