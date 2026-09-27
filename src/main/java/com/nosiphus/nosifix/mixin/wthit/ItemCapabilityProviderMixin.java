package com.nosiphus.nosifix.mixin.wthit;

import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.ItemData;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@Mixin(targets = "mcp.mobius.waila.plugin.neo.provider.ItemCapabilityProvider", remap = false)
public class ItemCapabilityProviderMixin implements IDataProvider<BlockEntity> {

    @Nullable
    private BlockCapabilityCache<IItemHandler, @Nullable Direction> cache;

    /**
     * @author seancrain
     * @reason WTHIT on NeoForge has a bug where it will display the first block you look at's properties for all blocks. (WTHIT PR: #372)
     */
    @Overwrite(remap = false)
    public void appendData(IDataWriter data, IServerAccessor<BlockEntity> accessor, IPluginConfig config) {
        data.add(ItemData.TYPE, res -> {
            var world = accessor.getLevel();
            var target = accessor.getTarget();
            var pos = target.getBlockPos();

            if (cache == null || (cache.level() != world || !cache.pos().equals(pos))) {
                cache = BlockCapabilityCache.create(Capabilities.ItemHandler.BLOCK, world, pos, null);
            }

            var handler = cache.getCapability();
            if (handler == null) return;

            res.add(ItemData.of(config).getter(handler::getStackInSlot, handler.getSlots()));
        });
    }

}