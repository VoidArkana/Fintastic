package net.voidarkana.fintastic.util.data.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import net.voidarkana.fintastic.common.block.FintyBlocks;

import java.util.Set;

public class YAFMBlockLootTableProvider extends BlockLootSubProvider {

    public YAFMBlockLootTableProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {

        this.dropSelf(FintyBlocks.ANUBIAS.get());
        this.dropSelf(FintyBlocks.FISHBOWL.get());
        
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return FintyBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
