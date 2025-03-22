package dev.galiev.sc

import com.mojang.logging.LogUtils
import dev.galiev.sc.blocks.BlocksRegistry
import dev.galiev.sc.enity.EntitiesRegistry
import dev.galiev.sc.events.SeedHarvestEvent
import dev.galiev.sc.helper.SolsticeDay
import dev.galiev.sc.items.ItemsRegistry
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.item.ItemGroup
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import org.slf4j.Logger
import java.time.LocalDate
import java.util.*
import kotlin.random.Random

object SummerCottage: ModInitializer {

    const val MOD_ID = "sc"
    val logger: Logger = LogUtils.getLogger()

    val RANDOM = Random(System.currentTimeMillis())
    val SUMMER_COTTAGE_KEY: RegistryKey<ItemGroup> = RegistryKey.of(
        RegistryKeys.ITEM_GROUP, Identifier.of(MOD_ID, "summer_cottage_item_group")
    )

    override fun onInitialize() {
        ItemsRegistry
        BlocksRegistry
        EntitiesRegistry
        PlayerBlockBreakEvents.AFTER.register(SeedHarvestEvent)
        Registry.register(
            Registries.ITEM_GROUP,
            SUMMER_COTTAGE_KEY,
            FabricItemGroup.builder().icon {
                ItemsRegistry.WATER_CAN.asItem()?.defaultStack
            }.displayName(Text.translatable("itemGroup.sc")).build()
        )

        val notifiedPlayers = mutableSetOf<UUID>()
        ServerTickEvents.START_WORLD_TICK.register(ServerTickEvents.StartWorldTick { world: ServerWorld ->
            val (from, to) = SolsticeDay.SOLSTICE.getDays()
            if (LocalDate.now() in from..to) {
                world.players.forEach {
                    if (notifiedPlayers.add(it.uuid)) {
                        it.sendMessage(Text.of("Solstice day! Today all crops will grow up faster."))
                    }
                }
            } else {
                notifiedPlayers.clear()
            }
        })
    }
}