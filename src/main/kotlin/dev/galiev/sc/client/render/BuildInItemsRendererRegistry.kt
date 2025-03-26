package dev.galiev.sc.client.render

import dev.galiev.sc.SummerCottage.MOD_ID
import dev.galiev.sc.client.model.item.DartsModelRenderer
import net.minecraft.client.render.item.model.special.SpecialModelTypes
import net.minecraft.util.Identifier

object BuildInItemsRendererRegistry {
    init {
        SpecialModelTypes.ID_MAPPER.put(
            Identifier.of(MOD_ID, "darts"),
            DartsModelRenderer.Unbaked.CODEC
        )
    }
}