package dev.galiev.sc.items.client

import dev.galiev.sc.SummerCottage.MOD_ID
import dev.galiev.sc.items.clothes.FishermanClothItem
import net.minecraft.util.Identifier
import software.bernie.geckolib.model.GeoModel

class FishermanClothModel: GeoModel<FishermanClothItem>() {
    override fun getModelResource(animatable: FishermanClothItem?): Identifier {
        return Identifier.of(MOD_ID, "geo/fishman_armor.geo.json")
    }

    override fun getTextureResource(animatable: FishermanClothItem?): Identifier {
        return Identifier.of(MOD_ID, "textures/armor/fishman_armor.png")
    }

    override fun getAnimationResource(animatable: FishermanClothItem?): Identifier {
        return Identifier.of(MOD_ID, "animations/fishman_armor.animation.json")
    }
}