package moretweaker.jei.buildcraft;

import buildcraft.api.mj.MjAPI;
import buildcraft.api.recipes.IngredientStack;
import buildcraft.lib.recipe.IntegrationRecipeBasic;
import com.google.common.collect.ImmutableList;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.mixins.buildcraft.IntegrationRecipeBasicAccessor;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntegrationWrapper implements IRecipeWrapper {
	private final List<List<ItemStack>> inputs;
	private final ItemStack output;
	private final String mjString;

	public IntegrationWrapper(IntegrationRecipeBasic recipe) {
		this.output = ((IntegrationRecipeBasicAccessor)recipe).getOutput();
		List<ItemStack> catalyst = Arrays.asList(recipe.getCenterStack().ingredient.matchingStacks);
		ImmutableList<IngredientStack> inputs = recipe.getRequirements(this.output);
		this.inputs = new ArrayList<>(inputs.size() + 1);
		this.inputs.add(catalyst);
		for (IngredientStack ingredient : inputs){
			this.inputs.add(Arrays.asList(ingredient.ingredient.matchingStacks));
		}
		this.mjString = recipe.getRequiredMicroJoules(this.output) / MjAPI.MJ + " MJ";
	}

	public void getIngredients(@Nonnull IIngredients ii) {
		ii.setInputLists(VanillaTypes.ITEM, inputs);
		ii.setOutput(VanillaTypes.ITEM, this.output);
	}

	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		minecraft.fontRenderer.drawString(this.mjString, 74, 56, Color.gray.getRGB());
	}
}
