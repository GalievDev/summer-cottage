package dev.galiev.sc.blocks

import dev.galiev.sc.SummerCottage
import dev.galiev.sc.blocks.custom.FoldingChair
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier


object BRegistry {

    val FOLDING_CHAIR = registerBlock(
        "folding_chair",
        FoldingChair(
            FabricBlockSettings.of(Material.WOOD).nonOpaque()
        ), SummerCottage.SUMMER_COTTAGE
    )

    private fun registerBlock(name: String, block: Block, group: ItemGroup): Block? {
        registerBlockItem(name, block, group)
        return Registry.register(Registries.BLOCK, Identifier(SummerCottage.MOD_ID, name), block)
    }

    private fun registerBlockItem(name: String, block: Block, group: ItemGroup): Item {
        val item: Item = Registry.register(
            Registries.ITEM, Identifier(SummerCottage.MOD_ID, name),
            BlockItem(block, FabricItemSettings())
        )
        ItemGroupEvents.modifyEntriesEvent(group).register(ItemGroupEvents.ModifyEntries { entries ->
            entries.add(item)
        })
        return item
    }
}