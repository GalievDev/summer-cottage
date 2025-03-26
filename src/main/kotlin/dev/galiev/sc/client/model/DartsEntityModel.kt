package dev.galiev.sc.client.model

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.model.*
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.util.math.MatrixStack


@Environment(EnvType.CLIENT)
class DartsEntityModel(root: ModelPart) : Model(RenderLayer::getEntitySolid) {
    private val group: ModelPart = root.getChild("group")

    companion object {
        fun getTexturedModelData(): TexturedModelData {
            val modelData = ModelData()
            val modelPart = modelData.root

            val group = modelPart.addChild(
                "group",
                ModelPartBuilder.create().uv(0, 8)
                    .cuboid(0.25f, 1.5f, 3.0f, 0.0f, 1.0f, 1.5f, Dilation(0.0f))
                    .uv(6, 0).cuboid(-1.0f, 1.25f, 3.0f, 1.0f, 0.0f, 1.5f, Dilation(0.0f))
                    .uv(6, 5).cuboid(0.25f, 0.0f, 3.0f, 0.0f, 1.0f, 1.5f, Dilation(0.0f))
                    .uv(0, 11).cuboid(0.25f, 0.5f, 4.5f, 0.0f, 0.5f, 0.5f, Dilation(0.0f))
                    .uv(4, 11).cuboid(0.25f, 1.0f, 4.0f, 0.0f, 0.5f, 0.5f, Dilation(0.0f))
                    .uv(2, 11).cuboid(0.25f, 1.5f, 4.5f, 0.0f, 0.5f, 0.5f, Dilation(0.0f))
                    .uv(0, 6).cuboid(0.5f, 1.25f, 3.0f, 1.0f, 0.0f, 1.5f, Dilation(0.0f))
                    .uv(10, 5).cuboid(0.5f, 1.25f, 4.5f, 0.5f, 0.0f, 0.5f, Dilation(0.0f))
                    .uv(10, 6).cuboid(-0.5f, 1.25f, 4.5f, 0.5f, 0.0f, 0.5f, Dilation(0.0f))
                    .uv(10, 7).cuboid(0.0f, 1.25f, 4.0f, 0.5f, 0.0f, 0.5f, Dilation(0.0f))
                    .uv(0, 3).cuboid(0.0f, 0.75f, 1.0f, 0.5f, 1.0f, 1.5f, Dilation(0.0f))
                    .uv(4, 8).cuboid(-0.25f, 1.0f, 1.0f, 0.25f, 0.5f, 1.5f, Dilation(0.0f))
                    .uv(8, 8).cuboid(0.5f, 1.0f, 1.0f, 0.25f, 0.5f, 1.5f, Dilation(0.0f))
                    .uv(6, 2).cuboid(0.0f, 1.0f, 2.5f, 0.5f, 0.5f, 1.5f, Dilation(0.0f))
                    .uv(0, 0).cuboid(0.0f, 1.0f, -1.0f, 0.5f, 0.5f, 2.0f, Dilation(0.0f)),
                ModelTransform.rotation(0.0f, 23.5f, 0.0f)
            )

            return TexturedModelData.of(modelData, 16, 16)
        }
    }

    override fun render(matrices: MatrixStack?, vertices: VertexConsumer?, light: Int, overlay: Int, color: Int) {
        group.render(matrices, vertices, light, overlay, color)
    }
}
