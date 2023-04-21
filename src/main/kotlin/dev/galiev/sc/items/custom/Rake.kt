package dev.galiev.sc.items.custom

import dev.galiev.sc.mixin.CropBlockMixin
import net.minecraft.block.Blocks
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.HoeItem
import net.minecraft.item.ItemUsageContext
import net.minecraft.item.ToolMaterial
import net.minecraft.registry.tag.BlockTags
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult

class Rake(material: ToolMaterial?, attackDamage: Int, attackSpeed: Float, settings: Settings?) : HoeItem(material, attackDamage, attackSpeed, settings) {
    override fun useOnBlock(context: ItemUsageContext?): ActionResult {
        val world = context?.world
        val pos = context?.blockPos
        val blockState = world?.getBlockState(pos)

        if (blockState?.isIn(BlockTags.DIRT) == true) {
            val player = context.player
            world.setBlockState(pos, Blocks.FARMLAND.defaultState)

            world.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F)

            if (!world.isClient && player != null){
                context.stack.damage(1, player) { p: PlayerEntity -> p.sendToolBreakStatus(context.hand) }
            }
            ActionResult.success(world.isClient)

        } else if (blockState?.isIn(BlockTags.CROPS) == true) {
            if ((blockState.block as CropBlockMixin).getAgeInvoke(blockState) == 7){
                world.breakBlock(pos, true)

                ActionResult.SUCCESS
            } else {
                ActionResult.PASS
            }
        }

        return ActionResult.PASS
    }
}