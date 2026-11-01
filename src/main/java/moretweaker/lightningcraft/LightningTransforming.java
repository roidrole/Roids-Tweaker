package moretweaker.lightningcraft;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.ListClear;
import roidrole.roidtweaker.utils.actions.ListRemove;
import sblectric.lightningcraft.recipes.LightningTransformRecipes;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.function.Predicate;

@ZenClass("moretweaker.lightningcraft.LightningTransforming")
@ZenRegister
@ModOnly("lightningcraft")
@SuppressWarnings("unused")
public class LightningTransforming {
	@ZenMethod
	public static void add(IItemStack output, IItemStack[] inputs) {
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		final NonNullList<ItemStack> in = NonNullList.create();
		for (IItemStack stack : inputs){
			in.add(CraftTweakerMC.getItemStack(stack));
		}
		LightningTransformRecipes.instance().addRecipe(out, in);
	}

	@ZenMethod
	public static void remove(IIngredient output){
		final Predicate<ItemStack> out = Utils.getStackMatcher(output);
		ListRemove.removeValues(LightningTransformRecipes.instance().getRecipeList(), out);
	}

	@ZenMethod
	public static void removeAll(){
		ListClear.clear(
			LightningTransformRecipes.instance().getRecipeList(),
			"lightning transformation"
		);
	}
}
