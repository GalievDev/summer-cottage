package dev.galiev.sc

import com.mojang.logging.LogUtils
import dev.galiev.sc.blocks.BlocksRegistry
import dev.galiev.sc.enity.EntitiesRegistry
import dev.galiev.sc.events.SeedHarvestEvent
import dev.galiev.sc.helper.SolsticeDay
import dev.galiev.sc.items.ItemsRegistry
import dev.syoritohatsuki.duckyupdater.DuckyUpdater
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.networking.v1.PacketSender
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayNetworkHandler
import net.minecraft.item.ItemGroup
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import org.slf4j.Logger
import java.time.LocalDate
import kotlin.random.Random

object SummerCottage: ModInitializer {

    const val MOD_ID = "sc"
    val logger: Logger = LogUtils.getLogger()

    val RANDOM = Random(System.currentTimeMillis())
    val SUMMER_COTTAGE: ItemGroup = FabricItemGroup.builder(Identifier(MOD_ID)).icon { ItemsRegistry.WATER_CAN.asItem()?.defaultStack }.build()

    override fun onInitialize() {
        ItemsRegistry
        BlocksRegistry
        EntitiesRegistry
        PlayerBlockBreakEvents.AFTER.register(SeedHarvestEvent)

        ClientPlayConnectionEvents.JOIN.register(ClientPlayConnectionEvents.Join { _: ClientPlayNetworkHandler?, _: PacketSender?, client: MinecraftClient? ->
            val (from, to) = SolsticeDay.SOLSTICE.getDays()
            if (LocalDate.now() in from..to) {
                client?.player?.sendMessage(Text.of("Solstice day! Today all crops will grow up faster."))
            }
        })

        DuckyUpdater.checkForUpdate("eJ2H87hd", MOD_ID)
    }
}