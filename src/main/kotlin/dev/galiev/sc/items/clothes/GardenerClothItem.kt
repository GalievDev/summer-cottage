package dev.galiev.sc.items.clothes

import dev.galiev.sc.items.client.GardenerClothRenderer
import dev.galiev.sc.items.materials.Materials
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.render.entity.equipment.EquipmentModel
import net.minecraft.client.render.entity.model.BipedEntityModel
import net.minecraft.client.render.entity.state.BipedEntityRenderState
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ArmorItem
import net.minecraft.item.ItemStack
import net.minecraft.item.equipment.EquipmentType
import net.minecraft.item.tooltip.TooltipType
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import software.bernie.geckolib.animatable.GeoItem
import software.bernie.geckolib.animatable.client.GeoRenderProvider
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.animation.*
import software.bernie.geckolib.util.GeckoLibUtil
import java.util.function.Consumer


class GardenerClothItem(type: EquipmentType) : ArmorItem(Materials.GARDENER_CLOTH_ARMOR_MATERIAL, type, Settings().maxCount(1)), GeoItem {
    private val cache = GeckoLibUtil.createInstanceCache(this)

    override fun registerControllers(controllers: AnimatableManager.ControllerRegistrar) {
        controllers.add(AnimationController(this, "controller", 0, ::predicate))
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache {
        return cache
    }

    override fun createGeoRenderer(consumer: Consumer<GeoRenderProvider>?) {
        consumer!!.accept(object : GeoRenderProvider {
            var renderer: GardenerClothRenderer? = null

            override fun <E : LivingEntity?, S : BipedEntityRenderState?> getGeoArmorRenderer(
                livingEntity: E?,
                itemStack: ItemStack?,
                equipmentSlot: EquipmentSlot?,
                type: EquipmentModel.LayerType?,
                original: BipedEntityModel<S>?
            ): BipedEntityModel<*>? {
                if (renderer == null) renderer = GardenerClothRenderer()

                return renderer!!
            }
        })
    }

    private fun predicate(animationState: AnimationState<GardenerClothItem>): PlayState {
        animationState.controller.setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP))
        return PlayState.CONTINUE
    }

    override fun appendTooltip(
        stack: ItemStack,
        context: TooltipContext,
        tooltip: MutableList<Text>,
        type: TooltipType
    ) {
        val text = Text.translatable("text.gardener_set")
        val split = text.string.split("\\n".toRegex())
        if (Screen.hasShiftDown()) {
            split.forEach { line ->
                tooltip.add(Text.literal(line).formatted(Formatting.YELLOW))
            }
        } else {
            tooltip.add(Text.translatable("text.press_shift").formatted(Formatting.YELLOW))
        }
    }
}