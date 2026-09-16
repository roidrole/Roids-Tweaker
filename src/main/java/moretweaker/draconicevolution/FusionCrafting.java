package moretweaker.draconicevolution;

import com.brandon3055.draconicevolution.api.fusioncrafting.FusionRecipeAPI;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.mixins.draconicevolution.AFusionRecipeRegistry;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.ListClear;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenProperty;

import java.util.function.Predicate;

@ZenClass("moretweaker.draconicevolution.FusionCrafting")
@ZenRegister
@ModOnly("draconicevolution")
@SuppressWarnings("unused")
public class FusionCrafting {
	@ZenProperty
	public static final int BASIC = 0;
	@ZenProperty
	public static final int WYVERN = 1;
	@ZenProperty
	public static final int DRACONIC = 2;
	@ZenProperty
	public static final int CHAOTIC = 3;

	@ZenMethod
	public static void add(IItemStack output, IItemStack catalyst, final int tier, long energyCost, IIngredient[] ingredients) {
		final FusionRecipeRT recipe = new FusionRecipeRT(
			output,
			catalyst,
			energyCost,
			tier,
			ingredients
		);

		FusionRecipeAPI.addRecipe(recipe);
	}

	@ZenMethod
	public static void remove(IIngredient catalyst) {
		final Predicate<ItemStack> catalystFilter = Utils.getStackMatcher(catalyst);
		((AFusionRecipeRegistry)FusionRecipeAPI.registry).getREGISTRY().removeIf(recipe -> catalystFilter.test(recipe.getRecipeCatalyst()));
	}

	@ZenMethod
	public static void removeAll() {
		CraftTweakerAPI.logInfo(ListClear.getMessage("fusion crafting"));
		((AFusionRecipeRegistry)FusionRecipeAPI.registry).getREGISTRY().clear();
	}
}
