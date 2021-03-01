package com.mystic.gamemode.mixin;

import com.chocohead.mm.api.ClassTinkerers;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.MoreOptionsDialog;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreateWorldScreen.class)
public abstract class WorldCreateMixin{

    private TranslatableText firstGameModeDescriptionLine;
    private TranslatableText secondGameModeDescriptionLine;
    private AbstractButtonWidget difficultyButton;
    private AbstractButtonWidget enableCheatsButton;
    @Shadow public boolean hardcore;
    @Shadow private boolean cheatsEnabled;
    @Shadow private boolean tweakedCheats;
    @Shadow public ButtonWidget gameModeSwitchButton;

    @Shadow
    public void tweakDefaultsTo(CreateWorldScreen.Mode mode) {
        if (!this.tweakedCheats) {
            this.cheatsEnabled = mode == CreateWorldScreen.Mode.CREATIVE;
        }

        if (mode == CreateWorldScreen.Mode.HARDCORE) {
            this.hardcore = true;
            this.enableCheatsButton.active = false;
            this.moreOptionsDialog.bonusItemsButton.active = false;
            this.difficultyButton.active = false;
        } else {
            this.hardcore = false;
            this.enableCheatsButton.active = true;
            this.moreOptionsDialog.bonusItemsButton.active = true;
            this.difficultyButton.active = true;
        }

        this.currentMode = mode;
        this.updateSettingsLabels();
    }

    @Shadow
    public void updateSettingsLabels() {
        this.firstGameModeDescriptionLine = new TranslatableText("selectWorld.gameMode." + this.currentMode.translationSuffix + ".line1");
        this.secondGameModeDescriptionLine = new TranslatableText("selectWorld.gameMode." + this.currentMode.translationSuffix + ".line2");

    }

    @Shadow  private CreateWorldScreen.Mode currentMode;

    @Shadow @Final public MoreOptionsDialog moreOptionsDialog;

    @Shadow protected abstract <T extends AbstractButtonWidget> T addButton(T button);

    @Inject(method = "init()V", at = @At(value = "HEAD", target = "Lnet/minecraft/client/gui/screen/world/CreateWorldScreen;gameModeSwitchButton:Lnet/minecraft/client/gui/widget/ButtonWidget;"), cancellable = true)
    private void init(CallbackInfo ci) {
        ci.cancel();
        int i = 100 / 2 - 155;
        this.gameModeSwitchButton = this.addButton(new ButtonWidget(i, 100, 150, 20, LiteralText.EMPTY, (buttonWidget) -> {
            if (currentMode == CreateWorldScreen.Mode.SURVIVAL) {
                tweakDefaultsTo(CreateWorldScreen.Mode.HARDCORE);
            } else if (currentMode == CreateWorldScreen.Mode.HARDCORE) {
                tweakDefaultsTo(CreateWorldScreen.Mode.CREATIVE);
            } else if (currentMode == CreateWorldScreen.Mode.CREATIVE) {
                tweakDefaultsTo(ClassTinkerers.getEnum(CreateWorldScreen.Mode.class, "UNLOCKABLE"));
            } else if (currentMode == ClassTinkerers.getEnum(CreateWorldScreen.Mode.class, "UNLOCKABLE")) {
                tweakDefaultsTo(CreateWorldScreen.Mode.SURVIVAL);
            }
            buttonWidget.queueNarration(250);
        }));
    }

    /*@Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screen/world/CreateWorldScreen;gameModeSwitchButton:Lnet/minecraft/client/gui/widget/ButtonWidget;", opcode = Opcodes.PUTFIELD), method = "init()V")
    private void init(CreateWorldScreen createWorldScreen, ButtonWidget value) {
        int i = createWorldScreen.width / 2 - 155;
        this.gameModeSwitchButton = this.addButton(new ButtonWidget(i, 100, 150, 20, LiteralText.EMPTY, (buttonWidget) -> {
            if (currentMode == CreateWorldScreen.Mode.SURVIVAL) {
                tweakDefaultsTo(CreateWorldScreen.Mode.HARDCORE);
            } else if (currentMode == CreateWorldScreen.Mode.HARDCORE) {
                tweakDefaultsTo(CreateWorldScreen.Mode.CREATIVE);
            } else if (currentMode == CreateWorldScreen.Mode.CREATIVE) {
                tweakDefaultsTo(ClassTinkerers.getEnum(CreateWorldScreen.Mode.class, "UNLOCKABLE"));
            } else if (currentMode == ClassTinkerers.getEnum(CreateWorldScreen.Mode.class, "UNLOCKABLE")) {
                tweakDefaultsTo(CreateWorldScreen.Mode.SURVIVAL);
            }
            buttonWidget.queueNarration(250);
        }));
    }*/
}
