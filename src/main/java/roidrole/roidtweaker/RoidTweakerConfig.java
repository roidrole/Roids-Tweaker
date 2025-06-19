package roidrole.roidtweaker;

import com.cleanroommc.configanytime.ConfigAnytime;
import net.minecraftforge.common.config.Config;

@Config(modid = Tags.MOD_ID, category = "")
public class RoidTweakerConfig {
    @Config.Comment("Configs for enabling mixins from the mod. They can cause incompatibilities, so you should only enable the ones you are using.")
    public static MixinCategory mixinCategory = new MixinCategory();
    public static class MixinCategory {

        public final VillagerCategory villagerCategory = new VillagerCategory();
        public static class VillagerCategory {
            public boolean allowDisablingCareers = false;

            public boolean allowMetaWildcards = false;

            @Config.Comment("Allows setting a custom function to set a profession to a villager. \nRequired for removing vanilla professions")
            public boolean allowCustomProfessionSetter = false;
        }

        @Config.Comment("Also required for modded profession disabling")
        public boolean allowRemovingRegistries = false;
    }


    static {
        ConfigAnytime.register(RoidTweakerConfig.class);
    }
}
