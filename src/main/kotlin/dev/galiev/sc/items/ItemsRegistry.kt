package dev.galiev.sc.items

import dev.galiev.sc.SummerCottage.MOD_ID
import dev.galiev.sc.SummerCottage.SUMMER_COTTAGE_KEY
import dev.galiev.sc.items.clothes.FishermanClothItem
import dev.galiev.sc.items.clothes.GardenerClothItem
import dev.galiev.sc.items.custom.Darts
import dev.galiev.sc.items.custom.Rake
import dev.galiev.sc.items.custom.WaterCan
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.Item
import net.minecraft.item.equipment.EquipmentType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier


object ItemsRegistry {
    private val ITEMS: MutableMap<Item, Identifier> = LinkedHashMap()

    val WATER_CAN = WaterCan().create("water_can")

    val RAKE = Rake().create("rake")

    val DARTS = Darts().create("darts")

    val GARDENER_HAT = GardenerClothItem(EquipmentType.HELMET).create("gardener_hat")

    val GARDENER_SHIRT = GardenerClothItem(EquipmentType.CHESTPLATE).create("gardener_shirt")

    val GARDENER_LEGGINGS = GardenerClothItem(EquipmentType.LEGGINGS).create("gardener_leggings")

    val GARDENER_BOOTS = GardenerClothItem(EquipmentType.BOOTS).create("gardener_boots")

    val FISHERMAN_HAT = FishermanClothItem(EquipmentType.HELMET).create("fisherman_cap")

    val FISHERMAN_SHIRT = FishermanClothItem(EquipmentType.CHESTPLATE).create("fisherman_shirt")

    val FISHERMAN_LEGGINGS = FishermanClothItem(EquipmentType.LEGGINGS).create("fisherman_leggings")

    val RUBBER_BOOTS = FishermanClothItem(EquipmentType.BOOTS).create("fisherman_boots")

    init {
        ITEMS.keys.forEach { item ->
            Registry.register(Registries.ITEM, ITEMS[item], item)
            ItemGroupEvents.modifyEntriesEvent(SUMMER_COTTAGE_KEY).register {
                it.add(item)
            }
        }
    }

    private fun Item.create(id: String): Item = this.apply {
        ITEMS[this] = Identifier.of(MOD_ID, id)
    }
}