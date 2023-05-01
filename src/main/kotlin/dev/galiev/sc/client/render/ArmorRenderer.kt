package dev.galiev.sc.client.render

import dev.galiev.sc.items.clothes.ClothArmorItem
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


class ArmorRenderer<T: PlayerEntity?, M: PlayerEntityModel<T>?>(context: FeatureRendererContext<T, M>): FeatureRenderer<T, M>(context) {
    override fun render(
        matrices: MatrixStack?,
        vertexConsumers: VertexConsumerProvider?,
        light: Int,
        entity: T,
        limbAngle: Float,
        limbDistance: Float,
        tickDelta: Float,
        animationProgress: Float,
        headYaw: Float,
        headPitch: Float
    ) {
        val head = entity?.getEquippedStack(EquipmentSlot.HEAD)
        val chest = entity?.getEquippedStack(EquipmentSlot.CHEST)
        val legs = entity?.getEquippedStack(EquipmentSlot.LEGS)

        val biped = contextModel

        if (head?.item is ClothArmorItem && chest?.item is ClothArmorItem && legs?.item is ClothArmorItem) {
            val itemRenderer: ItemRenderer = MinecraftClient.getInstance().itemRenderer
            val height: Double = 0.2
            val size: Float = 1.15F

            matrices!!.push()
            biped!!.head.rotate(matrices)
            matrices.translate(0.0, -1.0, 0.0)
            matrices.translate(0.0, -height, 0.0)
            matrices.scale(size, size, size)
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180.0f))
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f))

            val modelHead = ModelIdentifier("summer-cottage", head.item.name.toString(), "inventory")
            val modelChest = ModelIdentifier("summer-cottage", chest.item.name.toString(), "inventory")
            val modelLegs = ModelIdentifier("summer-cottage", legs.item.name.toString(), "inventory")
            itemRenderer.renderItem(
                head,
                ModelTransformationMode.NONE,
                false,
                matrices,
                vertexConsumers,
                light,
                OverlayTexture.DEFAULT_UV,
                itemRenderer.models.modelManager.getModel(modelHead)
            )
            itemRenderer.renderItem(
                head,
                ModelTransformationMode.NONE,
                false,
                matrices,
                vertexConsumers,
                light,
                OverlayTexture.DEFAULT_UV,
                itemRenderer.models.modelManager.getModel(modelChest)
            )
            itemRenderer.renderItem(
                head,
                ModelTransformationMode.NONE,
                false,
                matrices,
                vertexConsumers,
                light,
                OverlayTexture.DEFAULT_UV,
                itemRenderer.models.modelManager.getModel(modelLegs)
            )
            matrices.pop()
        }
    }

}