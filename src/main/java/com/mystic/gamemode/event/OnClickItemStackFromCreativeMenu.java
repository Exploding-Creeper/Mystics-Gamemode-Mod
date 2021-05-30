package com.mystic.gamemode.event;

/*import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.ActionResult;

public interface OnClickItemStackFromCreativeMenu
{
    Event<OnClickItemStackFromCreativeMenu> EVENT = EventFactory.createArrayBacked(OnClickItemStackFromCreativeMenu.class,
            (listeners) -> (player, inventory, index, x ,y, slotActionType) -> {
                for(OnClickItemStackFromCreativeMenu event : listeners) {
                    ActionResult result = event.interact(player, inventory, index, x, y, slotActionType);

                    if(result != ActionResult.PASS){
                        return result;
                    }
                }
                return ActionResult.PASS;
            }
    );

    ActionResult interact(PlayerEntity player, Inventory inventory, int index, int x, int y, SlotActionType slotActionType);
}*/
