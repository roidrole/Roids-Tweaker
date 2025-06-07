package xyz.tcreopargh.ctintegration;

import net.minecraftforge.fml.common.Loader;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Collections;
import java.util.List;

public class CTILateMixinLoader implements ILateMixinLoader {
    @Override
    public List<String> getMixinConfigs() {
        return Collections.singletonList("mixins.ctintegration.projecte.json");
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig) {
        return Loader.isModLoaded("projecte");
    }
}
