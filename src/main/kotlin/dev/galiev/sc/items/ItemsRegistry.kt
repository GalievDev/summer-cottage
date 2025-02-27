package dev.galiev.sc.items

import dev.galiev.sc.SummerCottage.MOD_ID
import dev.galiev.sc.SummerCottage.SUMMER_COTTAGE
import dev.galiev.sc.items.clothes.GardenerClothItem
import dev.galiev.sc.items.clothes.RubberClothItem
import dev.galiev.sc.items.custom.Darts
import dev.galiev.sc.items.custom.Rake
import dev.galiev.sc.items.custom.WaterCan
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.ArmorItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier


object ItemsRegistry {
    private val ITEMS: MutableMap<Item, Identifier> = LinkedHashMap()

    val WATER_CAN = WaterCan().create("water_can")

    val RAKE = Rake().create("rake")

    val DARTS = Darts().create("darts")

    val GARDENER_HAT = GardenerClothItem(ArmorItem.Type.HELMET).create("gardener_hat")

    val GARDENER_SHIRT = GardenerClothItem(ArmorItem.Type.CHESTPLATE).create("gardener_shirt")

    val GARDENER_LEGGINGS = GardenerClothItem(ArmorItem.Type.LEGGINGS).create("gardener_leggings")

    val GARDENER_BOOTS = GardenerClothItem(ArmorItem.Type.BOOTS).create("gardener_boots")

    val FISHERMAN_HAT = RubberClothItem(ArmorItem.Type.HELMET).create("fisherman_hat")

    val FISHERMAN_SHIRT = RubberClothItem(ArmorItem.Type.CHESTPLATE).create("fisherman_shirt")

    val FISHERMAN_LEGGINGS = RubberClothItem(ArmorItem.Type.LEGGINGS).create("fisherman_leggings")

    val FLIP_FLOPS = RubberClothItem(ArmorItem.Type.BOOTS).create("flip_flops")

    init {
        ITEMS.keys.forEach { item ->
            Registry.register(Registries.ITEM, ITEMS[item], item)
            ItemGroupEvents.modifyEntriesEvent(SUMMER_COTTAGE).register {
                it.add(item)
            }
        }
    }

    private fun Item.create(id: String): Item = this.apply {
        ITEMS[this] = Identifier(MOD_ID, id)
    }
}