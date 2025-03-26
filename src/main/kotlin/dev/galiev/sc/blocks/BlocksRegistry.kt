package dev.galiev.sc.blocks

import dev.galiev.sc.SummerCottage.MOD_ID
import dev.galiev.sc.SummerCottage.SUMMER_COTTAGE_KEY
import dev.galiev.sc.blocks.custom.Couple
import dev.galiev.sc.blocks.custom.DartsDesk
import dev.galiev.sc.blocks.custom.FoldingChair
import dev.galiev.sc.blocks.custom.Kettle
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.Items
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier


object BlocksRegistry {
    val FOLDING_CHAIR = register("folding_chair", ::FoldingChair) {
        strength(1.5f, 3.5f)
        nonOpaque()
    }

    val KETTLE = register("kettle", ::Kettle) {
        strength(1.8f)
        nonOpaque()
    }

    val COUPLE = register("couple", ::Couple) {
        strength(1.8f)
        nonOpaque()
    }

    val DARTS_DESK = register("darts_desk", ::DartsDesk) {
        strength(1.0f)
        nonOpaque()
    }

    private inline fun <reified T : Block> register(
        name: String, noinline factory: (AbstractBlock.Settings) -> T, settingsBuilder: AbstractBlock.Settings.() -> Unit = {}
    ): T {
        val settings = AbstractBlock.Settings.create().apply(settingsBuilder)
        val block = Blocks.register(
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(MOD_ID, name)), { factory(settings) }, settings
        ) as T
        Items.register(block)

        ItemGroupEvents.modifyEntriesEvent(SUMMER_COTTAGE_KEY).register{
            it.add(block)
        }

        return block
    }
}