package dev.galiev.sc.mixin;

import dev.galiev.sc.items.ItemsRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingRodItem.class)
public abstract class FishingRodItemMixin {

    @Inject(method = "use", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z",
            shift = At.Shift.BY
    ), cancellable = true)
    public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        var stack = user.getStackInHand(hand);
        if (isFullFisherman(user)) {
            int luckOfSea = EnchantmentHelper.getLuckOfTheSea(stack) + 2;
            int lure = EnchantmentHelper.getLure(stack) + 2;
            world.spawnEntity(new FishingBobberEntity(user, world, luckOfSea, lure));
            cir.setReturnValue(TypedActionResult.success(stack));
        }
    }

    @Unique
    private boolean isFullFisherman(PlayerEntity player) {
        return player.getEquippedStack(EquipmentSlot.HEAD).getItem() == ItemsRegistry.INSTANCE.getFISHERMAN_HAT() &&
                player.getEquippedStack(EquipmentSlot.CHEST).getItem() == ItemsRegistry.INSTANCE.getFISHERMAN_SHIRT() &&
                player.getEquippedStack(EquipmentSlot.LEGS).getItem() == ItemsRegistry.INSTANCE.getFISHERMAN_LEGGINGS() &&
                player.getEquippedStack(EquipmentSlot.FEET).getItem() == ItemsRegistry.INSTANCE.getRUBBER_BOOTS();
    }
}
