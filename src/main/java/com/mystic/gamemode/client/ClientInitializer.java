package com.mystic.gamemode.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.world.GameMode;

public class ClientInitializer implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        GameMode.class.getName();
    }
}
