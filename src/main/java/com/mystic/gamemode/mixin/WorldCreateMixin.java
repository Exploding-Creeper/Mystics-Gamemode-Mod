package com.mystic.gamemode.mixin;

import com.chocohead.mm.api.ClassTinkerers;
import com.mystic.gamemode.usage.GameModeUsage;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.EditGameRulesScreen;
import net.minecraft.client.gui.screen.world.MoreOptionsDialog;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.resource.DataPackSettings;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.level.LevelInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Path;
import java.util.Objects;

@Mixin(CreateWorldScreen.class)
public abstract class WorldCreateMixin extends Screen{

    @Shadow private Text firstGameModeDescriptionLine;
    @Shadow private Text secondGameModeDescriptionLine;
    @Shadow private TextFieldWidget levelNameField;
    @Shadow private String levelName;
    @Shadow private ButtonWidget createLevelButton;
    @Shadow private CyclingButtonWidget difficultyButton;
    @Shadow private ButtonWidget moreOptionsButton;
    @Shadow public boolean hardcore;
    @Shadow private boolean cheatsEnabled;
    @Shadow private Difficulty currentDifficulty;
    @Shadow private CyclingButtonWidget enableCheatsButton;
    @Shadow private ButtonWidget dataPacksButton;
    private Difficulty difficulty;
    @Shadow private boolean tweakedCheats;
    @Shadow private ButtonWidget gameRulesButton;
    @Shadow private GameRules gameRules;
    @Final @Shadow public MoreOptionsDialog moreOptionsDialog;
    @Final @Shadow private static Text GAME_MODE_TEXT;

    @Shadow private Difficulty getDifficulty() {
        return difficulty;
    }
    @Shadow private void toggleMoreOptions() {}
    @Shadow private void createLevel() {}
    @Shadow public void onCloseScreen() {}
    @Shadow public void setMoreOptionsOpen() {}
    @Shadow private void updateSaveFolderName() {}
    @Shadow public final void tweakDefaultsTo(CreateWorldScreen.Mode mode) {}
    @Shadow private CreateWorldScreen.Mode currentMode;
    @Shadow String saveDirectoryName;
    @Shadow private CyclingButtonWidget gameModeSwitchButton;
    @Shadow private void openPackScreen() {}

    protected WorldCreateMixin(Text title, Difficulty difficulty) {
        super(title);
        this.difficulty = difficulty;
    }

    @Inject(at = @At(value = "FIELD" ,shift = At.Shift.BEFORE, target = "Lnet/minecraft/client/gui/screen/world/CreateWorldScreen;currentMode:Lnet/minecraft/client/gui/screen/world/CreateWorldScreen$Mode;"), method = "<init>(Lnet/minecraft/client/gui/screen/Screen;Lnet/minecraft/world/level/LevelInfo;Lnet/minecraft/world/gen/GeneratorOptions;Ljava/nio/file/Path;Lnet/minecraft/resource/DataPackSettings;Lnet/minecraft/util/registry/DynamicRegistryManager$Impl;)V")
    private void inject(Screen parent, LevelInfo levelInfo, GeneratorOptions generatorOptions, Path dataPackTempDir, DataPackSettings dataPackSettings, DynamicRegistryManager.Impl registryManager, CallbackInfo ci){
        if(GameModeUsage.getGameModeFromId(4) == levelInfo.getGameMode()) {
            currentMode = ClassTinkerers.getEnum(CreateWorldScreen.Mode.class, "UNLOCKABLE");
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

    @Inject(method = "init()V", at = @At(value = "HEAD"), cancellable = true)
    private void init(CallbackInfo ci) {
        ci.cancel();
        Objects.requireNonNull(client).keyboard.setRepeatEvents(true);
        levelNameField = new TextFieldWidget(textRenderer, width / 2 - 100, 60, 200, 20, new TranslatableText("selectWorld.enterName")) {
            protected MutableText getNarrationMessage() {
                return ScreenTexts.joinSentences(super.getNarrationMessage(), new TranslatableText("selectWorld.resultFolder")).append(" ").append(saveDirectoryName);
            }
        };
        levelNameField.setText(levelName);
        levelNameField.setChangedListener((levelName) -> {
            createLevelButton.active = !levelNameField.getText().isEmpty();
            updateSaveFolderName();
        });
        addSelectableChild(levelNameField);
        int i = width / 2 - 155;
        int j = width / 2 + 5;
        gameModeSwitchButton = addDrawableChild(CyclingButtonWidget.builder(CreateWorldScreen.Mode::asText).values(new CreateWorldScreen.Mode[]{ CreateWorldScreen.Mode.SURVIVAL, CreateWorldScreen.Mode.HARDCORE, CreateWorldScreen.Mode.CREATIVE, GameModeUsage.getMode()}).initially(currentMode).narration((button) -> ClickableWidget.getNarrationMessage(button.getMessage()).append(ScreenTexts.SENTENCE_SEPARATOR).append(firstGameModeDescriptionLine).append(" ").append(secondGameModeDescriptionLine)).build(i, 100, 150, 20, GAME_MODE_TEXT, (button, mode) -> {
            tweakDefaultsTo(mode);
        }));
        difficultyButton = addDrawableChild(CyclingButtonWidget.builder(Difficulty::getTranslatableName).values(Difficulty.values()).initially(getDifficulty()).build(j, 100, 150, 20, new TranslatableText("options.difficulty"), (button, difficulty) -> {
            currentDifficulty = difficulty;
        }));

        enableCheatsButton = addDrawableChild(CyclingButtonWidget.onOffBuilder(cheatsEnabled && !hardcore).narration((button) -> ScreenTexts.joinSentences(button.getGenericNarrationMessage(), new TranslatableText("selectWorld.allowCommands.info"))).build(i, 151, 150, 20, new TranslatableText("selectWorld.allowCommands"), (button, cheatsEnabled) -> {
            tweakedCheats = true;
        }));
        dataPacksButton = addDrawableChild(new ButtonWidget(j, 151, 150, 20, new TranslatableText("selectWorld.dataPacks"), (button) -> {
            openPackScreen();
        }));
        gameRulesButton = addDrawableChild(new ButtonWidget(i, 185, 150, 20, new TranslatableText("selectWorld.gameRules"), (button) -> {
            client.setScreen(new EditGameRulesScreen(gameRules.copy(), (optional) -> {
                client.setScreen(this);
                optional.ifPresent((gameRules) -> {
                });
            }));
        }));
        moreOptionsDialog.init(CreateWorldScreen.create(this), client, textRenderer);
        moreOptionsButton = addDrawableChild(new ButtonWidget(j, 185, 150, 20, new TranslatableText("selectWorld.moreWorldOptions"), (button) -> {
            toggleMoreOptions();
        }));
        createLevelButton = addDrawableChild(new ButtonWidget(i, height - 28, 150, 20, new TranslatableText("selectWorld.create"), (button) -> {
            createLevel();
        }));
        createLevelButton.active = !levelName.isEmpty();
        addDrawableChild(new ButtonWidget(j, height - 28, 150, 20, ScreenTexts.CANCEL, (button) -> {
            onCloseScreen();
        }));
        setMoreOptionsOpen();
        setInitialFocus(levelNameField);
        tweakDefaultsTo(currentMode);
        updateSaveFolderName();
    }
}
