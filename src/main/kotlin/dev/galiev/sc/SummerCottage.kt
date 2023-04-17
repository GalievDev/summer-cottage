package dev.galiev.sc

import com.mojang.logging.LogUtils
import net.fabricmc.api.ModInitializer
import org.slf4j.Logger

object SummerCottage: ModInitializer {

    const val MOD_ID = "sc"
    val logger: Logger = LogUtils.getLogger()

    override fun onInitialize() {
        logger.info("${javaClass.simpleName} initialized with mod-id $MOD_ID")
    }
}