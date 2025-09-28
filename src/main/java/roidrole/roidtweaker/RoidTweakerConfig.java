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

            @Config.Comment("Required for removing vanilla professions")
            public boolean replaceProfessionSetter = false;
        }

        @Config.Comment("Also required for modded villager profession disabling")
        public boolean allowRemovingRegistries = false;
    }

    @Config.Comment("Configs for event handlers from the mod. They each add a little performance loss, so here is your option to disable them")
    @Config.Name("Event Category")
    public static EventCategory eventCategory = new EventCategory();
    public static class EventCategory {
        @Config.Comment("Allows making any existing item into a bauble.\nWill crash on startup if not using bubbles!")
        public boolean allowCustomBaubles = false;

        @Config.Comment("Allows modifying anvil recipes")
        public boolean allowAnvilRecipes = false;

        @Config.Comment("Allows listening to GameStages events")
        public boolean allowGameStagesEvents = false;
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
