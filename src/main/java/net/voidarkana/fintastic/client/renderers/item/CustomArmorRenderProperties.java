package net.voidarkana.fintastic.client.renderers.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.voidarkana.fintastic.client.FintasticLayers;
import net.voidarkana.fintastic.client.models.armor.HatModel;
import net.voidarkana.fintastic.common.item.YAFMItems;

public class CustomArmorRenderProperties implements IClientItemExtensions {

    private static boolean init;

    public static HatModel HAT_MODEL;

    public static void initializeModels() {
        init = true;
        HAT_MODEL = new HatModel(Minecraft.getInstance().getEntityModels().bakeLayer(FintasticLayers.HAT_LAYER));
    }

    @Override
    public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
        if(!init){
            initializeModels();
        }
        final var item = itemStack.getItem();


        if(item == YAFMItems.FISHING_HAT.get()){
            return HAT_MODEL;
        }

        return _default;
    }
}
