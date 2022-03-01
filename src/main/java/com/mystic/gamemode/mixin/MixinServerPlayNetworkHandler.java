package com.mystic.gamemode.mixin;

import com.chocohead.mm.api.ClassTinkerers;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPlayNetworkHandler.class)
public class MixinServerPlayNetworkHandler {
    @Redirect(method = "onCreativeInventoryAction", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerInteractionManager;isCreative()Z"))
    public boolean isCreativeOrJourney(ServerPlayerInteractionManager serverPlayerInteractionManager) {
        return serverPlayerInteractionManager.isCreative() || serverPlayerInteractionManager.getGameMode() == ClassTinkerers.getEnum(GameMode.class, "UNLOCKABLE");
    }
}
