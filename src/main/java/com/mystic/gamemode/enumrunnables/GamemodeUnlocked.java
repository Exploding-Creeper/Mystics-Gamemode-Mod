package com.mystic.gamemode.enumrunnables;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.GameMode;

public class GamemodeUnlocked implements Runnable {

    @Override
    public void run() {
        MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();

        String gameModeCommand = remapper.mapClassName("intermediary", "net.minecraft.class_1934");
        ClassTinkerers.enumBuilder(gameModeCommand, int.class, String.class).addEnum("UNLOCKABLE", 4, "unlockable").build();

        String gameModeScreenSelect = remapper.mapClassName("intermediary", "net.minecraft.class_5289$class_5290");
        String itemstack = 'L' + remapper.mapClassName("intermediary", "net.minecraft.class_1799") + ';';
        String text = 'L' + remapper.mapClassName("intermediary", "net.minecraft.class_2561") + ';';
        ClassTinkerers.enumBuilder(gameModeScreenSelect, text, "Ljava/lang/String;", itemstack).addEnum("UNLOCKABLE", () ->
                new Object[]{new TranslatableText("gameMode.unlockable"), "/gamemode unlockable", new ItemStack(Items.ANVIL)}).build();

        String mode = remapper.mapClassName("intermediary", "net.minecraft.class_525$class_4539");
        ClassTinkerers.enumBuilder(mode ,String.class, 'L' + gameModeCommand + ';').addEnum("UNLOCKABLEMODE", () -> new Object[] {"unlockable", ClassTinkerers.getEnum(GameMode.class, "UNLOCKABLE")}).build();
    }
}
