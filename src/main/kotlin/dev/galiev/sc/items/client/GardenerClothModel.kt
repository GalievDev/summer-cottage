package dev.galiev.sc.items.client

import dev.galiev.sc.SummerCottage.MOD_ID
import dev.galiev.sc.items.clothes.GardenerClothItem
import net.minecraft.util.Identifier
import software.bernie.geckolib.model.GeoModel

class GardenerClothModel: GeoModel<GardenerClothItem>() {
    override fun getModelResource(animatable: GardenerClothItem?): Identifier {
        return Identifier.of(MOD_ID, "geo/gardener_armor.geo.json")
    }

    override fun getTextureResource(animatable: GardenerClothItem?): Identifier {
        return Identifier.of(MOD_ID, "textures/armor/gardener_armor.png")
    }

    override fun getAnimationResource(animatable: GardenerClothItem?): Identifier {
        return Identifier.of(MOD_ID, "animations/gardener_armor.animation.json")
    }
}