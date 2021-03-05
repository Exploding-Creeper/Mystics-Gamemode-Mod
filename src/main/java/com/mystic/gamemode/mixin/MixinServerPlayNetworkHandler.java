package com.mystic.gamemode.mixin;

import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static com.mystic.gamemode.usage.GameModeUsage.UNLOCKABLE;

@Mixin(ServerPlayNetworkHandler.class)
public class MixinServerPlayNetworkHandler {
    @Redirect(method = "onCreativeInventoryAction", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerInteractionManager;isCreative()Z"))
    public boolean isCreativeOrJourney(ServerPlayerInteractionManager serverPlayerInteractionManager) {
        return serverPlayerInteractionManager.isCreative() || serverPlayerInteractionManager.getGameMode() == UNLOCKABLE;
    }
}
