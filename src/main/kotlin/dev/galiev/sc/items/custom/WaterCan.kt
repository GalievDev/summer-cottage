package dev.galiev.sc.items.custom

import dev.galiev.sc.SummerCottage.RANDOM
import dev.galiev.sc.helper.NbtHelper
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.CropBlock
import net.minecraft.block.Material
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.particle.ParticleTypes
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Formatting
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.RaycastContext
import net.minecraft.world.World
import net.minecraft.world.WorldEvents

class WaterCan : Item(FabricItemSettings().maxCount(1)) {

    override fun use(world: World?, user: PlayerEntity?, hand: Hand?): TypedActionResult<ItemStack> {
        val stack = user?.getStackInHand(hand)
        NbtHelper.setBoolean(stack, "Water", false)
        val trace: BlockHitResult = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY)

        if (trace.type != HitResult.Type.BLOCK){
            return TypedActionResult.fail(stack)
        }

        val pos = trace.blockPos
        val state = world?.getBlockState(pos)

        if (!NbtHelper.getBoolean(stack, "Water")) {
            if (state?.material == Material.WATER) {
                NbtHelper.setInt(stack, "Milliliters", 1000)
                NbtHelper.setBoolean(stack, "Water", true)
                user?.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F)

                return TypedActionResult.success(stack)
            } else {
                return TypedActionResult.fail(stack)
            }
        }
        return TypedActionResult.fail(stack)
    }

    override fun useOnBlock(context: ItemUsageContext?): ActionResult {
        val world = context?.world
        val item = context?.stack
        val blockPos = context?.blockPos ?: return ActionResult.FAIL

        if (NbtHelper.getBoolean(item, "Water")) {
            for (targetPos in BlockPos.iterate(blockPos.add(-1, 0, -1), blockPos.add(1, 0, 1))) {
                useOnFertilizable(targetPos, world!!, item!!)
            }
            if (!world?.isClient!!) {
                world.syncWorldEvent(WorldEvents.POINTED_DRIPSTONE_DRIPS_WATER_INTO_CAULDRON, blockPos, 0)
            }
            return ActionResult.success(world.isClient)
        } else return ActionResult.FAIL
    }

    private fun useOnFertilizable(pos: BlockPos, world: World, stack: ItemStack): Boolean {
        val blockState = world.getBlockState(pos)
        if (blockState.block is CropBlock && (blockState.block as CropBlock).isFertilizable(world, pos, blockState, world.isClient)) {
            if (world is ServerWorld){
                if ((blockState.block as CropBlock).canGrow(world, world.random, pos, blockState) && NbtHelper.getInt(stack, "Milliliters") >= 100) {
                    (blockState.block as CropBlock).grow(world, world.random, pos, blockState)
                    NbtHelper.setInt(stack, "Milliliters", NbtHelper.getInt(stack, "Milliliters") - RANDOM.nextInt(50, 100))
                } else if (NbtHelper.getInt(stack, "Milliliters") <= 100) {
                    NbtHelper.setBoolean(stack, "Water", false)
                }
            } else if (world.isClient) {
                world.addParticle(ParticleTypes.RAIN, pos.x + 0.5, pos.y + 0.5, pos.z + 0.5, 0.2, 0.1, 0.3)
            }
            return true
        }
        return false
    }


    override fun appendTooltip(stack: ItemStack?, world: World?, tooltip: MutableList<Text>?, context: TooltipContext?) {
        val waterCount = NbtHelper.getInt(stack, "Milliliters")

        tooltip?.add(Text.literal("$waterCount/1000 Milliliters").formatted(Formatting.AQUA))

        super.appendTooltip(stack, world, tooltip, context)
    }
}
