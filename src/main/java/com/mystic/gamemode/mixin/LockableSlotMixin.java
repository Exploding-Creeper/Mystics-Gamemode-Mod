package com.mystic.gamemode.mixin;

import com.mystic.gamemode.usage.GameModeUsage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreativeInventoryScreen.LockableSlot.class)
public class LockableSlotMixin {

    @Inject(at = @At("RETURN"), method = "canTakeItems", cancellable = true)
    public void canTakeItems(PlayerEntity playerEntity, CallbackInfoReturnable<Boolean> infoReturnable) {
        infoReturnable.setReturnValue(false);
    }
   
}
