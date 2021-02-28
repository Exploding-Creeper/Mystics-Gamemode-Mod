package com.mystic.gamemode.mixin;

import com.mystic.gamemode.GamemodeUnlocked;
import net.minecraft.client.gui.screen.GameModeSelectionScreen;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;


@Mixin(GameModeSelectionScreen.class)
public class GamemodeMixin {


	@Shadow @Final private Optional<GameModeSelectionScreen.GameMode> currentGameMode;

	@Shadow private Optional<GameModeSelectionScreen.GameMode> gameMode;

	@Inject(at = @At("HEAD"), method = "Lnet/minecraft/client/gui/screen/GameModeSelectionScreen$GameMode;next()Ljava/util/Optional;", cancellable = true)
	public GameMode next(CallbackInfo ci) {
		if(GameMode.byId(4) == GamemodeUnlocked.getUnlockableMode()){
			ci.cancel();
			return GamemodeUnlocked.getUnlockableMode(); //Change the Value after cancling
		}
		return GameMode.SURVIVAL;
	}

	@Inject(at = @At("HEAD"), method = "Lnet/minecraft/client/gui/screen/GameModeSelectionScreen$GameMode;of(Lnet/minecraft/world/GameMode;)Ljava/util/Optional;", cancellable = true)
	public GameMode of(CallbackInfo ci) {
		if(GameMode.byId(4) == GamemodeUnlocked.getUnlockableMode()){
			ci.cancel();
			return GamemodeUnlocked.getUnlockableMode();
		}
		return GameMode.SURVIVAL;
	}
}
