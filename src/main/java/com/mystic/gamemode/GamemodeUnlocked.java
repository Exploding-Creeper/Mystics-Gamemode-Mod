package com.mystic.gamemode;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.GameMode;

public class GamemodeUnlocked implements Runnable{

    @Override
    public void run() {
        MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();

        String gameModeCommand = remapper.mapClassName("intermediary", "net.minecraft.class_1934");
        ClassTinkerers.enumBuilder(gameModeCommand, int.class, String.class).addEnum("UNLOCKABLE", 4, "unlockable").build();

        String gameModeWorldScreen = remapper.mapClassName("intermediary", "net.minecraft.class_525$class_4539");
        ClassTinkerers.enumBuilder(gameModeWorldScreen, String.class, GameMode.class).addEnum("UNLOCKABLE", "unlockable", ClassTinkerers.getEnum(GameMode.class, "UNLOCKABLE")).build();

        String gameModeScreenSelect = remapper.mapClassName("intermediary", "net.minecraft.class_5289$class_5290");
        ClassTinkerers.enumBuilder(gameModeScreenSelect, TranslatableText.class, String.class, ItemStack.class ).addEnum("UNLOCKABLE", new TranslatableText("gameMode.unlockable"), "/gamemode unlockable", new ItemStack(Items.ANVIL)).build();

    }

    public static GameMode getUnlockableMode(){
        return ClassTinkerers.getEnum(GameMode.class, "UNLOCKABLE");
    }
}
