package net.voidarkana.fintastic.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

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

    public Level getWorld() {
        assert ServerLifecycleHooks.getCurrentServer() != null;
        return ServerLifecycleHooks.getCurrentServer().overworld();
    }
}
