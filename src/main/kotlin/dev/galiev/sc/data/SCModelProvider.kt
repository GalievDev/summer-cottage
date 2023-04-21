package dev.galiev.sc.data

import dev.galiev.sc.items.IRegistry
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.client.BlockStateModelGenerator
import net.minecraft.data.client.ItemModelGenerator
import net.minecraft.data.client.Models

class SCModelProvider(output: FabricDataOutput?) : FabricModelProvider(output) {
    override fun generateBlockStateModels(blockStateModelGenerator: BlockStateModelGenerator?) {

    }

    override fun generateItemModels(itemModelGenerator: ItemModelGenerator?) {
        itemModelGenerator?.register(IRegistry.RAKE, Models.GENERATED)
        itemModelGenerator?.register(IRegistry.WATER_CAN, Models.GENERATED)
    }
}