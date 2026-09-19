package moretweaker.jei;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import moretweaker.jei.buildcraft.BuildCraftJei;
import moretweaker.jei.erebus.ErebusJei;
import moretweaker.jei.infinitycraft.InfinityJei;
import moretweaker.jei.railcraft.RailcraftJei;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import roidrole.roidtweaker.utils.DeferredLoader;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Supplier;

@JEIPlugin
public class MoreJei implements IModPlugin {
	private static IJeiRuntime runtime = null;
	private static IJeiHelpers helpers = null;
	private static final List<MoreJeiPlugin> plugins = new LinkedList<>();
	public static final ResourceLocation GUI_VANILLA = new ResourceLocation("jei", "textures/gui/gui_vanilla.png");
	private static final Queue<IAction> runtimeQueue = new LinkedList<>();

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

	}

	public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime) {
		runtime = jeiRuntime;

		DeferredLoader.jeiRuntime(runtime);

		for(IAction action : runtimeQueue) {
			CraftTweakerAPI.apply(action);
		}

		runtimeQueue.clear();
	}

	public static void run(Consumer<IJeiRuntime> action) {
		if (runtime != null) {
			action.accept(runtime);
		}

	}

	public static IJeiHelpers getHelpers() {
		if (helpers == null) {
			throw new IllegalStateException("JeiHelpers not yet available.");
		} else {
			return helpers;
		}
	}

	public static void queueJeiRuntimeAction(IAction action) {
		runtimeQueue.add(action);
	}
}
