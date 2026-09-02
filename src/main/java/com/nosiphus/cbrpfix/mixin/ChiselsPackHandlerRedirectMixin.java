package com.nosiphus.cbrpfix.mixin;

import mod.chiselsandbits.forge.handler.AddPackFindersEventHandler;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.repository.Pack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = AddPackFindersEventHandler.class, remap = false)
public abstract class ChiselsPackHandlerRedirectMixin {

    @Redirect(
            method = "lambda$onAddPackFinders$0",
            at = @At(
                    value = "NEW",
                    target = "(ZLnet/minecraft/server/packs/repository/Pack$Position;Z)Lnet/minecraft/server/packs/PackSelectionConfig;",
                    remap = true
            )
    )
    private static PackSelectionConfig cnb$modifyConfig(boolean required, Pack.Position defaultPosition, boolean fixedPosition) {
        return new PackSelectionConfig(true, Pack.Position.TOP, false);
    }
}