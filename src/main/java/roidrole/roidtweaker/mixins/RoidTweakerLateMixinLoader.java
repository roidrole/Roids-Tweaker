package roidrole.roidtweaker.mixins;

import net.minecraftforge.fml.common.Loader;
import roidrole.roidtweaker.RoidTweakerConfig;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class RoidTweakerLateMixinLoader implements ILateMixinLoader {
    @Override
    public List<String> getMixinConfigs() {
        List<String> configs = Arrays.asList(
            null,
            "mixins.roidtweaker.bedrockcraft.json",
            "mixins.roidtweaker.bloodarsenal.json",
            "mixins.roidtweaker.buildcraft.json",
            "mixins.roidtweaker.cookingforblockheads.json",
            "mixins.roidtweaker.crafttweaker.json",
            "mixins.roidtweaker.draconicevolution.json",
            "mixins.roidtweaker.erebus.json",
            "mixins.roidtweaker.fossils.json",
            "mixins.roidtweaker.galacticraft.json",
            "mixins.roidtweaker.immersiveengineering.json",
            "mixins.roidtweaker.jei.json",
            "mixins.roidtweaker.projecte.json",
            "mixins.roidtweaker.railcraft.json",
            "mixins.roidtweaker.thebetweenlands.json",
            "mixins.roidtweaker.thermalexpansion.json"
        );
        if(RoidTweakerConfig.mixinCategory.allowCustomMobSwabEgg){
            configs.set(0, "mixins.roidtweaker.custom_swab_mob.json");
        }

        return configs;
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig) {
        if(mixinConfig == null){
            return false;
        }
        return Loader.isModLoaded(mixinConfig.split("\\.")[2]);
    }
}
