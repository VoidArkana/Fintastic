package net.voidarkana.fintastic.common.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.entity.YAFMEntities;
import net.voidarkana.fintastic.common.entity.custom.GuppyEntity;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class FishBucketItem extends MobBucketItem {

    public FishBucketItem(Supplier<? extends EntityType<?>> entityType, Supplier<? extends Fluid> fluid, Item item, boolean hasTooltip, Item.Properties builder) {
        super(entityType, fluid, () -> {
            return SoundEvents.BUCKET_EMPTY_FISH;
        }, builder);
        DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> {
            return () -> {
                return Fintastic.CALLBACKS.add(() -> {
                    ItemProperties.register(this, new ResourceLocation(Fintastic.MOD_ID, "variant"), (stack, world, player, i) -> {
                        return stack.hasTag() ? (float)stack.getTag().getInt("Variant") : 0.0F;
                    });
                });
            };
        });
    }

    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        ChatFormatting[] achatformatting = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
        ChatFormatting[] bchatformatting = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.AQUA};


        MutableComponent translatable = Component.translatable("fintastic.translatable.shift");
        translatable.withStyle(bchatformatting);

        if (!Screen.hasShiftDown()){
            pTooltipComponents.add(translatable);
        }

        if (getFishType() == YAFMEntities.FEATHERBACK.get()) {
            CompoundTag compoundtag = pStack.getTag();
            if (compoundtag != null && compoundtag.contains("Variant", 3)) {
                int i = compoundtag.getInt("Variant");


                String featherback_sci = "yafm.featherback_sci." + i;
                String common = "yafm.featherback_common." + i;


                MutableComponent mutablecomponent = Component.translatable(featherback_sci);
                mutablecomponent.withStyle(bchatformatting);

                if (Screen.hasShiftDown()){
                    pTooltipComponents.add(Component.translatable(common).withStyle(achatformatting));
                    pTooltipComponents.add(mutablecomponent);
                }
            }
        }

        if (getFishType() == YAFMEntities.GUPPY.get()) {
            CompoundTag compoundtag = pStack.getTag();
            if (compoundtag != null && compoundtag.contains("VariantSkin", 3)) {

                int skin = compoundtag.getInt("VariantSkin");
                int fin_model = compoundtag.getInt("FinModel");
                int fin_color = compoundtag.getInt("FinColor");
                int tail_model = compoundtag.getInt("TailModel");
                int tail_color = compoundtag.getInt("TailColor");
                int main_pattern = compoundtag.getInt("MainPattern");
                int main_pattern_color = compoundtag.getInt("MainPatternColor");
                int second_pattern = compoundtag.getInt("SecondaryPattern");
                int second_pattern_color = compoundtag.getInt("SecondaryPatternColor");

                Boolean has_main_pattern = compoundtag.getBoolean("HasMainPattern");
                Boolean has_second_pattern = compoundtag.getBoolean("HasSecondaryPattern");

                String base = "yafm.guppy_base." + skin;

                String fins = "yafm.guppy_fin." + GuppyEntity.getFinsName(fin_model);
                String finsColor = "yafm.guppy_color." + fin_color;

                String tail = "yafm.guppy_tail." + GuppyEntity.getTailName(tail_model);
                String tailColor = "yafm.guppy_color." + tail_color;

                String mainPattern = "yafm.guppy_pattern." + GuppyEntity.getMainPatternName(main_pattern);
                String mainPatternColor = "yafm.guppy_color." + main_pattern_color;

                String secondPattern = "yafm.guppy_pattern." + GuppyEntity.getSecondPatternName(second_pattern);
                String secondPatternColor = "yafm.guppy_color." + second_pattern_color;

                String sci = "yafm.guppy.sci";


                MutableComponent finInfo = Component.translatable("yafm.guppy_fin").append(":");
                finInfo.append(CommonComponents.SPACE).append(Component.translatable(fins)).append(",");
                finInfo.append(CommonComponents.SPACE).append(Component.translatable(finsColor));

                finInfo.withStyle(achatformatting);


                MutableComponent tailInfo = Component.translatable("yafm.guppy_tail").append(":");
                tailInfo.append(CommonComponents.SPACE).append(Component.translatable(tail)).append(",");
                tailInfo.append(CommonComponents.SPACE).append(Component.translatable(tailColor));

                tailInfo.withStyle(achatformatting);


                MutableComponent mainPatternInfo = Component.translatable("yafm.guppy_pattern_1").append(":");
                mainPatternInfo.append(CommonComponents.SPACE).append(Component.translatable(mainPattern)).append(",");
                mainPatternInfo.append(CommonComponents.SPACE).append(Component.translatable(mainPatternColor));

                mainPatternInfo.withStyle(achatformatting);



                MutableComponent secondPatternInfo = Component.translatable("yafm.guppy_pattern_2").append(":");
                secondPatternInfo.append(CommonComponents.SPACE).append(Component.translatable(secondPattern)).append(",");
                secondPatternInfo.append(CommonComponents.SPACE).append(Component.translatable(secondPatternColor));

                secondPatternInfo.withStyle(achatformatting);



                MutableComponent scientific_name = Component.translatable(sci);

                scientific_name.withStyle(bchatformatting);

                if (Screen.hasShiftDown()){
                    pTooltipComponents.add(Component.translatable(base).withStyle(achatformatting));
                    pTooltipComponents.add(finInfo);
                    pTooltipComponents.add(tailInfo);
                    if (has_main_pattern){
                        pTooltipComponents.add(mainPatternInfo);
                    }
                    if (has_second_pattern){
                        pTooltipComponents.add(secondPatternInfo);
                    }
                    pTooltipComponents.add(scientific_name);
                }
            }
        }

        if (getFishType() == YAFMEntities.CATFISH.get()) {
            CompoundTag compoundtag = pStack.getTag();
            if (compoundtag != null && compoundtag.contains("Variant", 3)) {
                int i = compoundtag.getInt("Variant");


                String featherback_sci = "yafm.catfish_sci." + i;
                String common = "yafm.catfish_common." + i;


                MutableComponent mutablecomponent = Component.translatable(featherback_sci);
                mutablecomponent.withStyle(bchatformatting);

                if (Screen.hasShiftDown()){
                    pTooltipComponents.add(Component.translatable(common).withStyle(achatformatting));
                    pTooltipComponents.add(mutablecomponent);
                }
            }
        }

        if (getFishType() == YAFMEntities.FRESHWATER_SHARK.get()) {
            CompoundTag compoundtag = pStack.getTag();
            if (compoundtag != null && compoundtag.contains("VariantModel", 3)) {
                int i = compoundtag.getInt("VariantModel");

                String featherback_sci = "yafm.freshwater_shark_sci." + i;
                String common = "yafm.freshwater_shark_common." + i;

                MutableComponent commonName = Component.translatable(common);

                if (i==1){
                    int j = compoundtag.getInt("VariantSkin");
                    String skinVariant = "yafm.highfin_skin." + j;
                    commonName.append(Component.translatable(skinVariant));
                }


                MutableComponent mutablecomponent = Component.translatable(featherback_sci);
                mutablecomponent.withStyle(bchatformatting);

                if (Screen.hasShiftDown()){
                    pTooltipComponents.add(commonName.withStyle(achatformatting));

                    pTooltipComponents.add(mutablecomponent);
                }
            }
        }

        if (getFishType() == YAFMEntities.MINNOW.get()) {
            CompoundTag compoundtag = pStack.getTag();
            if (compoundtag != null && compoundtag.contains("VariantModel", 3)) {
                int i = compoundtag.getInt("VariantModel");
                int j = compoundtag.getInt("VariantSkin");

                String featherback_sci = "yafm.minnow_sci." + i + "." + j;
                String common = "yafm.minnow_common." + i + "." + j;


                MutableComponent mutablecomponent = Component.translatable(featherback_sci);
                mutablecomponent.withStyle(bchatformatting);

                if (Screen.hasShiftDown()){
                    pTooltipComponents.add(Component.translatable(common).withStyle(achatformatting));

                    pTooltipComponents.add(mutablecomponent);
                }
            }
        }

        if (getFishType() == YAFMEntities.PLECO.get()) {
//            CompoundTag compoundtag = pStack.getTag();
//            if (compoundtag != null && compoundtag.contains("Variant", 3)) {
//                int i = compoundtag.getInt("Variant");

                String featherback_sci = "yafm.pleco_sci";
                String common = "yafm.pleco_common";


                MutableComponent mutablecomponent = Component.translatable(featherback_sci);
                mutablecomponent.withStyle(bchatformatting);

                if (Screen.hasShiftDown()){
                    pTooltipComponents.add(Component.translatable(common).withStyle(achatformatting));

                    pTooltipComponents.add(mutablecomponent);
                }
            //}
        }

        if (getFishType() == YAFMEntities.ARTEMIA.get()) {
            CompoundTag compoundtag = pStack.getTag();
            if (compoundtag != null && compoundtag.contains("VariantSkin", 3)) {
                int j = compoundtag.getInt("VariantSkin");

                String featherback_sci = "yafm.artemia_sci." + j;
                String common = "yafm.artemia_common." + j;


                MutableComponent mutablecomponent = Component.translatable(featherback_sci);
                mutablecomponent.withStyle(bchatformatting);

                if (Screen.hasShiftDown()){
                    pTooltipComponents.add(Component.translatable(common).withStyle(achatformatting));

                    pTooltipComponents.add(mutablecomponent);
                }
            }
        }

        if (getFishType() == YAFMEntities.DAPHNIA.get()) {
            String featherback_sci = "yafm.daphnia_sci";
            String common = "yafm.daphnia_common";


            MutableComponent mutablecomponent = Component.translatable(featherback_sci);
            mutablecomponent.withStyle(bchatformatting);

            if (Screen.hasShiftDown()){
                pTooltipComponents.add(Component.translatable(common).withStyle(achatformatting));

                pTooltipComponents.add(mutablecomponent);
            }
        }

    }
}
