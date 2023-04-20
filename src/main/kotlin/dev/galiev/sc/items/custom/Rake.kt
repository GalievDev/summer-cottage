package dev.galiev.sc.items.custom

import net.minecraft.block.Blocks
import net.minecraft.item.HoeItem
import net.minecraft.item.ItemUsageContext
import net.minecraft.item.ToolMaterial
import net.minecraft.registry.tag.BlockTags
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult

class Rake(material: ToolMaterial?, attackDamage: Int, attackSpeed: Float, settings: Settings?) : HoeItem(material, attackDamage, attackSpeed, settings) {
    override fun useOnBlock(context: ItemUsageContext?): ActionResult {
        val world = context?.world
        val pos = context?.blockPos
        if (world?.getBlockState(pos)?.isIn(BlockTags.DIRT) == true) {
            world.setBlockState(pos, Blocks.FARMLAND.defaultState)
        } else {
            context?.player?.playSound(SoundEvents.BLOCK_LAVA_POP, 1.0F, 1.0F)
        }

        return ActionResult.PASS
    }
}