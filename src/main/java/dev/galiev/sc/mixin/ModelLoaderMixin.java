package dev.galiev.sc.mixin;

import dev.galiev.sc.SummerCottage;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {

    @Shadow
    protected abstract void loadItemModel(ModelIdentifier id);

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/ModelLoader;loadItemModel(Lnet/minecraft/client/util/ModelIdentifier;)V", ordinal = 1))
    public void add3DItems(CallbackInfo ci) {
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(SummerCottage.MOD_ID, "darts_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(SummerCottage.MOD_ID, "water_can_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(SummerCottage.MOD_ID, "rake_3d")));
    }
}
