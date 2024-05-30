package dev.galiev.sc.mixin;

import com.mojang.authlib.GameProfile;
import dev.galiev.sc.items.ItemsRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayerEntity.class)
public class AbstractClientPlayerEntityMixin extends PlayerEntity {

    public AbstractClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(method = "getFovMultiplier", at = @At("RETURN"), cancellable = true)
    public void getFovMultiplier(CallbackInfoReturnable<Float> cir) {
        float f = 1.0f;
        var stack = this.getMainHandStack();
        if (this.isUsingItem()) {
            if (stack.isOf(ItemsRegistry.INSTANCE.getDARTS())) {
                int i = this.getItemUseTime();
                float g = (float)i / 20.0f;
                g = g > 1.0f ? 1.0f : g * g;
                f *= 1.0f - g * 0.3f;
                cir.setReturnValue(MathHelper.lerp(MinecraftClient.getInstance().options.getFovEffectScale().getValue().floatValue(), 1.0f, f));
            }
        }
    }

    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public boolean isCreative() {
        return false;
    }
}
