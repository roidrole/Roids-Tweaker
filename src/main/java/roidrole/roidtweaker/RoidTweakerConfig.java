package roidrole.roidtweaker;

import com.cleanroommc.configanytime.ConfigAnytime;
import net.minecraftforge.common.config.Config;

@Config(modid = Tags.MOD_ID, category = "")
public class RoidTweakerConfig {
    @Config.Comment("Configs for enabling mixins from the mod. They can cause incompatibilities, so you should only enable the ones you are using.")
    @Config.Name("Mixin Category")
    public static MixinCategory mixinCategory = new MixinCategory();
    public static class MixinCategory {

        @Config.Name("Villager Category")
        public final VillagerCategory villagerCategory = new VillagerCategory();
        public static class VillagerCategory {
            public boolean allowDisablingCareers = false;

            public boolean allowMetaWildcards = false;

            @Config.Comment("Allows setting a custom function to set a profession to a villager. \nRequired for removing vanilla professions")
            public boolean allowCustomProfessionSetter = false;
        }

        @Config.Comment("Also required for modded villager profession disabling")
        public boolean allowRemovingRegistries = false;

        @Config.Comment("Only compatible with Bubbles!\nAllows making any existing item into a bauble")
        public boolean allowCustomBaubles = false;
    }

    @Config.Name("Mekanism Category")
    public static MekanismCategory mekanismCategory = new MekanismCategory();
    public static class MekanismCategory {
        @Config.Comment(
            "The default texture domain to use if unspecified.\n" +
            "Textures are always in the format domain:path.\n" +
            "Default is mekatweaker for backwards-compatibility. I recommend changing it to contenttweaker to allow using the same folders as contenttweaker"
        )
        public String defaultDomain = "mekatweaker";
    }



    static {
        ConfigAnytime.register(RoidTweakerConfig.class);
    }
}
