package com.mystic.gamemode.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.world.GameMode;

public class ClientInitializer implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        GameMode.class.getName(); //Here to early load class but not to early ;)
        /*KeyBinding binding = KeyBindingHelper.registerKeyBinding(new KeyBinding("gamemode.mystic_keybinding_1", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_U, "key.category.first.mystic"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (binding.wasPressed()) {
                assert client.player != null;
                if(GameModeUsage.getGameModeFromPlayerEntity(client.player) == GameModeUsage.UNLOCKABLE) {
                    client.openScreen(new CreativeInventoryScreen(client.player));
                }
            }
        });*/ //TODO fix this with new creative menu and E key be the survival menu!
    }
}
