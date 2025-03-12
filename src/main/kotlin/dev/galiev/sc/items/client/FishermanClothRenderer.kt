package dev.galiev.sc.items.client

import dev.galiev.sc.items.clothes.FishermanClothItem
import software.bernie.geckolib.renderer.GeoArmorRenderer

class FishermanClothRenderer : GeoArmorRenderer<FishermanClothItem>(
    FishermanClothModel()
) {
}