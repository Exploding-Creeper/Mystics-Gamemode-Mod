package com.mystic.gamemode.mixin;

import com.chocohead.mm.api.ClassTinkerers;
import com.mystic.gamemode.usage.GameModeUsage;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.resource.DataPackSettings;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.level.LevelInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Path;

@Mixin(CreateWorldScreen.class)
public abstract class WorldCreateMixin extends Screen{

    protected WorldCreateMixin(Text title) {
        super(title);
    }

    @Shadow
    public final void tweakDefaultsTo(CreateWorldScreen.Mode mode) {}

    @Shadow  private CreateWorldScreen.Mode currentMode;

    @Shadow protected abstract <T extends AbstractButtonWidget> T addButton(T button);

    @Shadow public ButtonWidget gameModeSwitchButton;

    @Inject(at = @At(value = "FIELD" ,shift = At.Shift.BEFORE, target = "Lnet/minecraft/client/gui/screen/world/CreateWorldScreen;currentMode:Lnet/minecraft/client/gui/screen/world/CreateWorldScreen$Mode;"), method = "<init>(Lnet/minecraft/client/gui/screen/Screen;Lnet/minecraft/world/level/LevelInfo;Lnet/minecraft/world/gen/GeneratorOptions;Ljava/nio/file/Path;Lnet/minecraft/resource/DataPackSettings;Lnet/minecraft/util/registry/DynamicRegistryManager$Impl;)V")
    private void inject(Screen parent, LevelInfo levelInfo, GeneratorOptions generatorOptions, Path dataPackTempDir, DataPackSettings dataPackSettings, DynamicRegistryManager.Impl registryManager, CallbackInfo ci){
        if(GameModeUsage.getGameModeFromId(4) == levelInfo.getGameMode()) {
            this.currentMode = ClassTinkerers.getEnum(CreateWorldScreen.Mode.class, "UNLOCKABLE");
        }
    }

    @ModifyArg(method = "init()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/world/CreateWorldScreen;tweakDefaultsTo(Lnet/minecraft/client/gui/screen/world/CreateWorldScreen$Mode;)V"))
    private CreateWorldScreen.Mode init(CreateWorldScreen.Mode mode) {
        if(currentMode == CreateWorldScreen.Mode.SURVIVAL) {
            currentMode = ClassTinkerers.getEnum(CreateWorldScreen.Mode.class, "UNLOCKABLE");
            return ClassTinkerers.getEnum(CreateWorldScreen.Mode.class, "UNLOCKABLE");
        }
        if(currentMode == ClassTinkerers.getEnum(CreateWorldScreen.Mode.class, "UNLOCKABLE")) {
            currentMode = CreateWorldScreen.Mode.HARDCORE;
            return CreateWorldScreen.Mode.HARDCORE;
        }
        if(currentMode == CreateWorldScreen.Mode.HARDCORE){
            return CreateWorldScreen.Mode.CREATIVE;
        }
        if(currentMode == CreateWorldScreen.Mode.CREATIVE){
            return CreateWorldScreen.Mode.SURVIVAL;
        }
        return CreateWorldScreen.Mode.CREATIVE;
    }

    @Inject(at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screen/world/CreateWorldScreen;gameModeSwitchButton:Lnet/minecraft/client/gui/widget/ButtonWidget;"), method = "init()V", cancellable = true)
    private void init(CallbackInfo ci) {
        int i = width / 2 - 185;
        gameModeSwitchButton = this.addButton(new ButtonWidget(i, 100, 20, 20, LiteralText.EMPTY, (buttonWidget) -> {
            if (currentMode == CreateWorldScreen.Mode.SURVIVAL) {
                tweakDefaultsTo(ClassTinkerers.getEnum(CreateWorldScreen.Mode.class, "UNLOCKABLE"));
            }
            else if (currentMode == ClassTinkerers.getEnum(CreateWorldScreen.Mode.class, "UNLOCKABLE")) {
                tweakDefaultsTo(CreateWorldScreen.Mode.HARDCORE);
            }
            else if (currentMode == CreateWorldScreen.Mode.HARDCORE) {
                tweakDefaultsTo(CreateWorldScreen.Mode.CREATIVE);
            }
            else if(currentMode == CreateWorldScreen.Mode.CREATIVE){
                tweakDefaultsTo(CreateWorldScreen.Mode.SURVIVAL);
            }
        }));
    }
}
