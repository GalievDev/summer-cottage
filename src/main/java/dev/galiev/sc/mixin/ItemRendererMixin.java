package dev.galiev.sc.mixin;

import dev.galiev.sc.SummerCottage;
import dev.galiev.sc.items.ItemsRegistry;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @ModifyVariable(method = "renderItem", at = @At(value = "HEAD"), argsOnly = true)
    public BakedModel useRubyStaffModel(BakedModel value, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (stack.isOf(ItemsRegistry.INSTANCE.getDARTS()) && renderMode != ModelTransformationMode.GUI) {
            return ((ItemRendererAccessor) this).sc$getModels().getModelManager().getModel(new ModelIdentifier(SummerCottage.MOD_ID, "darts_3d", "inventory"));
        }
        return value;
    }
}
