package com.nosiphus.nosifix.mixin.wthit;

import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.EnergyData;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@Mixin(targets = "mcp.mobius.waila.plugin.neo.provider.EnergyCapabilityProvider", remap = false)
public class EnergyCapabilityProviderMixin implements IDataProvider<BlockEntity> {

    @Nullable
    private BlockCapabilityCache<IEnergyStorage, @Nullable Direction> cache;

    /**
     * @author seancrain
     * @reason WTHIT on NeoForge has a bug where it will display the first block you look at's properties for all blocks. (WTHIT PR: #372)
     */
    @Overwrite(remap = false)
    public void appendData(IDataWriter data, IServerAccessor<BlockEntity> accessor, IPluginConfig config) {
        data.add(EnergyData.TYPE, res -> {
            var world = accessor.getLevel();
            var target = accessor.getTarget();
            var pos = target.getBlockPos();

            if (cache == null || (cache.level() != world || !cache.pos().equals(pos))) {
                cache = BlockCapabilityCache.create(Capabilities.EnergyStorage.BLOCK, world, pos, null);
            }

            var storage = cache.getCapability();
            if (storage == null) return;

            res.add(EnergyData.of(storage.getEnergyStored(), storage.getMaxEnergyStored()));
        });
    }

}
