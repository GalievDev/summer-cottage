package dev.galiev.sc.events

import dev.galiev.sc.helper.BlocksHelper
import net.fabricmc.fabric.api.loot.v2.LootTableEvents
import net.fabricmc.fabric.api.loot.v2.LootTableSource
import net.minecraft.loot.LootManager
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.resource.ResourceManager
import net.minecraft.util.Identifier

object CropBreak: LootTableEvents.Modify {
    override fun modifyLootTable(resourceManager: ResourceManager?, lootManager: LootManager?, id: Identifier?, tableBuilder: LootTable.Builder?, source: LootTableSource?) {
/*        if (player.getEquippedStack(EquipmentSlot.HEAD) == IRegistry.GARDENER_HAT?.defaultStack &&
            player.getEquippedStack(EquipmentSlot.CHEST) == IRegistry.GARDENER_SHIRT?.defaultStack &&
            player.getEquippedStack(EquipmentSlot.LEGS) == IRegistry.GARDENER_LEGGINGS?.defaultStack
        ) {
        }*/
        BlocksHelper.CROPS.forEach{ block ->
            if (source?.isBuiltin!! && block.lootTableId.equals(id)){
                val pool: LootPool.Builder = LootPool.builder()
                    .with(ItemEntry.builder(block.asItem()))
            }
        }
    }
}