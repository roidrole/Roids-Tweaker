package moretweaker.aoa;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import moretweaker.MoreTweaker;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.tslat.aoa3.crafting.recipes.InfusionTableRecipe;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.ListAdd;
import roidrole.roidtweaker.utils.actions.ListRemove;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.function.Predicate;

@ZenClass("moretweaker.aoa.InfusionTable")
@ZenRegister
@ModOnly("aoa3")
@SuppressWarnings("unused")
public class InfusionTable {
	@ZenMethod
	public static void addRecipe(IItemStack output, IItemStack mainInput, IIngredient[] inputs, @Optional(valueLong = 1L) final int infusionLevel, @Optional final int xpMin, @Optional final int xpMax) {
		if (inputs.length == 0 || inputs.length > 9) {
			CraftTweakerAPI.logError("No or more than 9 inputs given for InfusionTable recipe");
		}

		final NonNullList<Ingredient> in2 = NonNullList.create();
		for(IIngredient ingredient : inputs) {
			in2.add(CraftTweakerMC.getIngredient(ingredient));
		}

		InfusionTableRecipe recipe = new InfusionTableRecipe(
			"",
			CraftTweakerMC.getItemStack(output),
			CraftTweakerMC.getItemStack(mainInput),
			in2,
			infusionLevel,
			xpMin,
			xpMax
		);

		DeferredLoader.postInit(ListAdd.from(IRecipe.class, recipe));
	}

	@ZenMethod
	public static void addRecipe(final String enchantmentId, final int enchantmentLevel, IIngredient[] inputs, @Optional(valueLong = 1L) final int infusionLevel, @Optional final int xpMin, @Optional final int xpMax) {
		if (inputs.length == 0 || inputs.length > 9) {
			CraftTweakerAPI.logError("No or more than 9 inputs given for InfusionTable recipe");
		}

		final NonNullList<Ingredient> in2 = NonNullList.create();

		for(IIngredient ingredient : inputs) {
			in2.add(CraftTweakerMC.getIngredient(ingredient));
		}

		InfusionTableRecipe recipe = new InfusionTableRecipe(
			"",
			Enchantment.getEnchantmentByLocation(enchantmentId),
			enchantmentLevel,
			in2,
			infusionLevel,
			xpMin,
			xpMax
		);

		DeferredLoader.postInit(ListAdd.from(IRecipe.class, recipe));
	}

	@ZenMethod
	public static void removeRecipe(IIngredient output) {
		Predicate<ItemStack> outputMatcher = Utils.getStackMatcher(output);
		DeferredLoader.postInit(ListRemove.from(IRecipe.class, recipe ->
			recipe instanceof InfusionTableRecipe &&
			!((InfusionTableRecipe)recipe).isEnchanting() &&
			outputMatcher.test(recipe.getRecipeOutput())
		));
	}

	@ZenMethod
	public static void removeRecipe(final String enchantmentId) {
		Enchantment ench = Enchantment.getEnchantmentByLocation(enchantmentId);
		DeferredLoader.postInit(ListRemove.from(IRecipe.class, recipe ->
				recipe instanceof InfusionTableRecipe &&
					((InfusionTableRecipe) recipe).isEnchanting() &&
					((InfusionTableRecipe) recipe).getEnchantment() != null &&
					((InfusionTableRecipe) recipe).getEnchantment().enchantment == ench)
		);
	}

	@ZenMethod
	public static void removeAll() {
		DeferredLoader.postInit(() -> {
			CraftTweakerAPI.logInfo("Removing all AOA InfusionTable recipes");
			MoreTweaker.forgeRemove(IRecipe.class, (r) -> r instanceof InfusionTableRecipe);
		});
	}
}
