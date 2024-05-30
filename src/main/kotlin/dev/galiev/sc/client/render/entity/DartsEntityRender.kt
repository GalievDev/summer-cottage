package dev.galiev.sc.client.render.entity

import dev.galiev.sc.SummerCottage.MOD_ID
import dev.galiev.sc.client.model.DartsEntityModel
import dev.galiev.sc.enity.custom.DartsEntity
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.render.OverlayTexture
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.EntityRenderer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.item.ItemRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.RotationAxis


@Environment(EnvType.CLIENT)
class DartsEntityRender(ctx: EntityRendererFactory.Context?) : EntityRenderer<DartsEntity>(ctx) {

    companion object {
        val TEXTURE = Identifier(MOD_ID, "textures/entity/darts.png")
    }

    private val model = DartsEntityModel(DartsEntityModel.getTexturedModelData().createModel())

    override fun getTexture(entity: DartsEntity?): Identifier = TEXTURE
    override fun render(
        entity: DartsEntity,
        yaw: Float,
        tickDelta: Float,
        matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider,
        light: Int
    ) {
        matrices.push()

        matrices.multiply(
            RotationAxis.POSITIVE_Y.rotationDegrees(
                MathHelper.lerp(
                    tickDelta,
                    entity.prevYaw,
                    entity.yaw
                ) - 120.0f
            )
        )

        matrices.multiply(
            RotationAxis.POSITIVE_Z.rotationDegrees(
                MathHelper.lerp(
                    tickDelta,
                    entity.prevPitch,
                    entity.pitch
                )
            )
        )

        val vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(
            vertexConsumers,
            model.getLayer(getTexture(entity)),
            false,
            false
        )
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f)

        matrices.pop()
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light)
    }
}