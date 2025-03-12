package dev.galiev.sc.items.materials

import net.minecraft.item.ArmorItem
import net.minecraft.item.ArmorMaterial
import net.minecraft.item.Items
import net.minecraft.recipe.Ingredient
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents

class FishermanClothMaterial: ArmorMaterial {
    private val DURABILITY = intArrayOf(4, 5, 3, 2)
    private val PROTECTION = intArrayOf(2, 3, 2, 1)
    override fun getDurability(type: ArmorItem.Type?): Int {
        return DURABILITY[type?.equipmentSlot?.entitySlotId!!]
    }

    override fun getProtection(type: ArmorItem.Type?): Int {
        return PROTECTION[type?.equipmentSlot?.entitySlotId!!]
    }

    override fun getEnchantability(): Int {
        return 1
    }

    override fun getEquipSound(): SoundEvent {
        return SoundEvents.ITEM_ARMOR_EQUIP_LEATHER
    }

    override fun getRepairIngredient(): Ingredient {
        return Ingredient.ofItems(Items.LEATHER)
    }

    override fun getName(): String {
        return "fisherman"
    }

    override fun getToughness(): Float {
        return 0F
    }

    override fun getKnockbackResistance(): Float {
        return 0F
    }
}