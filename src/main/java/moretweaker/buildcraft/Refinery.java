package moretweaker.buildcraft;

import buildcraft.api.mj.MjAPI;
import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import buildcraft.lib.recipe.RefineryRecipeRegistry;
import com.google.common.base.Predicates;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import roidrole.roidtweaker.utils.actions.ListClear;
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
		BuildcraftRecipeRegistry.refineryRecipes.getDistillationRegistry().removeRecipes((r) -> fluidIn == r.in().getFluid());
	}

	@ZenMethod
	public static void removeAll() {
		BuildcraftRecipeRegistry.refineryRecipes.getDistillationRegistry().removeRecipes(Predicates.alwaysTrue());
		ListClear.logMessage("BC refinery distillation");
	}
}
