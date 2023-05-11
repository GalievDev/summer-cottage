package dev.galiev.sc.client

import dev.galiev.sc.blocks.custom.entity.ChairEntity
import dev.galiev.sc.client.render.ArmorRenderer
import dev.galiev.sc.helper.EntityTypeRegistry
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback
import net.minecraft.client.render.Frustum
import net.minecraft.client.render.entity.EntityRenderer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.PlayerEntityRenderer
import net.minecraft.util.Identifier

class SummerCottageClient: ClientModInitializer {
    override fun onInitializeClient() {
        EntityRendererRegistry.register(EntityTypeRegistry.CHAIR_ENTITY, ::EmptyRenderer)
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register(LivingEntityFeatureRendererRegistrationCallback
        { entityType, entityRenderer, registrationHelper, context ->
            if (entityRenderer is PlayerEntityRenderer) {
                registrationHelper.register(ArmorRenderer(entityRenderer))
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