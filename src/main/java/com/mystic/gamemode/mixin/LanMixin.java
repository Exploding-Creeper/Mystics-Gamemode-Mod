package com.mystic.gamemode.mixin;

import com.chocohead.mm.api.ClassTinkerers;
import net.minecraft.client.gui.screen.OpenToLanScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OpenToLanScreen.class)
public class LanMixin extends Screen {

    @Shadow private ButtonWidget buttonGameMode;

    @Shadow private String gameMode;

    @Shadow private void updateButtonText() {}

    protected LanMixin(Text title) {
        super(title);
    }

    @Inject(at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screen/OpenToLanScreen;buttonGameMode:Lnet/minecraft/client/gui/widget/ButtonWidget;"), method = "init()V", cancellable = true)
    private void init(CallbackInfo ci) {
        buttonGameMode = this.addButton(new ButtonWidget(this.width / 2 - 185, 100, 20, 20, LiteralText.EMPTY, (buttonWidget) -> {
            if ("spectator".equals(this.gameMode)) {
                this.gameMode = "creative";
            } else if ("creative".equals(this.gameMode)) {
                this.gameMode = "adventure";
            } else if ("adventure".equals(this.gameMode)) {
                this.gameMode = "survival";
            } else if ("survival".equals(this.gameMode)) {
                this.gameMode = "unlockable";
            }else{
                this.gameMode = "spectator";
            }

            this.updateButtonText();
        }));
    }
}
