package net.voidarkana.fintastic.util.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.voidarkana.fintastic.Fintastic;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = Fintastic.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new FintyRecipeProvider(packOutput));
//        generator.addProvider(event.includeServer(), FintyLootTableProvider.create(packOutput));

        generator.addProvider(event.includeClient(), new FintyBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new FintyItemModelProvider(packOutput, existingFileHelper));

        FintyBlockTagGenerator blockTagGenerator = generator.addProvider(event.includeServer(),
                new FintyBlockTagGenerator(packOutput, lookupProvider, existingFileHelper));

        generator.addProvider(event.includeServer(),new FintyItemTagGenerator(packOutput, lookupProvider, blockTagGenerator.contentsGetter(), existingFileHelper));

        generator.addProvider(event.includeServer(),new FintyFluidTagGenerator(packOutput, lookupProvider, existingFileHelper));

        generator.addProvider(event.includeServer(),new FintyBiomeTagGenerator(packOutput, lookupProvider, existingFileHelper));

        generator.addProvider(event.includeServer(),new FintyPOITagsProvider(packOutput, lookupProvider, existingFileHelper));

        generator.addProvider(event.includeServer(),new FintyEntityTypeTagGenerator(packOutput, lookupProvider, existingFileHelper));

        generator.addProvider(event.includeServer(), new FintyWorldGenProvider(packOutput, lookupProvider));
    }
}
