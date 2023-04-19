package dev.galiev.sc.items.custom

import dev.galiev.sc.SummerCottage
import dev.galiev.sc.helper.NbtHelper
import net.minecraft.block.Material
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.UseAction
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.world.RaycastContext
import net.minecraft.world.World

class WaterCan(settings: Settings?) : Item(settings) {
    override fun getUseAction(stack: ItemStack?): UseAction {
        return UseAction.BOW
    }
    override fun use(world: World?, user: PlayerEntity?, hand: Hand?): TypedActionResult<ItemStack> {
        val stack = user?.getStackInHand(hand)
        val trace: BlockHitResult = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY)

        if (trace.type != HitResult.Type.BLOCK){
            return TypedActionResult.pass(stack)
        }

        val pos = trace.blockPos
        val state = world?.getBlockState(pos)

        if (state?.material == Material.WATER){
            if (NbtHelper.getInt(stack, "Liters") < 1000 && !NbtHelper.getBoolean(stack, "Water")) {
                NbtHelper.setInt(stack, "Liters", 100)
            } else {
                NbtHelper.setBoolean(stack, "Water", true)
            }
            user?.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F)

            SummerCottage.logger.info("Water: ${NbtHelper.getInt(stack, "Liters")}")

            return TypedActionResult.success(stack)
        } else {
            user?.playSound(SoundEvents.ENTITY_VILLAGER_NO, 1.0F, 1.0F)
        }

        return TypedActionResult.pass(stack)
    }

    override fun appendTooltip(stack: ItemStack?, world: World?, tooltip: MutableList<Text>?, context: TooltipContext?) {
        val waterCount = NbtHelper.getInt(stack, "Liters")
        if (Screen.hasShiftDown()) {
            tooltip?.add(Text.literal("$waterCount/1000 Liters").formatted(Formatting.AQUA))
        } else {
            tooltip?.add(Text.literal("Press Shift for more info").formatted(Formatting.YELLOW))
        }

        super.appendTooltip(stack, world, tooltip, context)
    }
}