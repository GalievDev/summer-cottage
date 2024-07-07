package dev.galiev.sc.items.client

import dev.galiev.sc.items.clothes.GardenerClothItem
import software.bernie.geckolib.renderer.GeoArmorRenderer

class GardenerClothRenderer : GeoArmorRenderer<GardenerClothItem>(
    GardenerClothModel()
) {
}