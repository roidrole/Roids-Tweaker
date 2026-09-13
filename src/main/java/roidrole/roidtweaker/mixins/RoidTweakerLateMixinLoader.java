package roidrole.roidtweaker.mixins;

import net.minecraftforge.fml.common.Loader;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class RoidTweakerLateMixinLoader implements ILateMixinLoader {
    @Override
    public List<String> getMixinConfigs() {
        return Arrays.asList(
            "mixins.roidtweaker.buildcraft.json",
            "mixins.roidtweaker.cookingforblockheads.json",
            "mixins.roidtweaker.crafttweaker.json",
            "mixins.roidtweaker.erebus.json",
            "mixins.roidtweaker.immersiveengineering.json",
            "mixins.roidtweaker.jei.json",
            "mixins.roidtweaker.projecte.json",
            "mixins.roidtweaker.railcraft.json",
            "mixins.roidtweaker.thebetweenlands.json",
            "mixins.roidtweaker.thermalexpansion.json"
        );
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig) {
        return Loader.isModLoaded(mixinConfig.split("\\.")[2]);
    }
}
