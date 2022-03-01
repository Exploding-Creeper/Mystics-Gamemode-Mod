package com.mystic.gamemode.client;

import com.mystic.gamemode.usage.GameModeUsage;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.world.GameMode;
import org.lwjgl.glfw.GLFW;

public class ClientInitializer implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        GameMode.class.getName(); //Here to early load class but not to early ;)
        KeyBinding binding = KeyBindingHelper.registerKeyBinding(new KeyBinding("gamemode.mystic_keybinding_1", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_U, "key.category.first.mystic"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (binding.wasPressed()) {
                assert client.player != null;
                if(GameModeUsage.getGameModeFromPlayerEntity(client.player) == GameModeUsage.UNLOCKABLE) {
                    client.setScreen(new CreativeInventoryScreen(client.player));
                }
            }
        });
    }
}
