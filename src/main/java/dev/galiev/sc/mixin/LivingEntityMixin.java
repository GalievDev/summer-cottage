package dev.galiev.sc.mixin;

import dev.galiev.sc.items.clothes.gardener.GardenerHat;
import dev.galiev.sc.items.clothes.gardener.GardenerShirt;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "getPreferredEquipmentSlot", at = @At("HEAD"), cancellable = true)
    private static void getPreferredEquipmentSlot(ItemStack stack, CallbackInfoReturnable<EquipmentSlot> cir) {
        if(stack.getItem() instanceof GardenerHat) cir.setReturnValue(EquipmentSlot.HEAD);
        else if (stack.getItem() instanceof GardenerShirt) cir.setReturnValue(EquipmentSlot.CHEST);
    }
}
