package dev.galiev.sc.data

import dev.galiev.sc.blocks.BlocksRegistry
import dev.galiev.sc.items.ItemsRegistry
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.client.BlockStateModelGenerator
import net.minecraft.data.client.ItemModelGenerator
import net.minecraft.data.client.Models

class SCModelProvider(output: FabricDataOutput?) : FabricModelProvider(output) {
    override fun generateBlockStateModels(blockStateModelGenerator: BlockStateModelGenerator?) {
        blockStateModelGenerator?.registerCubeAllModelTexturePool(BlocksRegistry.FOLDING_CHAIR)
    }

    override fun generateItemModels(itemModelGenerator: ItemModelGenerator?) {
        itemModelGenerator?.register(ItemsRegistry.GARDENER_HAT, Models.GENERATED)
        itemModelGenerator?.register(ItemsRegistry.GARDENER_SHIRT, Models.GENERATED)
        itemModelGenerator?.register(ItemsRegistry.GARDENER_LEGGINGS, Models.GENERATED)
    }
}