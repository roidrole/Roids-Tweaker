package moretweaker.buildcraft;

import buildcraft.api.mj.MjAPI;
import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import buildcraft.lib.recipe.RefineryRecipeRegistry;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import roidrole.roidtweaker.utils.actions.ListClear;
import roidrole.roidtweaker.utils.actions.ListRemove;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("moretweaker.buildcraft.Refinery")
@ZenRegister
@ModOnly("buildcraftfactory")
@SuppressWarnings("unused")
public class Refinery {
	@ZenMethod
	public static void addDistilling(ILiquidStack input, ILiquidStack output, ILiquidStack gasOutput, int energy) {
		FluidStack in = CraftTweakerMC.getLiquidStack(input);
		FluidStack out = CraftTweakerMC.getLiquidStack(output);
		FluidStack gasOut = CraftTweakerMC.getLiquidStack(gasOutput);
		long mj = (long)energy * MjAPI.MJ;
		final RefineryRecipeRegistry.DistillationRecipe recipe = new RefineryRecipeRegistry.DistillationRecipe(mj, in, gasOut, out);

		BuildcraftRecipeRegistry.refineryRecipes.getDistillationRegistry().addRecipe(recipe);
	}

	@ZenMethod
	public static void removeDistilling(ILiquidStack input) {
		final FluidStack in = CraftTweakerMC.getLiquidStack(input);
		if(in == null){
			CraftTweakerAPI.logError("Attempting to remove distilling recipe for null input");
			return;
		}
		final Fluid fluidIn = in.getFluid();
		ListRemove.remove(
			BuildcraftRecipeRegistry.refineryRecipes.getDistillationRegistry().getAllRecipes(),
			recipe -> fluidIn == recipe.in().getFluid()
		);
	}

	@ZenMethod
	public static void removeAll() {
		ListClear.clear(
			BuildcraftRecipeRegistry.refineryRecipes.getDistillationRegistry().getAllRecipes(),
			"BC refinery distillation"
		);
	}
}
