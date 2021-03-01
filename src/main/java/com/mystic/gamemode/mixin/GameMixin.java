package com.mystic.gamemode.mixin;

import com.chocohead.mm.api.ClassTinkerers;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameMode.class)
public class GameMixin
{
    private static GameMode gameMode;

    @Inject(at = @At("HEAD"), method = "Lnet/minecraft/world/GameMode;isSurvivalLike()Z", cancellable = true)
    private void isSurvivalLike(CallbackInfoReturnable<Boolean> cir){
        if(gameMode == ClassTinkerers.getEnum(GameMode.class, "UNLOCKABLE")){
            cir.cancel();
            cir.setReturnValue(true);
        }
    }
}
