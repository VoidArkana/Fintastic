package net.voidarkana.fintastic.mixin.common;

import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.Predicate;

@Mixin(SimpleCriterionTrigger.class)
public interface SimpleCriterionTriggerInvoker<T extends SimpleCriterionTrigger.SimpleInstance> {

    @Invoker("trigger")
    void fintastic$trigger(ServerPlayer player, Predicate<T> predicate);
}
