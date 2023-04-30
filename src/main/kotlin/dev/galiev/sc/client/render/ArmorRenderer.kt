package dev.galiev.sc.client.render

import dev.galiev.sc.items.clothes.ClothArmorItem
import jdk.vm.ci.code.Location.stack
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
            val hat = head.item as ClothArmorItem
            val itemRenderer: ItemRenderer = MinecraftClient.getInstance().getItemRenderer()
            val height: Double = hat.getHeight()
            val size: Float = hat.getSize()

            matrices!!.push()
            biped!!.head.rotate(matrices)
            matrices!!.translate(0.0, -1.0, 0.0)
            matrices!!.translate(0.0, -height, 0.0)
            matrices!!.scale(size, size, size)
            matrices!!.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180.0f))
            matrices!!.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f))

            val model = ModelIdentifier("villager-hats", hat.getHatName(), "inventory")
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
            matrices!!.pop()
        }
    }

}