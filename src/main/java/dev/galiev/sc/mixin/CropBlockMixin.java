package dev.galiev.sc.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(CropBlock.class)
public interface CropBlockMixin{

    @Invoker("getAge")
    public int getAgeInvoke(BlockState state);
}
