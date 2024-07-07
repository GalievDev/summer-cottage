package dev.galiev.sc.items.client

import dev.galiev.sc.SummerCottage.MOD_ID
import dev.galiev.sc.items.clothes.GardenerClothItem
import net.minecraft.util.Identifier
import software.bernie.geckolib.model.GeoModel

class GardenerClothModel: GeoModel<GardenerClothItem>() {
    override fun getModelResource(animatable: GardenerClothItem?): Identifier {
        return Identifier(MOD_ID, "geo/gardener.geo.json")
    }

    override fun getTextureResource(animatable: GardenerClothItem?): Identifier {
        return Identifier(MOD_ID, "textures/armor/gardener.png")
    }

    override fun getAnimationResource(animatable: GardenerClothItem?): Identifier {
        return Identifier(MOD_ID, "animations/gardener.animation.json")
    }
}