package dev.galiev.sc.client.model

import dev.galiev.sc.SummerCottage.MOD_ID
import net.minecraft.client.render.entity.model.EntityModelLayer
import net.minecraft.util.Identifier

object ModelLayers {
    val DARTS: EntityModelLayer = EntityModelLayer(Identifier.of(MOD_ID, "darts_3d"), "group")
}