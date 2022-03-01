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


@Mixin(GameModeSelectionScreen.GameModeSelection.class)
public class GamemodeMixin {

	@Final private Optional<GameModeSelectionScreen.GameModeSelection> currentGameMode;

	private GameModeSelectionScreen.GameModeSelection gameMode;

	private static GameMode gameMode2;

	@Inject(at = @At("HEAD"), method = "next()Ljava/util/Optional;", cancellable = true)
	private void next(CallbackInfoReturnable<Optional<GameModeSelectionScreen.GameModeSelection>> cir) {
		if (gameMode == GameModeSelectionScreen.GameModeSelection.SPECTATOR || gameMode == GameModeSelectionScreen.GameModeSelection.ADVENTURE ||
				gameMode == GameModeSelectionScreen.GameModeSelection.SURVIVAL || gameMode == GameModeSelectionScreen.GameModeSelection.CREATIVE) {
			cir.cancel();
			cir.setReturnValue(Optional.of(ClassTinkerers.getEnum(GameModeSelectionScreen.GameModeSelection.class, "UNLOCKABLE")));
		}else if (gameMode == ClassTinkerers.getEnum(GameModeSelectionScreen.GameModeSelection.class, "UNLOCKABLE")){
			cir.setReturnValue(Optional.of(GameModeSelectionScreen.GameModeSelection.CREATIVE));
			cir.setReturnValue(Optional.of(GameModeSelectionScreen.GameModeSelection.SURVIVAL));
			cir.setReturnValue(Optional.of(GameModeSelectionScreen.GameModeSelection.ADVENTURE));
			cir.setReturnValue(Optional.of(GameModeSelectionScreen.GameModeSelection.SPECTATOR));
		}else{
			cir.setReturnValue(Optional.of(GameModeSelectionScreen.GameModeSelection.SURVIVAL));
		}
	}

	@Inject(at = @At("HEAD"), method = "of(Lnet/minecraft/world/GameMode;)Ljava/util/Optional;", cancellable = true)
	private static void of(CallbackInfoReturnable<Optional<GameModeSelectionScreen.GameModeSelection>> cir) {
		if (gameMode2 == UNLOCKABLE) {
			cir.cancel();
			cir.setReturnValue(Optional.of(ClassTinkerers.getEnum(GameModeSelectionScreen.GameModeSelection.class, "UNLOCKABLE")));
		}else{
			cir.setReturnValue(Optional.of(GameModeSelectionScreen.GameModeSelection.CREATIVE));
		}
	}
}
