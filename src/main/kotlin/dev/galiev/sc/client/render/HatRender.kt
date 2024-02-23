package dev.galiev.sc.client.render

import dev.galiev.sc.items.clothes.gardener.GardenerHat
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.OverlayTexture
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.feature.FeatureRenderer
import net.minecraft.client.render.entity.feature.FeatureRendererContext
import net.minecraft.client.render.entity.model.PlayerEntityModel
import net.minecraft.client.render.item.ItemRenderer
import net.minecraft.client.render.model.json.ModelTransformationMode
import net.minecraft.client.util.ModelIdentifier
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.RotationAxis


class HatRender<T: PlayerEntity, M: PlayerEntityModel<T>>(context: FeatureRendererContext<T, M>?) : FeatureRenderer<T, M>(
    context
) {
    override fun render(matrices: MatrixStack?, vertexConsumers: VertexConsumerProvider?, light: Int, entity: T, limbAngle: Float, limbDistance: Float, tickDelta: Float, animationProgress: Float, headYaw: Float, headPitch: Float) {
        val stack = entity.getEquippedStack(EquipmentSlot.HEAD)
        val biped = this.contextModel

        if (stack.item is GardenerHat) {
            val itemRenderer: ItemRenderer = MinecraftClient.getInstance().itemRenderer
            val height = 0.1
            val size = 1.55F

            matrices!!.push()
            biped.head.rotate(matrices)
            matrices.translate(0.0, -1.0, 0.0)
            matrices.translate(0.0, -height, 0.0)
            matrices.scale(size, size, size)
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180.0f))
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f))

            val model = ModelIdentifier("sc", "gardener_hat", "inventory")
            itemRenderer.renderItem(
                stack,
                ModelTransformationMode.NONE,
                false,
                matrices,
                vertexConsumers,
                light,
                OverlayTexture.DEFAULT_UV,
                itemRenderer.models.modelManager.getModel(model)
            )
            matrices.pop()
        }
    }
}