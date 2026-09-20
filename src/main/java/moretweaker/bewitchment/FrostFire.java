package moretweaker.bewitchment;

import com.bewitchment.api.registry.FrostfireRecipe;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.ListAdd;
import roidrole.roidtweaker.utils.actions.ListClear;
import roidrole.roidtweaker.utils.actions.ListRemove;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.function.Predicate;

@ZenClass("moretweaker.bewitchment.FrostFire")
@ZenRegister
@ModOnly("bewitchment")
@SuppressWarnings("unused")
public class FrostFire {
	@ZenMethod
	public static void addRecipe(IItemStack output, IIngredient input) {
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		final Ingredient in = CraftTweakerMC.getIngredient(input);
		FrostfireRecipe recipe = new FrostfireRecipe(Utils.newRL(), in, out);
		DeferredLoader.postInit(ListAdd.from(FrostfireRecipe.class, recipe));
	}

	@ZenMethod
	public static void removeRecipe(IIngredient output) {
		final Predicate<ItemStack> out = Utils.getStackMatcher(output);
		DeferredLoader.postInit(ListRemove.from(FrostfireRecipe.class, (r) -> out.test(r.output)));
	}

	@ZenMethod
	public static void removeAll() {
		DeferredLoader.postInit(ListClear.from(FrostfireRecipe.class, "witches frostfire"));
	}
}
