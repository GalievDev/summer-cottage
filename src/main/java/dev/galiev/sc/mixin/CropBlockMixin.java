package dev.galiev.sc.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.PlantBlock;
import net.minecraft.state.property.IntProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(CropBlock.class)
public abstract class CropBlockMixin extends PlantBlock implements Fertilizable {

    public CropBlockMixin(Settings settings) {
        super(settings);
    }
}
