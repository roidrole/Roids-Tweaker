package roidrole.roidtweaker.mixins;

import net.minecraftforge.fml.common.Loader;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Arrays;
import java.util.List;

public class RoidTweakerLateMixinLoader implements ILateMixinLoader {
    @Override
    public List<String> getMixinConfigs() {
        return Arrays.asList(
            "mixins.roidtweaker.projecte.json",
            "mixins.roidtweaker.immersiveengineering.json",
            "mixins.roidtweaker.thermalexpansion.json",
            "mixins.roidtweaker.crafttweaker.json"
        );
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig) {
        return Loader.isModLoaded(mixinConfig.split("\\.")[2]);
    }
}
