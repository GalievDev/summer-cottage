package dev.galiev.sc.client

import dev.galiev.sc.client.render.ArmorRenderer
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback
import net.minecraft.client.render.entity.PlayerEntityRenderer

class SummerCottageClient: ClientModInitializer {
    override fun onInitializeClient() {
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register(LivingEntityFeatureRendererRegistrationCallback
        { entityType, entityRenderer, registrationHelper, context ->
            if (entityRenderer is PlayerEntityRenderer) {
                registrationHelper.register(ArmorRenderer(entityRenderer))
            }
        })
    }
}