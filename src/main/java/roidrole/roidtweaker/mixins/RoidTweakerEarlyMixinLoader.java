package roidrole.roidtweaker.mixins;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import roidrole.roidtweaker.RoidTweakerConfig;
import zone.rong.mixinbooter.IEarlyMixinLoader;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RoidTweakerEarlyMixinLoader implements IFMLLoadingPlugin, IEarlyMixinLoader {
    @Override
    public List<String> getMixinConfigs() {
        return Arrays.asList(
            "mixins.roidtweaker.firstjoindetect.json",
            "mixins.roidtweaker.registry.disable.json",
            "mixins.roidtweaker.villager.accessor.json",
            "mixins.roidtweaker.villager.metawildcards.json",
            "mixins.roidtweaker.villager.careerdisable.json",
            "mixins.roidtweaker.villager.customprofessionsetter.json"
        );
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig) {
        switch (mixinConfig){
            case "mixins.roidtweaker.firstjoindetect.json" : return RoidTweakerConfig.mixinCategory.allowPlayerFirstJoinEvent;
            case "mixins.roidtweaker.registry.disable.json" : return RoidTweakerConfig.mixinCategory.allowRemovingRegistries;
            case "mixins.roidtweaker.villager.metawildcards.json" : return RoidTweakerConfig.mixinCategory.villagerCategory.allowMetaWildcards;
            case "mixins.roidtweaker.villager.careerdisable.json" : return RoidTweakerConfig.mixinCategory.villagerCategory.allowDisablingCareers;
            case "mixins.roidtweaker.villager.customprofessionsetter.json" : return RoidTweakerConfig.mixinCategory.villagerCategory.allowCustomProfessionSetter;
            default : return true;
        }
    }

    @Override
    public String[] getASMTransformerClass() {
        return null;
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
