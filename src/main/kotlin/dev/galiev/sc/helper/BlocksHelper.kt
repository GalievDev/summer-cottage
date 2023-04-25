package dev.galiev.sc.helper

import net.minecraft.block.Block
import net.minecraft.block.CropBlock
import net.minecraft.registry.Registries
import java.util.stream.Collectors

object BlocksHelper {
    var CROPS: MutableList<Block> = ArrayList()
    init {
        CROPS = Registries.BLOCK.stream()
            .filter { block -> block is CropBlock }
            .collect(Collectors.toList())
    }
}