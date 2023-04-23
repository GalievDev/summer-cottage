package dev.galiev.sc.items

import dev.galiev.sc.SummerCottage.MOD_ID
import dev.galiev.sc.SummerCottage.SUMMER_COTTAGE
import dev.galiev.sc.SummerCottage.logger
import dev.galiev.sc.items.custom.Rake
import dev.galiev.sc.items.custom.WaterCan
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents.ModifyEntries
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ToolMaterials
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier


object IRegistry {
    val WATER_CAN = registerItem("water_can", WaterCan(FabricItemSettings()))
    val RAKE = registerItem("rake", Rake(ToolMaterials.DIAMOND, 1, 1F, FabricItemSettings()))

    private fun registerItem(name: String, item: Item): Item? {
        return Registry.register(Registries.ITEM, Identifier(MOD_ID, name), item)
    }

    fun addItemsToItemGroup() {
        addToItemGroup(SUMMER_COTTAGE, WATER_CAN)
        addToItemGroup(SUMMER_COTTAGE, RAKE)
    }

    private fun addToItemGroup(group: ItemGroup, item: Item?) {
        ItemGroupEvents.modifyEntriesEvent(group).register(ModifyEntries { entries -> entries.add(item) })
    }

    fun registerModItems() {
        logger.info("Registering Mod Items for $MOD_ID")
        addItemsToItemGroup()
    }
}