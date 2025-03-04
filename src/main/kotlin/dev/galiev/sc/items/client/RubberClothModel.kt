package dev.galiev.sc.items.client

import dev.galiev.sc.SummerCottage.MOD_ID
import dev.galiev.sc.items.clothes.RubberClothItem
import net.minecraft.util.Identifier
import software.bernie.geckolib.model.GeoModel

class RubberClothModel: GeoModel<RubberClothItem>() {
    override fun getModelResource(animatable: RubberClothItem?): Identifier {
        return Identifier(MOD_ID, "geo/fishman_armor.geo.json")
    }

    override fun getTextureResource(animatable: RubberClothItem?): Identifier {
        return Identifier(MOD_ID, "textures/armor/fishman_armor.png")
    }

    override fun getAnimationResource(animatable: RubberClothItem?): Identifier {
        return Identifier(MOD_ID, "animations/fishman_armor.animation.json")
    }
}