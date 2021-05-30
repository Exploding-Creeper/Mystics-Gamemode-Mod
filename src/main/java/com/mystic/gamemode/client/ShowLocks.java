package com.mystic.gamemode.client;

/*import com.mystic.gamemode.mixin.LockableSlotMixin;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Environment(EnvType.CLIENT)
public class ShowLocks extends CreativeInventoryScreen {

    private final Text allowButtonTitle = Text.of("allow");
    private final Text denyButtonTitle = Text.of("deny");
    private ButtonWidget allowButtonWidget = new ButtonWidget(0, 0, 20, 20, allowButtonTitle, button -> {});
    private ButtonWidget denyButtonWidget = new ButtonWidget(0, 0, 20, 20, denyButtonTitle, button -> {});


    @Override
    public void init(){
        super.init();
        allowButtonWidget = addButton(new ButtonWidget(0, 0, 20, 20, allowButtonTitle, button -> {
            allowButtonWidget.visible = false;
            denyButtonWidget.visible = false;
            allowButtonWidget.active = false;
            denyButtonWidget.active = false;
        }));
        denyButtonWidget = addButton(new ButtonWidget(0, 0, 20, 20, denyButtonTitle, button -> {
            allowButtonWidget.visible = false;
            denyButtonWidget.visible = false;
            allowButtonWidget.active = false;
            denyButtonWidget.active = false;
        }));
        allowButtonWidget.visible = false;
        denyButtonWidget.visible = false;
        allowButtonWidget.active = false;
        denyButtonWidget.active = false;
    }

    public ShowLocks(PlayerEntity playerEntity, Inventory inventory, int index, int x, int y) {
        super(playerEntity);

        if(!new LockableSlot(inventory, index, x, y).canTakeItems(playerEntity)){
            for (Item item : IntStream.range(0, inventory.size()).mapToObj(i2 -> inventory.getStack(i2).getItem()).collect(Collectors.toList())) {
                if(clickCreativeStackWhileLocked(new LockableSlot(inventory, index, x, y), playerEntity)){
                    if(allowButtonWidget.isFocused()){
                        //set this true for the item clicked.
                    }else if (denyButtonWidget.isFocused()){
                        allowButtonWidget.visible = false;
                        denyButtonWidget.visible = false;
                        allowButtonWidget.active = false;
                        denyButtonWidget.active = false;
                    }
                }
            }
        }
    }

    public boolean clickCreativeStackWhileLocked(LockableSlot lockableSlot, PlayerEntity playerEntity){
        if (lockableSlot != null) {
            if (!lockableSlot.hasStack()) {
                if (!lockableSlot.canTakeItems(playerEntity)) {
                    allowButtonWidget.y = lockableSlot.y + 10;
                    denyButtonWidget.y = lockableSlot.y + 10;
                    denyButtonWidget.x = lockableSlot.x - 10;
                    allowButtonWidget.x = lockableSlot.x + 10;
                    allowButtonWidget.visible = true;
                    denyButtonWidget.visible = true;
                    allowButtonWidget.active = true;
                    denyButtonWidget.active = true;
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}*/
