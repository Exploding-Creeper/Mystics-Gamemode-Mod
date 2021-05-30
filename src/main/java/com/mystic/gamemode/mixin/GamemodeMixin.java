package com.mystic.gamemode.mixin;

import com.chocohead.mm.api.ClassTinkerers;
import net.minecraft.client.gui.screen.GameModeSelectionScreen;
import net.minecraft.world.GameMode;
import org.lwjgl.system.NonnullDefault;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

import static com.mystic.gamemode.usage.GameModeUsage.UNLOCKABLE;


@Mixin(GameModeSelectionScreen.GameMode.class)
public class GamemodeMixin {

	@Final private Optional<GameModeSelectionScreen.GameMode> currentGameMode;

	private GameModeSelectionScreen.GameMode gameMode;

	private static GameMode gameMode2;

	@Inject(at = @At("HEAD"), method = "next()Ljava/util/Optional;", cancellable = true)
	private void next(CallbackInfoReturnable<Optional<GameModeSelectionScreen.GameMode>> cir) {
		if (gameMode == GameModeSelectionScreen.GameMode.SPECTATOR || gameMode == GameModeSelectionScreen.GameMode.ADVENTURE ||
				gameMode == GameModeSelectionScreen.GameMode.SURVIVAL || gameMode == GameModeSelectionScreen.GameMode.CREATIVE) {
			cir.cancel();
			cir.setReturnValue(Optional.of(ClassTinkerers.getEnum(GameModeSelectionScreen.GameMode.class, "UNLOCKABLE")));
		}else if (gameMode == ClassTinkerers.getEnum(GameModeSelectionScreen.GameMode.class, "UNLOCKABLE")){
			cir.setReturnValue(Optional.of(GameModeSelectionScreen.GameMode.CREATIVE));
			cir.setReturnValue(Optional.of(GameModeSelectionScreen.GameMode.SURVIVAL));
			cir.setReturnValue(Optional.of(GameModeSelectionScreen.GameMode.ADVENTURE));
			cir.setReturnValue(Optional.of(GameModeSelectionScreen.GameMode.SPECTATOR));
		}else{
			cir.setReturnValue(Optional.of(GameModeSelectionScreen.GameMode.SURVIVAL));
		}
	}

	@Inject(at = @At("HEAD"), method = "of(Lnet/minecraft/world/GameMode;)Ljava/util/Optional;", cancellable = true)
	private static void of(CallbackInfoReturnable<Optional<GameModeSelectionScreen.GameMode>> cir) {
		if (gameMode2 == UNLOCKABLE) {
			cir.cancel();
			cir.setReturnValue(Optional.of(ClassTinkerers.getEnum(GameModeSelectionScreen.GameMode.class, "UNLOCKABLE")));
		}else{
			cir.setReturnValue(Optional.of(GameModeSelectionScreen.GameMode.CREATIVE));
		}
	}
}
