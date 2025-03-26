package dev.galiev.sc.client

import dev.galiev.sc.blocks.custom.entity.ChairEntity
import dev.galiev.sc.client.model.DartsEntityModel
import dev.galiev.sc.client.model.ModelLayers
import dev.galiev.sc.client.render.entity.DartsEntityRender
import dev.galiev.sc.enity.EntitiesRegistry
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.minecraft.client.render.Frustum
import net.minecraft.client.render.entity.EntityRenderer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.state.EntityRenderState

class SummerCottageClient: ClientModInitializer {
    override fun onInitializeClient() {
        EntityRendererRegistry.register(EntitiesRegistry.CHAIR_ENTITY, ::EmptyRenderer)
        EntityRendererRegistry.register(EntitiesRegistry.DARTS_ENTITY, ::DartsEntityRender)
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.DARTS, DartsEntityModel::getTexturedModelData)
    }
}

private class EmptyRenderer(ctx: EntityRendererFactory.Context?) : EntityRenderer<ChairEntity, EntityRenderState>(ctx) {
    override fun shouldRender(entity: ChairEntity?, frustum: Frustum?, x: Double, y: Double, z: Double): Boolean {
        return false
    }

    override fun createRenderState(): EntityRenderState = EntityRenderState()
}