package dev.galiev.sc.items.custom

import dev.galiev.sc.SummerCottage.RANDOM
import dev.galiev.sc.helper.ComponentHelper
import net.minecraft.block.Blocks
import net.minecraft.block.CropBlock
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.item.tooltip.TooltipType
import net.minecraft.particle.ParticleTypes
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Formatting
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.RaycastContext
import net.minecraft.world.World
import net.minecraft.world.WorldEvents

class WaterCan : Item(Settings().maxCount(1)) {

    override fun use(world: World, user: PlayerEntity, hand: Hand?): ActionResult {
        val stack = user.getStackInHand(hand)
        stack.set(ComponentHelper.HAS_WATER, false)
        val trace: BlockHitResult = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY)

        if (trace.type != HitResult.Type.BLOCK){
            return ActionResult.FAIL
        }

        val pos = trace.blockPos
        val state = world.getBlockState(pos)

        if (!stack.get(ComponentHelper.HAS_WATER)!!) {
            if (state.block == Blocks.WATER) {
                stack.set(ComponentHelper.MILLILITERS, 1000)
                stack.set(ComponentHelper.HAS_WATER, true)
                user.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F)

                return ActionResult.SUCCESS
            } else {
                return ActionResult.FAIL
            }
        }
        return ActionResult.FAIL
    }

    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        val world = context.world
        val stack = context.stack
        val blockPos = context.blockPos ?: return ActionResult.FAIL

        if (stack.get(ComponentHelper.HAS_WATER)!!) {
            for (targetPos in BlockPos.iterate(blockPos.add(-1, 0, -1), blockPos.add(1, 0, 1))) {
                useOnFertilizable(targetPos, world!!, stack!!)
            }
            if (!world?.isClient!!) {
                world.syncWorldEvent(WorldEvents.POINTED_DRIPSTONE_DRIPS_WATER_INTO_CAULDRON, blockPos, 0)
            }
            return ActionResult.SUCCESS
        } else return ActionResult.FAIL
    }

    private fun useOnFertilizable(pos: BlockPos, world: World, stack: ItemStack): Boolean {
        val blockState = world.getBlockState(pos)
        if (blockState.block is CropBlock && (blockState.block as CropBlock).isFertilizable(world, pos, blockState)) {
            if (world is ServerWorld){
                if ((blockState.block as CropBlock).canGrow(world, world.random, pos, blockState) && stack.get(ComponentHelper.MILLILITERS)!! >= 100) {
                    (blockState.block as CropBlock).grow(world, world.random, pos, blockState)
                    stack.set(ComponentHelper.MILLILITERS, stack.get(ComponentHelper.MILLILITERS)!! - RANDOM.nextInt(50, 100))
                } else if (stack.get(ComponentHelper.MILLILITERS)!! <= 100) {
                    stack.set(ComponentHelper.HAS_WATER, false)
                }
            } else if (world.isClient) {
                world.addParticle(ParticleTypes.RAIN, pos.x + 0.5, pos.y + 0.5, pos.z + 0.5, 0.2, 0.1, 0.3)
            }
            return true
        }
        return false
    }


    override fun appendTooltip(
        stack: ItemStack,
        context: TooltipContext,
        tooltip: MutableList<Text>,
        type: TooltipType
    ) {
        val waterCount = stack.get(ComponentHelper.MILLILITERS)

        if (waterCount == null) {
            tooltip.add(Text.literal("0/1000 Milliliters").formatted(Formatting.AQUA))
        } else {
            tooltip.add(Text.literal("$waterCount/1000 Milliliters").formatted(Formatting.AQUA))
        }
    }
}
