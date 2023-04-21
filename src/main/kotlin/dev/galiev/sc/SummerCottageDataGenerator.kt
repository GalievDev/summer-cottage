package dev.galiev.sc

import dev.galiev.sc.data.SCModelProvider
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

class SummerCottageDataGenerator: DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator?) {
        val pack: FabricDataGenerator.Pack? = fabricDataGenerator?.createPack()

        pack?.addProvider(::SCModelProvider)
    }
}