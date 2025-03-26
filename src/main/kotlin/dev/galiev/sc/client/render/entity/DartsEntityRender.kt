package dev.galiev.sc.client.render.entity

import dev.galiev.sc.SummerCottage.MOD_ID
import dev.galiev.sc.client.model.DartsEntityModel
import dev.galiev.sc.client.model.ModelLayers
import dev.galiev.sc.enity.custom.DartsEntity
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.render.OverlayTexture
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.EntityRenderer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.state.ProjectileEntityRenderState
import net.minecraft.client.render.item.ItemRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier
import net.minecraft.util.math.RotationAxis


@Environment(EnvType.CLIENT)
class DartsEntityRender(
    ctx: EntityRendererFactory.Context,
    private val model: DartsEntityModel = DartsEntityModel(ctx.getPart(ModelLayers.DARTS))
) : EntityRenderer<DartsEntity, ProjectileEntityRenderState>(ctx) {

    companion object {
        val TEXTURE: Identifier = Identifier.of(MOD_ID, "textures/entity/darts.png")
    }

    override fun render(
        state: ProjectileEntityRenderState,
        matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider,
        light: Int
    ) {
        matrices.push()

        matrices.multiply(
            RotationAxis.POSITIVE_Y.rotationDegrees(state.yaw - 90.0f)
        )

        matrices.multiply(
            RotationAxis.POSITIVE_Z.rotationDegrees(state.pitch)
        )

        val vertexConsumer = ItemRenderer.getItemGlintConsumer(
            vertexConsumers,
            model.getLayer(TEXTURE),
            false,
            false
        )
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV)

        matrices.pop()
        super.render(state, matrices, vertexConsumers, light)
    }

    override fun createRenderState(): ProjectileEntityRenderState = ProjectileEntityRenderState()
}