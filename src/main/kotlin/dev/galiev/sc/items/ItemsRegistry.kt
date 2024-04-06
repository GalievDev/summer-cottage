package dev.galiev.sc.items

import dev.galiev.sc.SummerCottage.MOD_ID
import dev.galiev.sc.SummerCottage.SUMMER_COTTAGE
import dev.galiev.sc.items.clothes.fisherman.FishermanHat
import dev.galiev.sc.items.clothes.fisherman.FishermanLeggings
import dev.galiev.sc.items.clothes.fisherman.FishermanShirt
import dev.galiev.sc.items.clothes.fisherman.FlipFlops
import dev.galiev.sc.items.clothes.gardener.GardenerHat
import dev.galiev.sc.items.clothes.gardener.GardenerLeggings
import dev.galiev.sc.items.clothes.gardener.GardenerShirt
import dev.galiev.sc.items.custom.Darts
import dev.galiev.sc.items.custom.Rake
import dev.galiev.sc.items.custom.WaterCan
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier


object ItemsRegistry {
    private val ITEMS: MutableMap<Item, Identifier> = LinkedHashMap()

    val WATER_CAN = WaterCan().create("water_can")

    val RAKE = Rake().create("rake")

    val DARTS = Darts().create("darts")

    val GARDENER_HAT = GardenerHat().create("gardener_hat")

    val GARDENER_SHIRT = GardenerShirt().create("gardener_shirt")

    val GARDENER_LEGGINGS = GardenerLeggings().create("gardener_leggings")

    val FISHERMAN_HAT = FishermanHat().create("fisherman_hat")

    val FISHERMAN_SHIRT = FishermanShirt().create("fisherman_shirt")

    val FISHERMAN_LEGGINGS = FishermanLeggings().create("fisherman_leggings")

    val FLIP_FLOPS = FlipFlops().create("flip_flops")

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