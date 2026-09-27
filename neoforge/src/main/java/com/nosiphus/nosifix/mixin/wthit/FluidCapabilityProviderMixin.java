package com.nosiphus.nosifix.mixin.wthit;

import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.FluidData;
import mcp.mobius.waila.api.neo.NeoFluidData;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@Mixin(targets = "mcp.mobius.waila.plugin.neo.provider.FluidCapabilityProvider", remap = false)
public class FluidCapabilityProviderMixin implements IDataProvider<BlockEntity> {

    @Nullable
    private BlockCapabilityCache<IFluidHandler, @Nullable Direction> cache;

    /**
     * @author seancrain
     * @reason WTHIT on NeoForge has a bug where it will display the first block you look at's properties for all blocks. (WTHIT PR: #372)
     */
    @Overwrite(remap = false)
    public void appendData(IDataWriter data, IServerAccessor<BlockEntity> accessor, IPluginConfig config) {
        data.add(FluidData.TYPE, res -> {
            var world = accessor.getLevel();
            var target = accessor.getTarget();
            var pos = target.getBlockPos();

            if (cache == null || (cache.level() != world || !cache.pos().equals(pos))) {
                cache = BlockCapabilityCache.create(Capabilities.FluidHandler.BLOCK, world, pos, null);
            }

            var handler = cache.getCapability();
            if (handler == null) return;

            var size = handler.getTanks();
            var fluidData = NeoFluidData.of(size);

            for (var i = 0; i < size; i++) {
                fluidData.add(handler.getFluidInTank(i), handler.getTankCapacity(i));
            }

            res.add(fluidData);
        });
    }

}