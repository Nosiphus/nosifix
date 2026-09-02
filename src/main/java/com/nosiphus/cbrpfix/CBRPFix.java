package com.nosiphus.cbrpfix;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(CBRPFix.MODID)
public class CBRPFix {
    public static final String MODID = "cbrpfix";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CBRPFix(IEventBus modEventBus, ModContainer modContainer) {
    }
}
