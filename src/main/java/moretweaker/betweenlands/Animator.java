package moretweaker.betweenlands;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.ListClear;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import thebetweenlands.api.recipes.IAnimatorRecipe;
import thebetweenlands.common.BetweenlandsAPI;
import thebetweenlands.common.recipe.misc.AnimatorRecipe;

import java.util.function.Predicate;

@ZenClass("moretweaker.betweenlands.Animator")
@ZenRegister
@ModOnly("thebetweenlands")
@SuppressWarnings("unused")
public class Animator {
	@ZenMethod
	public static void addRecipe(IItemStack input, final int fuel, final int life, IItemStack output) {
		final ItemStack in = CraftTweakerMC.getItemStack(input);
		final ItemStack out = CraftTweakerMC.getItemStack(output);

		AnimatorRecipe recipe = new AnimatorRecipe(in, fuel, life, out);
		BetweenlandsAPI.getInstance().registerAnimatorRecipe(recipe);
	}

	@ZenMethod
	public static void addRecipe(IItemStack input, final int fuel, final int life, String entity) {
		final ItemStack in = CraftTweakerMC.getItemStack(input);
		final ResourceLocation entityLocation = new ResourceLocation(entity);
		final Class<? extends Entity> entityClass = EntityList.getClass(entityLocation);
		if (entityClass == null) {
			CraftTweakerAPI.logError("No entity found for id: " + entity);
			return;
		}

		AnimatorRecipe recipe = new AnimatorRecipe(in, fuel, life, entityClass);
		recipe.setRenderEntity(entityLocation);
		BetweenlandsAPI.getInstance().registerAnimatorRecipe(recipe);
	}

	@ZenMethod
	public static void removeRecipe(IIngredient output) {
		Predicate<ItemStack> outputMatcher = Utils.getStackMatcher(output);
		AnimatorRecipe.getRecipes().removeIf(recipe ->
			outputMatcher.test(recipe.getResult(Animator.guessInput(recipe)))
		);
	}

	@ZenMethod
	public static void removeRecipe(String entityOutput) {
		ResourceLocation entityLocation = new ResourceLocation(entityOutput);
		final Class<? extends Entity> entityClass = EntityList.getClass(entityLocation);
		if (entityClass == null) {
			CraftTweakerAPI.logError("No entity found for id: " + entityOutput);
			return;
		}

		AnimatorRecipe.getRecipes().removeIf(
			recipe -> entityClass == recipe.getSpawnEntityClass(Animator.guessInput(recipe))
		);
	}

	@ZenMethod
	public static void removeAll() {
		ListClear.clear(AnimatorRecipe.getRecipes(), "Animator");
	}

	private static ItemStack guessInput(IAnimatorRecipe recipe) {
		return recipe instanceof AnimatorRecipe ? ((AnimatorRecipe)recipe).getInput() : ItemStack.EMPTY;
	}
}
