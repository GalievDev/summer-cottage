package dev.galiev.sc.helper

import com.mojang.serialization.Codec
import dev.galiev.sc.SummerCottage
import net.minecraft.component.ComponentType
import net.minecraft.network.codec.PacketCodecs
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import java.util.function.UnaryOperator

object ComponentHelper {
    val MILLILITERS: ComponentType<Int> = register("milliliters") { builder ->
        builder.codec(Codec.INT).packetCodec(PacketCodecs.INTEGER)
    }

    val HAS_WATER: ComponentType<Boolean> = register("water") { builder ->
        builder.codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL)
    }

    private fun <T> register(name: String, builderOperator: UnaryOperator<ComponentType.Builder<T>>): ComponentType<T> {
        return Registry.register<ComponentType<*>, ComponentType<T>>(
            Registries.DATA_COMPONENT_TYPE, Identifier.of(SummerCottage.MOD_ID, name),
            builderOperator.apply(ComponentType.builder()).build()
        )
    }
}