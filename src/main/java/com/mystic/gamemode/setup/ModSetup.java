package com.mystic.gamemode.setup;

import com.mystic.gamemode.utils.Reference;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModSetup
{
    @SubscribeEvent
    public static void serverLoad(FMLServerStartingEvent event) {

    }

    @SubscribeEvent
    public static void init(final FMLCommonSetupEvent event) {

    }
}
