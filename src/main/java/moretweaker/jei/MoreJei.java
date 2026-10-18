package moretweaker.jei;

import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import moretweaker.jei.buildcraft.BuildCraftJei;
import moretweaker.jei.erebus.ErebusJei;
import moretweaker.jei.infinitycraft.InfinityJei;
import moretweaker.jei.railcraft.RailcraftJei;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@JEIPlugin
public class MoreJei implements IModPlugin {
	private static IJeiHelpers helpers = null;
	private static final List<MoreJeiPlugin> plugins = new ArrayList<>(4);
	public static final ResourceLocation GUI_VANILLA = new ResourceLocation("jei", "textures/gui/gui_vanilla.png");

	public MoreJei() {
		register("buildcraftsilicon", BuildCraftJei::new);
		register("erebus", ErebusJei::new);
		register("railcraft", RailcraftJei::new);
		register("infinitycraft", InfinityJei::new);
	}

	private static void register(String modid, Supplier<MoreJeiPlugin> plugin) {
		if (Loader.isModLoaded(modid)) {
			plugins.add(plugin.get());
		}

	}

	public void register(IModRegistry registry) {
		helpers = registry.getJeiHelpers();

		for(MoreJeiPlugin plugin : plugins) {
			plugin.register(registry);
		}
	}

	public void registerCategories(IRecipeCategoryRegistration registry) {
		helpers = registry.getJeiHelpers();

		for(MoreJeiPlugin plugin : plugins) {
			plugin.register(registry);
		}
		plugins.clear();
	}

	public static IJeiHelpers getHelpers() {
		if (helpers == null) {
			throw new IllegalStateException("JeiHelpers not yet available.");
		} else {
			return helpers;
		}
	}
}
