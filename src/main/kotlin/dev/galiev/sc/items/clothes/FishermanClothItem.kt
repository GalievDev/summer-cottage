package dev.galiev.sc.items.clothes

import dev.galiev.sc.items.client.FishermanClothRenderer
import dev.galiev.sc.items.materials.Materials
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.item.TooltipContext
import net.minecraft.client.render.entity.model.BipedEntityModel
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ArmorItem
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.world.World
import software.bernie.geckolib.animatable.GeoItem
import software.bernie.geckolib.animatable.client.RenderProvider
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache
import software.bernie.geckolib.core.animation.*
import software.bernie.geckolib.core.`object`.PlayState
import java.util.function.Consumer
import java.util.function.Supplier

class FishermanClothItem(type: Type?) : ArmorItem(Materials.FISHERMAN_CLOTH_ARMOR_MATERIAL, type, FabricItemSettings().maxCount(1)), GeoItem {
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
            var renderer: FishermanClothRenderer? = null

            override fun getHumanoidArmorModel(
                livingEntity: LivingEntity, itemStack: ItemStack,
                equipmentSlot: EquipmentSlot, original: BipedEntityModel<LivingEntity>
            ): BipedEntityModel<LivingEntity> {
                if (renderer == null) renderer = FishermanClothRenderer()

                renderer!!.prepForRender(livingEntity, itemStack, equipmentSlot, original)

                return renderer!!
            }
        })
    }

    override fun getRenderProvider(): Supplier<Any> {
        return renderProvider
    }

    private fun predicate(animationState: AnimationState<FishermanClothItem>): PlayState {
        animationState.controller.setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP))
        return PlayState.CONTINUE
    }

    override fun appendTooltip(
        stack: ItemStack,
        world: World?,
        tooltip: MutableList<Text>,
        context: TooltipContext?
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