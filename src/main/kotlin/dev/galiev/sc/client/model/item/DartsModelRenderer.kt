package dev.galiev.sc.client.model.item

import com.mojang.serialization.MapCodec
import dev.galiev.sc.SummerCottage.MOD_ID
import dev.galiev.sc.client.model.DartsEntityModel
import dev.galiev.sc.client.model.ModelLayers
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.model.LoadedEntityModels
import net.minecraft.client.render.item.ItemRenderer
import net.minecraft.client.render.item.model.special.SimpleSpecialModelRenderer
import net.minecraft.client.render.item.model.special.SpecialModelRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.ModelTransformationMode
import net.minecraft.util.Identifier

class DartsModelRenderer(val model: DartsEntityModel): SimpleSpecialModelRenderer {
    override fun render(
        modelTransformationMode: ModelTransformationMode,
        matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider,
        light: Int,
        overlay: Int,
        glint: Boolean
    ) {
        matrices.push()
        matrices.scale(1.0f, -1.0f, -1.0f)
        model.render(
            matrices, ItemRenderer.getItemGlintConsumer(
                vertexConsumers,
                model.getLayer(Identifier.of(MOD_ID, "textures/entity/darts.png")),
                false,
                glint
            ), light, overlay
        )
        matrices.pop()
    }

    object Unbaked : SpecialModelRenderer.Unbaked {
        val CODEC: MapCodec<Unbaked> = MapCodec.unit(Unbaked)

        override fun getCodec(): MapCodec<Unbaked> = CODEC

        override fun bake(entityModels: LoadedEntityModels): SpecialModelRenderer<*> {
            return DartsModelRenderer(DartsEntityModel(entityModels.getModelPart(ModelLayers.DARTS)))
        }
    }
}