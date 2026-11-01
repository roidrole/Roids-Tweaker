package moretweaker.enderfuge;

import com.themastergeneral.enderfuge.common.processing.EnderfugeFuel;
import com.themastergeneral.enderfuge.common.processing.EnderfugeRecipes;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.ListClear;
import roidrole.roidtweaker.utils.actions.ListRemove;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.function.Predicate;

@ZenClass("moretweaker.enderfuge.EnderFuge")
@ZenRegister
@ModOnly("enderfuge")
@SuppressWarnings("unused")
public class EnderFuge {
	@ZenMethod
	public static void addRecipe(IItemStack output, IIngredient input) {
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		final ItemStack[] in = CraftTweakerMC.getExamples(input);
		for(ItemStack inPart : in) {
			EnderfugeRecipes.instance().addSmeltingRecipe(inPart, out, 0.0F);
		}
	}

	@ZenMethod
	public static void removeRecipe(IIngredient output) {
		final Predicate<ItemStack> outputFilter = Utils.getStackMatcher(output);
		ListRemove.remove(EnderfugeRecipes.instance().getSmeltingList().values(), outputFilter);
	}

	@ZenMethod
	public static void addFuel(IIngredient fuel, @Optional(valueLong = 400L) final int burnTicks) {
		final ItemStack[] in = CraftTweakerMC.getExamples(fuel);
		for(ItemStack inPart : in) {
			EnderfugeFuel.instance().addEnderfugeFuel(inPart, burnTicks);
		}
	}

	@ZenMethod
	public static void removeFuel(IIngredient fuel) {
		final Predicate<ItemStack> out = Utils.getStackMatcher(fuel);
		ListRemove.remove(EnderfugeFuel.instance().getSmeltingList().keySet(), out);
	}

	@ZenMethod
	public static void removeAllRecipes() {
		ListClear.clear(EnderfugeRecipes.instance().getSmeltingList(), "EnderFuge");
	}

	@ZenMethod
	public static void removeAllFuels() {
		ListClear.clear(EnderfugeFuel.instance().getSmeltingList(), "EnderFuge fuel");
	}
}
