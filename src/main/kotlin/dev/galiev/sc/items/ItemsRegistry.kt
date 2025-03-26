package dev.galiev.sc.items

import dev.galiev.sc.SummerCottage.MOD_ID
import dev.galiev.sc.SummerCottage.SUMMER_COTTAGE_KEY
import dev.galiev.sc.helper.ComponentHelper
import dev.galiev.sc.items.clothes.FishermanClothItem
import dev.galiev.sc.items.clothes.GardenerClothItem
import dev.galiev.sc.items.custom.Darts
import dev.galiev.sc.items.custom.Rake
import dev.galiev.sc.items.custom.WaterCan
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.item.equipment.EquipmentType
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier


object ItemsRegistry {

    val WATER_CAN = register("water_can", ::WaterCan) {
        component(ComponentHelper.HAS_WATER, false)
        component(ComponentHelper.MILLILITERS, 0)
        maxCount(1)
    }

    val RAKE = register("rake", ::Rake) {
        maxCount(1)
    }

    val DARTS = register("darts", ::Darts) {
    }

    val GARDENER_HAT = register("gardener_hat", {
        GardenerClothItem(EquipmentType.HELMET, it.maxCount(1))
    })

    val GARDENER_SHIRT = register("gardener_shirt", {
        GardenerClothItem(EquipmentType.CHESTPLATE, it.maxCount(1))
    })

    val GARDENER_LEGGINGS = register("gardener_leggings", {
        GardenerClothItem(EquipmentType.LEGGINGS, it.maxCount(1))
    })

    val GARDENER_BOOTS = register("gardener_boots", {
        GardenerClothItem(EquipmentType.BOOTS, it.maxCount(1))
    })

    val FISHERMAN_HAT = register("fisherman_cap", {
        FishermanClothItem(EquipmentType.HELMET, it.maxCount(1))
    })

    val FISHERMAN_SHIRT = register("fisherman_shirt", {
        FishermanClothItem(EquipmentType.HELMET, it.maxCount(1))
    })

    val FISHERMAN_LEGGINGS = register("fisherman_leggings", {
        FishermanClothItem(EquipmentType.LEGGINGS, it.maxCount(1))
    })

    val RUBBER_BOOTS = register("fisherman_boots", {
        FishermanClothItem(EquipmentType.BOOTS, it.maxCount(1))
    })

    private inline fun <reified T : Item> register(
        name: String, crossinline factory: (Item.Settings) -> T, settingsBuilder: Item.Settings.() -> Unit = {}
    ): T {
        val settings = Item.Settings().apply(settingsBuilder)
        val item = Items.register(
            RegistryKey.of(
                RegistryKeys.ITEM, Identifier.of(MOD_ID, name)
            ), { factory(settings) }, settings
        ) as T
        ItemGroupEvents.modifyEntriesEvent(SUMMER_COTTAGE_KEY).register{
            it.add(item)
        }
        return item
    }
}