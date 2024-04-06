package dev.galiev.sc.client

import dev.galiev.sc.blocks.custom.entity.ChairEntity
import dev.galiev.sc.client.model.DartsEntityModel
import dev.galiev.sc.client.model.ModelLayers
import dev.galiev.sc.client.render.HatRender
import dev.galiev.sc.client.render.ShirtRender
import dev.galiev.sc.client.render.entity.DartsEntityRender
import dev.galiev.sc.enity.EntitiesRegistry
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback
import net.minecraft.client.render.Frustum
import net.minecraft.client.render.entity.EntityRenderer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.PlayerEntityRenderer
import net.minecraft.util.Identifier

class SummerCottageClient: ClientModInitializer {
    override fun onInitializeClient() {
        EntityRendererRegistry.register(EntitiesRegistry.CHAIR_ENTITY, ::EmptyRenderer)
        EntityRendererRegistry.register(EntitiesRegistry.DARTS_ENTITY, ::DartsEntityRender)
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.DARTS, DartsEntityModel::getTexturedModelData)

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register(LivingEntityFeatureRendererRegistrationCallback
        { _, entityRenderer, registrationHelper, _ ->
            if (entityRenderer is PlayerEntityRenderer) {
                registrationHelper.register(HatRender(entityRenderer))
                registrationHelper.register(ShirtRender(entityRenderer))
            }
        })
    }
}

private class EmptyRenderer(ctx: EntityRendererFactory.Context?) : EntityRenderer<ChairEntity>(ctx) {
    override fun shouldRender(entity: ChairEntity?, frustum: Frustum?, x: Double, y: Double, z: Double): Boolean {
        return false
    }
    override fun getTexture(entity: ChairEntity?): Identifier? {
        return null
    }
}