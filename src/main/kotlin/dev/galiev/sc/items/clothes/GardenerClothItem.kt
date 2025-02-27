package dev.galiev.sc.items.clothes

import dev.galiev.sc.items.client.GardenerClothRenderer
import dev.galiev.sc.items.materials.Materials
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.client.render.entity.model.BipedEntityModel
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ArmorItem
import net.minecraft.item.ItemStack
import software.bernie.geckolib.animatable.GeoItem
import software.bernie.geckolib.animatable.client.RenderProvider
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache
import software.bernie.geckolib.core.animation.*
import software.bernie.geckolib.core.`object`.PlayState
import java.util.function.Consumer
import java.util.function.Supplier


class GardenerClothItem(type: Type?) : ArmorItem(Materials.CLOTH_ARMOR_MATERIAL, type, FabricItemSettings()), GeoItem {
    private val cache = SingletonAnimatableInstanceCache(this)
    private val renderProvider = GeoItem.makeRenderer(this)

    override fun registerControllers(controllers: AnimatableManager.ControllerRegistrar) {
        controllers.add(AnimationController(this, "controller", 0, ::predicate))
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache {
        return cache
    }

    override fun createRenderer(consumer: Consumer<Any>?) {
        consumer!!.accept(object : RenderProvider {
            var renderer: GardenerClothRenderer? = null

            override fun getHumanoidArmorModel(
                livingEntity: LivingEntity, itemStack: ItemStack,
                equipmentSlot: EquipmentSlot, original: BipedEntityModel<LivingEntity>
            ): BipedEntityModel<LivingEntity> {
                if (renderer == null) renderer = GardenerClothRenderer()

                renderer!!.prepForRender(livingEntity, itemStack, equipmentSlot, original)

                return renderer!!
            }
        })
    }

    override fun getRenderProvider(): Supplier<Any> {
        return renderProvider
    }

    private fun predicate(animationState: AnimationState<GardenerClothItem>): PlayState {
        animationState.controller.setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP))
        return PlayState.CONTINUE
    }
}