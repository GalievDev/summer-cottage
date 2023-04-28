package dev.galiev.sc

import com.mojang.logging.LogUtils
import dev.galiev.sc.blocks.BRegistry
import dev.galiev.sc.entity.ChairEntity
import dev.galiev.sc.events.SeedHarvestEvent
import dev.galiev.sc.helper.BlocksHelper
import dev.galiev.sc.items.IRegistry
import dev.syoritohatsuki.duckyupdater.DuckyUpdater
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import org.slf4j.Logger

object SummerCottage: ModInitializer {

    const val MOD_ID = "sc"
    val logger: Logger = LogUtils.getLogger()

    val SUMMER_COTTAGE: ItemGroup = FabricItemGroup.builder(Identifier(MOD_ID)).icon { IRegistry.WATER_CAN?.asItem()?.defaultStack }.build()

    override fun onInitialize() {
        logger.info("${javaClass.simpleName} initialized with mod-id $MOD_ID")
        DuckyUpdater.checkForUpdate("eJ2H87hd", MOD_ID)
        IRegistry.registerModItems()
        PlayerBlockBreakEvents.AFTER.register(SeedHarvestEvent)
        BRegistry
        BlocksHelper
        Registry.register(Registries.ENTITY_TYPE, Identifier(MOD_ID, "chair_entity_type"), ChairEntity.CHAIR_ENTITY_TYPE)
    }

    fun addAllToGroup(item: Item) {
        ItemGroupEvents.modifyEntriesEvent(SUMMER_COTTAGE).register(ItemGroupEvents.ModifyEntries { entries ->
            entries.add(
                item
            )
        })
    }
}