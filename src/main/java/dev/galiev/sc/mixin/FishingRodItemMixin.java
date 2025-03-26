package dev.galiev.sc.mixin;

import dev.galiev.sc.items.ItemsRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
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
            target = "Lnet/minecraft/entity/projectile/ProjectileEntity;spawn(Lnet/minecraft/entity/projectile/ProjectileEntity;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/entity/projectile/ProjectileEntity;",
            shift = At.Shift.BY
    ), cancellable = true)
    public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        var stack = user.getStackInHand(hand);
        if (isFullFisherman(user) && world instanceof ServerWorld serverWorld) {
            int luckOfSea = EnchantmentHelper.getFishingLuckBonus(serverWorld, stack, user) + 2;
            int lure = (int) EnchantmentHelper.getFishingTimeReduction(serverWorld, stack, user) + 2;
            world.spawnEntity(new FishingBobberEntity(user, world, luckOfSea, lure));
            cir.setReturnValue(ActionResult.SUCCESS);
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
