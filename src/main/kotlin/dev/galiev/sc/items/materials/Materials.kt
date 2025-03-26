package dev.galiev.sc.items.materials

import dev.galiev.sc.SummerCottage.MOD_ID
import net.minecraft.item.equipment.ArmorMaterial
import net.minecraft.item.equipment.EquipmentAsset
import net.minecraft.item.equipment.EquipmentAssetKeys
import net.minecraft.item.equipment.EquipmentType
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.tag.ItemTags
import net.minecraft.sound.SoundEvents
import net.minecraft.util.Identifier


object Materials {
    private val GARDENER_CLOTH_ARMOR_KEY: RegistryKey<EquipmentAsset> = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(MOD_ID, "gardner_cloth_material"))
    private val FISHERMAN_CLOTH_ARMOR_KEY: RegistryKey<EquipmentAsset> = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(MOD_ID, "fisherman_cloth_material"))

    val GARDENER_CLOTH_ARMOR_MATERIAL: ArmorMaterial = ArmorMaterial(
        15,
        mapOf(
            EquipmentType.HELMET to 2,
            EquipmentType.CHESTPLATE to 3,
            EquipmentType.LEGGINGS to 2,
            EquipmentType.BOOTS to 1,
        ),
        5,
        SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
        0F,
        0F,
        ItemTags.WOOL,
        GARDENER_CLOTH_ARMOR_KEY
    )

    val FISHERMAN_CLOTH_ARMOR_MATERIAL: ArmorMaterial = ArmorMaterial(
        20,
        mapOf(
            EquipmentType.HELMET to 2,
            EquipmentType.CHESTPLATE to 3,
            EquipmentType.LEGGINGS to 2,
            EquipmentType.BOOTS to 1,
        ),
        5,
        SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
        0F,
        0F,
        ItemTags.WOOL,
        FISHERMAN_CLOTH_ARMOR_KEY
    )
}