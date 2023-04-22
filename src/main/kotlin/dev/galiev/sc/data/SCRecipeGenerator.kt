package dev.galiev.sc.data

import dev.galiev.sc.items.IRegistry
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.data.server.recipe.RecipeProvider
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.item.Items
import net.minecraft.recipe.book.RecipeCategory
import net.minecraft.util.Identifier
import java.util.function.Consumer

class SCRecipeGenerator(output: FabricDataOutput?) : FabricRecipeProvider(output) {
    override fun generate(exporter: Consumer<RecipeJsonProvider>?) {

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, IRegistry.RAKE)
            .pattern("NNN")
            .pattern("ISI")
            .pattern("ASA")
            .input('N', Items.IRON_NUGGET)
            .input('S', Items.STICK)
            .input('I', Items.IRON_INGOT)
            .input('A', Items.AIR)
            .criterion(
                hasItem(Items.IRON_NUGGET),
                conditionsFromItem(Items.IRON_NUGGET)
            )
            .criterion(
                hasItem(Items.STICK),
                conditionsFromItem(Items.STICK)
            )
            .criterion(
                hasItem(Items.IRON_INGOT),
                conditionsFromItem(Items.IRON_INGOT)
            )
            .criterion(
                hasItem(Items.AIR),
                conditionsFromItem(Items.AIR)
            )
            .offerTo(exporter, Identifier(getRecipeName(IRegistry.RAKE)))

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, IRegistry.WATER_CAN)
            .pattern("AAA")
            .pattern("IBI")
            .pattern("III")
            .input('I', Items.IRON_INGOT)
            .input('A', Items.AIR)
            .input('B', Items.BUCKET)
            .criterion(
                hasItem(Items.IRON_INGOT),
                conditionsFromItem(Items.IRON_INGOT)
            )
            .criterion(
                hasItem(Items.AIR),
                conditionsFromItem(Items.AIR)
            )
            .criterion(
                hasItem(Items.BUCKET),
                RecipeProvider.conditionsFromItem(Items.BUCKET)
            )
            .offerTo(exporter, Identifier(getRecipeName(IRegistry.WATER_CAN)))
    }
}