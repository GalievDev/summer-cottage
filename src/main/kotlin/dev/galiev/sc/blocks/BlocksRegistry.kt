package dev.galiev.sc.blocks

import dev.galiev.sc.SummerCottage
import dev.galiev.sc.blocks.custom.Couple
import dev.galiev.sc.blocks.custom.FoldingChair
import dev.galiev.sc.blocks.custom.Kettle
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier


object BlocksRegistry {
    private val BLOCKS: MutableMap<Block, Identifier> = LinkedHashMap()

    val FOLDING_CHAIR = FoldingChair().create("folding_chair")
    val KETTLE = Kettle().create("kettle")
    val COUPLE = Couple().create("couple")

    init {
        BLOCKS.keys.forEach {
            val blockItem = BlockItem(it, FabricItemSettings())
            Registry.register(Registries.BLOCK, BLOCKS[it], it)
            Registry.register(Registries.ITEM, BLOCKS[it], blockItem)

            SummerCottage.addAllToGroup(blockItem)
        }
    }

    private fun Block.create(name: String): Block {
        BLOCKS[this] = Identifier(SummerCottage.MOD_ID, name)
        return this
    }
}