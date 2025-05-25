package net.voidarkana.fintastic.util;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.common.Mod;
import net.voidarkana.fintastic.Fintastic;

@Mod.EventBusSubscriber(modid = Fintastic.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonProxy {
    public void init() {
    }

    public void clientInit() {
    }

    public Player getClientSidePlayer() {
        return null;
    }

    public Object getArmorRenderProperties() {
        return null;
    }
}
