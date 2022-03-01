package com.mystic.gamemode.mixin;

import com.mystic.gamemode.usage.GameModeUsage;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.OpenToLanScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.util.NetworkUtils;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(OpenToLanScreen.class)
public class LanMixin extends Screen {

    @Final @Shadow private static Text GAME_MODE_TEXT;
    @Shadow private GameMode gameMode;
    @Shadow private boolean allowCommands;
    @Final @Shadow private Screen parent;
    @Final @Shadow private static Text ALLOW_COMMANDS_TEXT;

    protected LanMixin(Text title) {
        super(title);
    }

    @Inject(method = "init()V", at = @At(value = "HEAD"), cancellable = true)
    private void init(CallbackInfo ci) {
        ci.cancel();
        this.addDrawableChild(CyclingButtonWidget.builder(GameMode::getSimpleTranslatableName).values(new GameMode[]{GameMode.SURVIVAL, GameMode.SPECTATOR, GameMode.CREATIVE, GameMode.ADVENTURE, GameModeUsage.getGameMode()}).initially(this.gameMode).build(this.width / 2 - 155, 100, 150, 20, GAME_MODE_TEXT, (button, gameMode) -> {
            this.gameMode = gameMode;
        }));
        this.addDrawableChild(CyclingButtonWidget.onOffBuilder(this.allowCommands).build(this.width / 2 + 5, 100, 150, 20, ALLOW_COMMANDS_TEXT, (button, allowCommands) -> {
            this.allowCommands = allowCommands;
        }));
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 155, this.height - 28, 150, 20, new TranslatableText("lanServer.start"), (button) -> {
            Objects.requireNonNull(this.client).setScreen(null);
            int i = NetworkUtils.findLocalPort();
            TranslatableText text;
            if (Objects.requireNonNull(this.client.getServer()).openToLan(this.gameMode, this.allowCommands, i)) {
                text = new TranslatableText("commands.publish.started", new Object[]{i});
            } else {
                text = new TranslatableText("commands.publish.failed");
            }

            this.client.inGameHud.getChatHud().addMessage(text);
            this.client.updateWindowTitle();
        }));
        this.addDrawableChild(new ButtonWidget(this.width / 2 + 5, this.height - 28, 150, 20, ScreenTexts.CANCEL, (button) -> {
            Objects.requireNonNull(this.client).setScreen(this.parent);
        }));
    }
}
