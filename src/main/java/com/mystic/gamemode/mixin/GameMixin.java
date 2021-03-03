package com.mystic.gamemode.mixin;

import com.chocohead.mm.api.ClassTinkerers;
import com.mystic.gamemode.usage.GameModeUsage;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameMode.class)
public abstract class GameMixin
{
    @Shadow public abstract String getName();

    @Inject(at = @At("HEAD"), method = "Lnet/minecraft/world/GameMode;isBlockBreakingRestricted()Z", cancellable = true)
    private void isSurvivalLike(CallbackInfoReturnable<Boolean> cir){

        if (getName().equals(ClassTinkerers.getEnum(GameMode.class, "UNLOCKABLE").getName())) {
            cir.cancel();
            cir.setReturnValue(false);
        }
    }

    @Inject(at = @At("HEAD"), method = "Lnet/minecraft/world/GameMode;setAbilities(Lnet/minecraft/entity/player/PlayerAbilities;)V")
    private void setAbilities(PlayerAbilities abilities, CallbackInfo ci){
        if(getName().equals(GameModeUsage.UNLOCKABLE.getName())){
            abilities.allowFlying = false;
            abilities.creativeMode = true;
            abilities.invulnerable = false;
            abilities.flying = false;
        }
    }
}
