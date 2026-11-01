package moretweaker.galacticraft;

import micdoodle8.mods.galacticraft.api.recipe.ShapelessOreRecipeGC;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Really just a wrapper around IRecipe that delegates matches to the IRecipe.
 * Exists solely to bypass an instanceOf in galacticraft.
 */
public class RecipeGCRT extends ShapelessOreRecipeGC {
	private final IRecipe internal;

	public RecipeGCRT(IRecipe internal) {
		super(internal.getRecipeOutput(), Collections.EMPTY_LIST);
		this.input = null;
		this.internal = internal;
	}

	@Override
	public boolean matches(InventoryCrafting inventory, World world) {
		return internal.matches(inventory, world);
	}

	@Override
	public boolean canFit(int width, int height) {
		return internal.canFit(width, height);
	}

	@Override
	public @NotNull ItemStack getRecipeOutput() {
		return internal.getRecipeOutput();
	}

	@Override
	public @NotNull ItemStack getCraftingResult(InventoryCrafting var1) {
		return internal.getCraftingResult(var1);
	}

	@Override
	public boolean matches(List<ItemStack> var1) {
		return false;
	}

	@Override
	public ArrayList<Object> getInput() {
		return new ArrayList<>(internal.getIngredients());
	}

	@Override
	public @NotNull NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
		return internal.getRemainingItems(inv);
	}

	@Override
	public void replaceInput(ItemStack inputA, List<ItemStack> inputB) {
		//NO-OP
	}

	@Override
	public void replaceInput(ItemStack inputB) {
		//NO-OP
	}

	@Override
	public @NotNull NonNullList<Ingredient> getIngredients() {
		return internal.getIngredients();
	}

	@Override
	public boolean isDynamic() {
		return internal.isDynamic();
	}

	@Override
	public @NotNull String getGroup() {
		return internal.getGroup();
	}
}
