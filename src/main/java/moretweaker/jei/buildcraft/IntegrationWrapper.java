package moretweaker.jei.buildcraft;

import buildcraft.api.mj.MjAPI;
import buildcraft.api.recipes.IngredientStack;
import buildcraft.lib.recipe.IntegrationRecipeBasic;
import com.google.common.collect.ImmutableList;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import moretweaker.CraftingPart;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.mixins.buildcraft.IntegrationRecipeBasicAccessor;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class IntegrationWrapper implements IRecipeWrapper {
	private final CraftingPart catalyst;
	private final CraftingPart[] inputs;
	private final ItemStack output;
	private final String mjString;

	public IntegrationWrapper(IntegrationRecipeBasic recipe) {
		this.output = ((IntegrationRecipeBasicAccessor)recipe).getOutput();
		this.catalyst = this.toCraftingPart(recipe.getCenterStack());
		ImmutableList<IngredientStack> inputs = recipe.getRequirements(this.output);
		this.inputs = this.toCraftingParts(inputs.toArray(new IngredientStack[0]));
		this.mjString = recipe.getRequiredMicroJoules(this.output) / MjAPI.MJ + " MJ";
	}

	public void getIngredients(@Nonnull IIngredients ii) {
		List<List<ItemStack>> in = new ArrayList<>();
		in.add(this.catalyst.toStackList());

		for(int i = 0; i < 8; ++i) {
			if (i < this.inputs.length) {
				in.add(this.inputs[i].toStackList());
			} else {
				in.add(ImmutableList.of(ItemStack.EMPTY));
			}
		}

		ii.setInputLists(VanillaTypes.ITEM, in);
		ii.setOutput(VanillaTypes.ITEM, this.output);
	}

	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		minecraft.fontRenderer.drawString(this.mjString, 74, 56, Color.gray.getRGB());
	}

	private CraftingPart toCraftingPart(IngredientStack stack) {
		return new CraftingPart(stack.ingredient, stack.count);
	}

	private CraftingPart[] toCraftingParts(IngredientStack[] stacks) {
		CraftingPart[] parts = new CraftingPart[stacks.length];

		for(int i = 0; i < stacks.length; ++i) {
			parts[i] = this.toCraftingPart(stacks[i]);
		}

		return parts;
	}
}
