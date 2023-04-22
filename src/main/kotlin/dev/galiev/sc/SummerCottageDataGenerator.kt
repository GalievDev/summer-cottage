package dev.galiev.sc

import dev.galiev.sc.data.SCModelProvider
import dev.galiev.sc.data.SCRecipeGenerator
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

class SummerCottageDataGenerator: DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator?) {
        val pack: FabricDataGenerator.Pack? = fabricDataGenerator?.createPack()

        pack?.addProvider(::SCModelProvider)
        pack?.addProvider(::SCRecipeGenerator)
    }
}