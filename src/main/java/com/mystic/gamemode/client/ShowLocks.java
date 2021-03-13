package com.mystic.gamemode.client;

import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ShowLocks extends CreativeInventoryScreen {

    public static final

    public ShowLocks(PlayerEntity player, Inventory inventory, int i1, int j, int k) {
        super(player);

        if(new LockableSlot(inventory, i1, j, k).canTakeItems(player) == false){
            for (Item item : IntStream.range(0, inventory.size()).mapToObj(i2 -> inventory.getStack(i2).getItem()).collect(Collectors.toList())) {
                //ask if to purchase item/block here!!!
                //if(true){

                //}
            }
        }
    }
}
