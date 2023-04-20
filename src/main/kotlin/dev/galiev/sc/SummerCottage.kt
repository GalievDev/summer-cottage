package dev.galiev.sc

import com.mojang.logging.LogUtils
import dev.galiev.sc.items.IRegistry
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import org.slf4j.Logger

object SummerCottage: ModInitializer {

    const val MOD_ID = "sc"
    val logger: Logger = LogUtils.getLogger()

    val SUMMER_COTTAGE: ItemGroup = FabricItemGroup.builder(Identifier(MOD_ID)).icon { IRegistry.WATER_CAN?.asItem()?.defaultStack }.build()

    override fun onInitialize() {
        logger.info("${javaClass.simpleName} initialized with mod-id $MOD_ID")
        IRegistry.registerModItems()
    }
}