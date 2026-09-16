package roidrole.roidtweaker.utils.predicates;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

import java.util.function.Predicate;

public class OutputMatching implements Predicate<IRecipe> {
	private final Predicate<ItemStack> predicate;

	public OutputMatching(Predicate<ItemStack> predicate) {
		this.predicate = predicate;
	}

	@Override
	public boolean test(IRecipe recipe) {
		return predicate.test(recipe.getRecipeOutput());
	}
}
