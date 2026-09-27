package com.nosiphus.nosifix;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(NosiFix.MODID)
public class NosiFix {
    public static final String MODID = "nosifix";
    public static final Logger LOGGER = LogUtils.getLogger();

    public NosiFix(IEventBus modEventBus, ModContainer modContainer) {
    }
}
