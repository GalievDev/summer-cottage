package dev.galiev.sc.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.galiev.sc.SummerCottage;
import dev.galiev.sc.items.ItemsRegistry;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Shadow
    @Final
    private ItemModels models;

    @Shadow
    public abstract ItemModels getModels();

    @ModifyVariable(
            method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At(value = "HEAD"),
            argsOnly = true
    )
    public BakedModel renderItem(BakedModel bakedModel, @Local(argsOnly = true) ItemStack stack, @Local(argsOnly = true) ModelTransformationMode renderMode) {
        if ((renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.FIXED)) {
            if (stack.getItem() == ItemsRegistry.INSTANCE.getDARTS()) {
                return getModels().getModelManager().getModel(new ModelIdentifier(SummerCottage.MOD_ID, "darts", "inventory"));
            }
            if (stack.getItem() == ItemsRegistry.INSTANCE.getWATER_CAN()) {
                return getModels().getModelManager().getModel(new ModelIdentifier(SummerCottage.MOD_ID, "water_can", "inventory"));
            }
            if (stack.getItem() == ItemsRegistry.INSTANCE.getRAKE()) {
                return getModels().getModelManager().getModel(new ModelIdentifier(SummerCottage.MOD_ID, "rake", "inventory"));
            }
        }

        return bakedModel;
    }

    @ModifyVariable(
            method = "getModel",
            at = @At(value = "STORE"),
            ordinal = 1
    )
    public BakedModel getHeldItemModelMixin(BakedModel bakedModel, @Local(argsOnly = true) ItemStack stack) {
        if (stack.getItem() == ItemsRegistry.INSTANCE.getDARTS()) {
            return this.models.getModelManager().getModel(new ModelIdentifier(SummerCottage.MOD_ID, "darts_3d", "inventory"));
        }
        if (stack.getItem() == ItemsRegistry.INSTANCE.getWATER_CAN()) {
            return this.models.getModelManager().getModel(new ModelIdentifier(SummerCottage.MOD_ID, "water_can_3d", "inventory"));
        }
        if (stack.getItem() == ItemsRegistry.INSTANCE.getRAKE()) {
            return this.models.getModelManager().getModel(new ModelIdentifier(SummerCottage.MOD_ID, "rake_3d", "inventory"));
        }

        return bakedModel;
    }
}
