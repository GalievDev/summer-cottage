package dev.galiev.sc.mixin;

import dev.galiev.sc.helper.SolsticeDay;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.PlantBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.time.LocalDate;

@Mixin(CropBlock.class)
public abstract class CropBlockMixin extends PlantBlock implements Fertilizable {

    @Shadow public abstract void grow(ServerWorld world, Random random, BlockPos pos, BlockState state);

    @Shadow
    public abstract int getAge(BlockState state);

    public CropBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At("HEAD"))
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if(this.getAge(state) != CropBlock.MAX_AGE) {
            var dates = SolsticeDay.SOLSTICE.getDays();
            var from = dates.getFirst();
            var to = dates.getSecond();
            if (!LocalDate.now().isBefore(from) && !LocalDate.now().isAfter(to)) {
                this.grow(world, random, pos, state);
            }
        }
    }
}
