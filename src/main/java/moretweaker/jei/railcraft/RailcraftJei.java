package moretweaker.jei.railcraft;

import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mods.railcraft.common.blocks.RailcraftBlocks;
import moretweaker.jei.MoreJeiPlugin;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.mixins.railcraft.AccFluidFuelManager;

import java.util.Map;

public class RailcraftJei implements MoreJeiPlugin {
	public void register(IRecipeCategoryRegistration registry) {
		registry.addRecipeCategories(new FluidFuelCategory(registry.getJeiHelpers().getGuiHelper()));
	}

	public void register(IModRegistry registry) {
		registry.addRecipeCatalyst(new ItemStack(RailcraftBlocks.BOILER_FIREBOX_FLUID.block()), "moretweaker.railcraft.fluidfuel.recipes");
		registry.handleRecipes(Map.Entry.class, FluidFuelWrapper::new, "moretweaker.railcraft.fluidfuel.recipes");

		registry.addRecipes(AccFluidFuelManager.getBoilerFuel().entrySet(), "moretweaker.railcraft.fluidfuel.recipes");

	}
}
