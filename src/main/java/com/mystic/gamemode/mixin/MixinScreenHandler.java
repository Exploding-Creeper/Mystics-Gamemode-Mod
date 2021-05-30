package com.mystic.gamemode.mixin;

/*import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.lwjgl.glfw.GLFW;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.event.MouseEvent;

@Mixin(ScreenHandler.class)
public abstract class MixinScreenHandler {

    @Shadow public abstract Slot getSlot(int index);

    @Inject(method = "method_30010", at = @At(value = "FIELD", target = "Lnet/minecraft/screen/slot/SlotActionType;PICKUP:Lnet/minecraft/screen/slot/SlotActionType;", opcode = Opcodes.GETSTATIC), cancellable = true)
    public void onClickSlot(int slotIndex, int buttonIndex, SlotActionType slotActionType, PlayerEntity playerEntity, CallbackInfoReturnable<ItemStack> cir){
        if(slotActionType == SlotActionType.PICKUP || slotActionType == SlotActionType.QUICK_MOVE){
            OnCreativeClick.EVENT.invoker().interact(playerEntity, playerEntity.inventory, slotIndex, getSlot(slotIndex).x, getSlot(slotIndex).y, slotActionType);
        }
    }
}*/
