package dev.galiev.sc.items.clothes

import dev.galiev.sc.items.client.FishermanClothRenderer
import dev.galiev.sc.items.materials.Materials
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.render.entity.model.BipedEntityModel
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ArmorItem
import net.minecraft.item.ItemStack
import net.minecraft.item.tooltip.TooltipType
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import software.bernie.geckolib.animatable.GeoItem
import software.bernie.geckolib.animatable.client.GeoRenderProvider
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.animation.*
import software.bernie.geckolib.util.GeckoLibUtil
import java.util.function.Consumer

class FishermanClothItem(type: Type?) : ArmorItem(Materials.FISHERMAN_CLOTH_ARMOR_MATERIAL, type, Settings()), GeoItem {
    private val cache = GeckoLibUtil.createInstanceCache(this);

    override fun registerControllers(controllers: AnimatableManager.ControllerRegistrar) {
        controllers.add(AnimationController(this, "controller", 0, ::predicate))
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache {
        return cache
    }

    override fun createGeoRenderer(consumer: Consumer<GeoRenderProvider>) {
        consumer.accept(object : GeoRenderProvider {
            var renderer: FishermanClothRenderer? = null

            override fun <T : LivingEntity?> getGeoArmorRenderer(
                livingEntity: T?,
                itemStack: ItemStack?,
                equipmentSlot: EquipmentSlot?,
                original: BipedEntityModel<T>?
            ): BipedEntityModel<*>? {
                if (renderer == null) renderer = FishermanClothRenderer()

                renderer!!.prepForRender(livingEntity, itemStack, equipmentSlot, original)

                return renderer!!
            }
        })
    }

    private fun predicate(animationState: AnimationState<FishermanClothItem>): PlayState {
        animationState.controller.setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP))
        return PlayState.CONTINUE
    }

    override fun appendTooltip(
        stack: ItemStack,
        context: TooltipContext,
        tooltip: MutableList<Text>,
        type: TooltipType
    ) {
        val text = Text.translatable("text.fisherman_set")
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