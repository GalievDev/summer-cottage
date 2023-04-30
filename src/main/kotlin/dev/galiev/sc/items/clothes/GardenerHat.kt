package dev.galiev.sc.items.clothes

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.ArmorItem
import net.minecraft.item.ArmorMaterials
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.world.World

class GardenerHat() : ArmorItem(ArmorMaterials.LEATHER, ArmorItem.Type.HELMET, FabricItemSettings()) {
    override fun appendTooltip(
        stack: ItemStack?,
        world: World?,
        tooltip: MutableList<Text>?,
        context: TooltipContext?
    ) {
        if (Screen.hasShiftDown()){
            tooltip?.add(Text.translatable("text.gardener_set").formatted(Formatting.GREEN))
        } else {
            tooltip?.add(Text.literal("Press Shift for more info").formatted(Formatting.YELLOW))
        }

        super.appendTooltip(stack, world, tooltip, context)
    }
}