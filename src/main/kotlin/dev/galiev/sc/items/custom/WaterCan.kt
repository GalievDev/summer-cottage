package dev.galiev.sc.items.custom

import dev.galiev.sc.SummerCottage
import dev.galiev.sc.helper.NbtHelper
import net.minecraft.block.CropBlock
import net.minecraft.block.Material
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import net.minecraft.util.*
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.RaycastContext
import net.minecraft.world.World
import net.minecraft.world.WorldEvents

class WaterCan(settings: Settings?) : Item(settings) {
    override fun getUseAction(stack: ItemStack?): UseAction {
        return UseAction.BOW
    }
    override fun use(world: World?, user: PlayerEntity?, hand: Hand?): TypedActionResult<ItemStack> {
        val stack = user?.getStackInHand(hand)
        NbtHelper.setBoolean(stack, "Water", false)
        val trace: BlockHitResult = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY)

        if (trace.type != HitResult.Type.BLOCK){
            return TypedActionResult.pass(stack)
        }

        val pos = trace.blockPos
        val state = world?.getBlockState(pos)

        if (!NbtHelper.getBoolean(stack, "Water")) {
            if (state?.material == Material.WATER) {
                NbtHelper.setInt(stack, "Liters", 1000)
                NbtHelper.setBoolean(stack, "Water", true)
                user?.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F)

                SummerCottage.logger.info("Water: ${NbtHelper.getInt(stack, "Liters")}")

                return TypedActionResult.success(stack)
            }
        }
        return TypedActionResult.pass(stack)
    }

    override fun useOnBlock(context: ItemUsageContext?): ActionResult {
        val world = context?.world
        val item = context?.stack
        val blockPos = context?.blockPos

        if (NbtHelper.getBoolean(item, "Liters") && useOnFertilizable(blockPos?.x!!, blockPos.y, blockPos.z, world!!, item!!)) {
            useOnFertilizable(blockPos.x + 1, blockPos.y, blockPos.z, world, item)
            useOnFertilizable(blockPos.x - 1, blockPos.y, blockPos.z, world, item)
            useOnFertilizable(blockPos.x, blockPos.y, blockPos.z + 1, world, item)
            useOnFertilizable(blockPos.x, blockPos.y, blockPos.z - 1, world, item)
            useOnFertilizable(blockPos.x + 1, blockPos.y, blockPos.z - 1, world, item)
            useOnFertilizable(blockPos.x + 1, blockPos.y, blockPos.z + 1, world, item)
            useOnFertilizable(blockPos.x - 1, blockPos.y, blockPos.z + 1, world, item)
            useOnFertilizable(blockPos.x - 1, blockPos.y, blockPos.z - 1, world, item)
            if (!world.isClient) {
                world.syncWorldEvent(WorldEvents.POINTED_DRIPSTONE_DRIPS_WATER_INTO_CAULDRON, blockPos, 0)
            }
            return ActionResult.success(world.isClient)
        }

        return ActionResult.PASS
    }

    fun useOnFertilizable(x: Int, y: Int, z: Int, world: World, stack: ItemStack): Boolean {
        val blockPos: BlockPos.Mutable = BlockPos.Mutable(x,y,z)
        val blockState = world.getBlockState(blockPos)
        if (blockState.block is CropBlock && (blockState.block as CropBlock).isFertilizable(world, blockPos, blockState, world.isClient)) {
            if (world is ServerWorld){
                if ((blockState.block as CropBlock).canGrow(world, world.random, blockPos, blockState)) {
                    (blockState.block as CropBlock).grow(world, world.random, blockPos, blockState)
                }
                NbtHelper.setInt(stack, "Liters", NbtHelper.getInt(stack, "Liters") - 100)
            }
            return true
        }
        return false
    }


    override fun appendTooltip(stack: ItemStack?, world: World?, tooltip: MutableList<Text>?, context: TooltipContext?) {
        val waterCount = NbtHelper.getInt(stack, "Liters")
        if (Screen.hasShiftDown()) {
            tooltip?.add(Text.literal("$waterCount/1000 Milliliters").formatted(Formatting.AQUA))
        } else {
            tooltip?.add(Text.literal("Press Shift for more info").formatted(Formatting.YELLOW))
        }

        super.appendTooltip(stack, world, tooltip, context)
    }
}
