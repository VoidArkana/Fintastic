package net.voidarkana.fintastic.common.item.custom;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.entity.FintyEntities;
import net.voidarkana.fintastic.common.entity.custom.*;
import net.voidarkana.fintastic.common.item.FintyItems;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class FishBucketItem extends MobBucketItem {

    private final Supplier<? extends EntityType<?>> entityType;

    public FishBucketItem(Supplier<? extends EntityType<?>> entityType, Supplier<? extends Fluid> fluid, Item item, boolean hasTooltip, Item.Properties builder) {
        super(entityType.get(), fluid.get(), SoundEvents.BUCKET_EMPTY_FISH, builder);
        this.entityType = entityType;
        if (FMLEnvironment.dist == Dist.CLIENT) {
            Fintastic.CALLBACKS.add(() -> ItemProperties.register(this, Fintastic.location("variant"), (stack, world, player, i) -> {
                CustomData data = stack.get(DataComponents.BUCKET_ENTITY_DATA);
                return data != null ? (float) data.copyTag().getInt("Variant") : 0.0F;
            }));
        }
    }

    @Override
    public void checkExtraContent(@Nullable Player player, Level level, ItemStack containerStack, BlockPos pos) {
        if (level instanceof ServerLevel) {
            this.spawn((ServerLevel)level, containerStack, pos);
            level.gameEvent(player, GameEvent.ENTITY_PLACE, pos);
        }

    }

    public EntityType<?> getFishType() {
        return this.entityType.get();
    }

    private static CompoundTag getBucketData(ItemStack stack) {
        return stack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY).copyTag();
    }


    private void spawn(ServerLevel serverLevel, ItemStack bucketedMobStack, BlockPos pos) {
        Entity entity = getFishType().spawn(serverLevel, bucketedMobStack, null, pos, MobSpawnType.BUCKET, true, false);
        if (entity instanceof DwarfFrog frog){
            CompoundTag tag = getBucketData(bucketedMobStack);
            frog.loadFromBucketTag(tag);
            frog.setFromBucket(true);
            if (!tag.contains("Age")){
                if (frog.isBaby() && bucketedMobStack.is(FintyItems.DWARF_FROG_BUCKET.get())){
                    frog.setBaby(false);
                }else if (!frog.isBaby() && bucketedMobStack.is(FintyItems.DWARF_FROG_TADPOLE_BUCKET.get())){
                    frog.setAge(-12000);
                }
            }
        }else if (entity instanceof Bucketable bucketable) {
            CompoundTag tag = getBucketData(bucketedMobStack);
            bucketable.loadFromBucketTag(tag);
            bucketable.setFromBucket(true);

        }

    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        List<Component> details = new ArrayList<>();

        ChatFormatting[] achatformatting = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
        ChatFormatting[] bchatformatting = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.AQUA};


        MutableComponent translatable = Component.translatable("fintastic.translatable.shift");
        translatable.withStyle(bchatformatting);


        if (getFishType() == FintyEntities.COPEPOD.get()) {
            CompoundTag compoundtag = getBucketData(stack);
            if (compoundtag.contains("Variant", 3)) {
                int i = compoundtag.getInt("Variant");


                String scientific = "fintastic.copepod_sci";
                String common = "fintastic.copepod_common." + Copepod.CopepodVariant.byId(i).getSerializedName();


                MutableComponent mutablecomponent = Component.translatable(scientific);
                mutablecomponent.withStyle(bchatformatting);

                details.add(Component.translatable(common).withStyle(achatformatting));
                details.add(mutablecomponent);
            }
        }

        if (getFishType() == FintyEntities.FEATHERBACK.get()) {
            CompoundTag compoundtag = getBucketData(stack);
            if (compoundtag.contains("Variant", 3)) {
                int i = compoundtag.getInt("Variant");


                String featherback_sci = "fintastic.featherback_sci." + Featherback.FeatherbackVariant.byId(i).getSerializedName();
                String common = "fintastic.featherback_common." + Featherback.FeatherbackVariant.byId(i).getSerializedName();


                MutableComponent mutablecomponent = Component.translatable(featherback_sci);
                mutablecomponent.withStyle(bchatformatting);

                details.add(Component.translatable(common).withStyle(achatformatting));
                details.add(mutablecomponent);
            }
        }

        if (getFishType() == FintyEntities.GUPPY.get()) {
            CompoundTag compoundtag = getBucketData(stack);
            if (compoundtag.contains("Variant", 3)) {

                int skin = compoundtag.getInt("Variant");
                int fin_model = compoundtag.getInt("FinModel");
                int fin_color = compoundtag.getInt("FinColor");
                int tail_model = compoundtag.getInt("TailModel");
                int tail_color = compoundtag.getInt("TailColor");
                int main_pattern = compoundtag.getInt("MainPattern");
                int main_pattern_color = compoundtag.getInt("MainPatternColor");
                int second_pattern = compoundtag.getInt("SecondaryPattern");
                int second_pattern_color = compoundtag.getInt("SecondaryPatternColor");

                String base = "fintastic.guppy_base." + skin;

                String fins = "fintastic.guppy_fin." + Guppy.getFinsName(fin_model);
                String finsColor = "fintastic.guppy_color." + fin_color;

                String tail = "fintastic.guppy_tail." + Guppy.getTailName(tail_model);
                String tailColor = "fintastic.guppy_color." + tail_color;

                String mainPattern = "fintastic.guppy_pattern." + Guppy.getMainPatternName(main_pattern);
                String mainPatternColor = "fintastic.guppy_color." + main_pattern_color;

                String secondPattern = "fintastic.guppy_pattern." + Guppy.getSecondPatternName(second_pattern);
                String secondPatternColor = "fintastic.guppy_color." + second_pattern_color;

                String sci = "fintastic.guppy.sci";


                MutableComponent finInfo = Component.translatable("fintastic.guppy_fin").append(":");
                finInfo.append(CommonComponents.SPACE).append(Component.translatable(fins)).append(",");
                finInfo.append(CommonComponents.SPACE).append(Component.translatable(finsColor));

                finInfo.withStyle(achatformatting);


                MutableComponent tailInfo = Component.translatable("fintastic.guppy_tail").append(":");
                tailInfo.append(CommonComponents.SPACE).append(Component.translatable(tail)).append(",");
                tailInfo.append(CommonComponents.SPACE).append(Component.translatable(tailColor));

                tailInfo.withStyle(achatformatting);


                MutableComponent mainPatternInfo = Component.translatable("fintastic.guppy_pattern_1").append(":");
                mainPatternInfo.append(CommonComponents.SPACE).append(Component.translatable(mainPattern)).append(",");
                mainPatternInfo.append(CommonComponents.SPACE).append(Component.translatable(mainPatternColor));

                mainPatternInfo.withStyle(achatformatting);



                MutableComponent secondPatternInfo = Component.translatable("fintastic.guppy_pattern_2").append(":");
                secondPatternInfo.append(CommonComponents.SPACE).append(Component.translatable(secondPattern)).append(",");
                secondPatternInfo.append(CommonComponents.SPACE).append(Component.translatable(secondPatternColor));

                secondPatternInfo.withStyle(achatformatting);


                MutableComponent dorsalFinInfo = Component.translatable("fintastic.guppy_dorsal_fin").withStyle(achatformatting);


                MutableComponent scientific_name = Component.translatable(sci);

                scientific_name.withStyle(bchatformatting);


                details.add(Component.translatable(base).withStyle(achatformatting));
                details.add(finInfo);
                details.add(tailInfo);

                if (compoundtag.contains("HasMainPattern")){
                    boolean has_main_pattern = compoundtag.getBoolean("HasMainPattern");
                    if (has_main_pattern){
                        details.add(mainPatternInfo);
                    }
                }

                if (compoundtag.contains("HasSecondaryPattern")){
                    boolean has_second_pattern = compoundtag.getBoolean("HasSecondaryPattern");
                    if (has_second_pattern){
                        details.add(secondPatternInfo);
                    }
                }

                if (compoundtag.contains("HasDorsalFin")){
                    boolean hasDorsalFin = compoundtag.getBoolean("HasDorsalFin");
                    if (hasDorsalFin){
                        details.add(dorsalFinInfo);
                    }
                }
                details.add(scientific_name);
            }
        }

        if (getFishType() == FintyEntities.PLECO.get()) {
            CompoundTag compoundtag = getBucketData(stack);
            if (compoundtag.contains("Variant", 3)) {
                int i = compoundtag.getInt("Variant");
                Pleco.PlecoVariant variant = Pleco.PlecoVariant.byId(i);

                String featherback_sci = "fintastic.pleco_sci." + variant.getSerializedName();
                String common = "fintastic.pleco_common." + variant.getSerializedName();


                MutableComponent mutablecomponent = Component.translatable(featherback_sci);
                mutablecomponent.withStyle(bchatformatting);

                details.add(Component.translatable(common).withStyle(achatformatting));
                details.add(mutablecomponent);
            }
        }

        if (getFishType() == FintyEntities.CATFISH.get()) {
            CompoundTag compoundtag = getBucketData(stack);
            if (compoundtag.contains("Variant", 3)) {
                int i = compoundtag.getInt("Variant");
                Catfish.CatfishVariant variant = Catfish.CatfishVariant.byId(i);

                String featherback_sci = "fintastic.catfish_sci." + variant.getSerializedName();
                String common = "fintastic.catfish_common." + variant.getSerializedName();


                MutableComponent mutablecomponent = Component.translatable(featherback_sci);
                mutablecomponent.withStyle(bchatformatting);

                details.add(Component.translatable(common).withStyle(achatformatting));
                details.add(mutablecomponent);
            }
        }

        if (getFishType() == FintyEntities.SHARKMINNOW.get()) {
            CompoundTag compoundtag = getBucketData(stack);
            if (compoundtag.contains("Variant", 3)) {
                int i = compoundtag.getInt("Variant");

                String featherback_sci = "fintastic.sharkminnow_sci." + Sharkminnow.SharkminnowVariant.byId(i).getSerializedName();
                String common = "fintastic.sharkminnow_common." + Sharkminnow.SharkminnowVariant.byId(i).getSerializedName();

                MutableComponent commonName = Component.translatable(common);

                MutableComponent mutablecomponent = Component.translatable(featherback_sci);
                mutablecomponent.withStyle(bchatformatting);

                details.add(commonName.withStyle(achatformatting));

                details.add(mutablecomponent);
            }
        }

        if (getFishType() == FintyEntities.MINNOW.get()) {
            CompoundTag compoundtag = getBucketData(stack);
            if (compoundtag.contains("Variant", 3)) {

                int joinedVariantID = Integer.decode(String.valueOf(compoundtag.getInt("Variant")));
                Minnow.MinnowVariant minnowVariant = Minnow.MinnowVariant.byId(joinedVariantID);

                String featherback_sci = "fintastic.minnow_sci." + minnowVariant.getSerializedName();
                String common = "fintastic.minnow_common." + minnowVariant.getSerializedName();

                MutableComponent mutablecomponent = Component.translatable(featherback_sci);
                mutablecomponent.withStyle(bchatformatting);

                details.add(Component.translatable(common).withStyle(achatformatting));

                details.add(mutablecomponent);
            }
        }

        if (getFishType() == FintyEntities.FAIRY_SHRIMP.get()) {
            CompoundTag compoundtag = getBucketData(stack);
            if (compoundtag.contains("Variant", 3)) {
                int j = compoundtag.getInt("Variant");

                String featherback_sci = "fintastic.fairy_shrimp_sci." + FairyShrimp.FairyShrimpVariant.byId(j).getSerializedName();
                String common = "fintastic.fairy_shrimp_common." + FairyShrimp.FairyShrimpVariant.byId(j).getSerializedName();


                MutableComponent mutablecomponent = Component.translatable(featherback_sci);
                mutablecomponent.withStyle(bchatformatting);

                details.add(Component.translatable(common).withStyle(achatformatting));

                details.add(mutablecomponent);
            }
        }

        if (getFishType() == FintyEntities.DAPHNIA.get()) {
            String featherback_sci = "fintastic.daphnia_sci";
            String common = "fintastic.daphnia_common";


            MutableComponent mutablecomponent = Component.translatable(featherback_sci);
            mutablecomponent.withStyle(bchatformatting);

            details.add(Component.translatable(common).withStyle(achatformatting));

            details.add(mutablecomponent);
        }

        if (getFishType() == FintyEntities.MOONY.get()) {
            CompoundTag compoundtag = getBucketData(stack);
            if (compoundtag.contains("Variant", 3)) {

                int i = compoundtag.getInt("Variant");

                String featherback_sci = "fintastic.moony_sci." + Moony.MoonyVariant.byId(i).getSerializedName();
                String common = "fintastic.moony_common." + Moony.MoonyVariant.byId(i).getSerializedName();

                MutableComponent mutablecomponent = Component.translatable(featherback_sci);
                mutablecomponent.withStyle(bchatformatting);

                details.add(Component.translatable(common).withStyle(achatformatting));
                details.add(mutablecomponent);
            }
        }

        if (getFishType() == FintyEntities.ARAPAIMA.get()) {

            String featherback_sci = "fintastic.arapaima_sci";
            String common = "fintastic.arapaima_common";

            MutableComponent mutablecomponent = Component.translatable(featherback_sci);
            mutablecomponent.withStyle(bchatformatting);

            details.add(Component.translatable(common).withStyle(achatformatting));

            details.add(mutablecomponent);
        }

        if (getFishType() == FintyEntities.COELACANTH.get()) {

            String featherback_sci = "fintastic.coelacanth_sci";
            String common = "fintastic.coelacanth_common";

            MutableComponent mutablecomponent = Component.translatable(featherback_sci);
            mutablecomponent.withStyle(bchatformatting);

            details.add(Component.translatable(common).withStyle(achatformatting));

            details.add(mutablecomponent);
        }


        if (getFishType() == FintyEntities.GOURAMI.get()) {
            CompoundTag compoundtag = getBucketData(stack);
            if (compoundtag.contains("VariantModel", 3)) {

                int joinedVariantID = Integer.decode(String.valueOf(compoundtag.getInt("VariantModel")) + compoundtag.getInt("VariantSkin"));

                String featherback_sci = "fintastic.gourami_sci." + joinedVariantID;
                String common = "fintastic.gourami_common." + joinedVariantID;

                MutableComponent mutablecomponent = Component.translatable(featherback_sci);
                mutablecomponent.withStyle(bchatformatting);

                details.add(Component.translatable(common).withStyle(achatformatting));

                details.add(mutablecomponent);
            }
        }

        if (getFishType() == FintyEntities.COD.get()) {
            CompoundTag compoundtag = getBucketData(stack);
            if (compoundtag.contains("Variant", 3)) {

                int i = compoundtag.getInt("Variant");

                String scientific = "fintastic.cod_sci." + FintasticCod.CodVariant.byId(i).getSerializedName();
                String common = "fintastic.cod_common." + FintasticCod.CodVariant.byId(i).getSerializedName();

                MutableComponent mutablecomponent = Component.translatable(scientific);
                mutablecomponent.withStyle(bchatformatting);

                details.add(Component.translatable(common).withStyle(achatformatting));
                details.add(mutablecomponent);
            }
        }
        if (getFishType() == FintyEntities.SALMON.get()) {
            CompoundTag compoundtag = getBucketData(stack);
            if (compoundtag.contains("Variant", 3)) {

                int i = compoundtag.getInt("Variant");
                String j = compoundtag.getString("Size");

                String scientific = "fintastic.salmon_sci." + FintasticSalmon.SalmonVariant.byId(i).getSerializedName();
                String common = "fintastic.salmon_common." + FintasticSalmon.SalmonVariant.byId(i).getSerializedName();
                String size = "fintastic.size." + j;

                MutableComponent sizeInfo = Component.translatable("fintastic.size.title").append(":");
                sizeInfo.append(CommonComponents.SPACE).append(Component.translatable(size));

                sizeInfo.withStyle(achatformatting);

                MutableComponent mutablecomponent = Component.translatable(scientific);
                mutablecomponent.withStyle(bchatformatting);

                details.add(Component.translatable(common).withStyle(achatformatting));
                details.add(sizeInfo);
                details.add(mutablecomponent);
            }
        }


        if (getFishType() == FintyEntities.DWARF_FROG.get()) {
            CompoundTag compoundtag = getBucketData(stack);
            if (compoundtag.contains("Variant", 3)) {
                int i = compoundtag.getInt("Variant");
                int age = compoundtag.getInt("Age");

                String scientific = "fintastic.dwarf_frog_sci";

                String common = "fintastic.dwarf_frog_common." + (age < 0 ? DwarfFrog.FrogVariant.byId(i).getTadpoleName() :
                        DwarfFrog.FrogVariant.byId(i).getSerializedName());


                MutableComponent mutablecomponent = Component.translatable(scientific);
                mutablecomponent.withStyle(bchatformatting);

                details.add(Component.translatable(common).withStyle(achatformatting));
                details.add(mutablecomponent);
            }
        }

        if (getFishType() == FintyEntities.SMALL_CATFISH.get()) {
            CompoundTag compoundtag = getBucketData(stack);
            if (compoundtag.contains("Variant", 3)) {
                int i = compoundtag.getInt("Variant");
                SmallCatfish.SmallCatfishVariant variant = SmallCatfish.SmallCatfishVariant.byId(i);

                String featherback_sci = "fintastic.small_catfish_sci." + variant.getSerializedName();
                String common = "fintastic.small_catfish_common." + variant.getSerializedName();


                MutableComponent mutablecomponent = Component.translatable(featherback_sci);
                mutablecomponent.withStyle(bchatformatting);

                details.add(Component.translatable(common).withStyle(achatformatting));
                details.add(mutablecomponent);
            }
        }

        if (details.isEmpty()) return;

        if (Screen.hasShiftDown()) {
            tooltipComponents.addAll(details);
        }else {
            tooltipComponents.add(translatable);
        }
    }
}
