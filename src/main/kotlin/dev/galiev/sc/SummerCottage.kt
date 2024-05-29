package dev.galiev.sc

import com.mojang.logging.LogUtils
import dev.galiev.sc.blocks.BlocksRegistry
import dev.galiev.sc.enity.EntitiesRegistry
import dev.galiev.sc.events.FishingEvent
import dev.galiev.sc.events.SeedHarvestEvent
import dev.galiev.sc.helper.BlocksHelper
import dev.galiev.sc.items.ItemsRegistry
import dev.syoritohatsuki.duckyupdater.DuckyUpdater
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents
import net.fabricmc.fabric.api.event.player.UseItemCallback
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import org.slf4j.Logger
import kotlin.random.Random

object SummerCottage: ModInitializer {

    const val MOD_ID = "sc"
    val logger: Logger = LogUtils.getLogger()

    val RANDOM = Random(System.currentTimeMillis())
    val SUMMER_COTTAGE: ItemGroup = FabricItemGroup.builder(Identifier(MOD_ID)).icon { ItemsRegistry.WATER_CAN.asItem()?.defaultStack }.build()

    override fun onInitialize() {
        logger.info("${javaClass.simpleName} initialized with mod-id $MOD_ID")
        ItemsRegistry
        BlocksRegistry
        EntitiesRegistry
        BlocksHelper
        PlayerBlockBreakEvents.AFTER.register(SeedHarvestEvent)
        UseItemCallback.EVENT.register(FishingEvent)
        DuckyUpdater.checkForUpdate("eJ2H87hd", MOD_ID)
    }
}