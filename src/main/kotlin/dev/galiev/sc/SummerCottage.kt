package dev.galiev.sc

import com.mojang.logging.LogUtils
import dev.galiev.sc.blocks.BlocksRegistry
import dev.galiev.sc.events.SeedHarvestEvent
import dev.galiev.sc.helper.BlocksHelper
import dev.galiev.sc.helper.EntityTypeRegistry
import dev.galiev.sc.items.ItemsRegistry
import dev.syoritohatsuki.duckyupdater.DuckyUpdater
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import org.slf4j.Logger

object SummerCottage: ModInitializer {

    const val MOD_ID = "sc"
    val logger: Logger = LogUtils.getLogger()

    val SUMMER_COTTAGE: ItemGroup = FabricItemGroup.builder(Identifier(MOD_ID)).icon { ItemsRegistry.WATER_CAN?.asItem()?.defaultStack }.build()

    override fun onInitialize() {
        logger.info("${javaClass.simpleName} initialized with mod-id $MOD_ID")
        ItemsRegistry.registerModItems()
        BlocksRegistry
        EntityTypeRegistry
        BlocksHelper
        PlayerBlockBreakEvents.AFTER.register(SeedHarvestEvent)
        DuckyUpdater.checkForUpdate("eJ2H87hd", MOD_ID)
    }

    fun addAllToGroup(item: Item) {
        ItemGroupEvents.modifyEntriesEvent(SUMMER_COTTAGE).register(ItemGroupEvents.ModifyEntries { entries ->
            entries.add(
                item
            )
        })
    }
}