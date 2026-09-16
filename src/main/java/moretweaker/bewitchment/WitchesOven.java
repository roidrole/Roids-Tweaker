package moretweaker.bewitchment;

import com.bewitchment.api.registry.OvenRecipe;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import moretweaker.MoreTweaker;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.RegistryClear;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.function.Predicate;

@ZenClass("moretweaker.bewitchment.WitchesOven")
@ZenRegister
@ModOnly("bewitchment")
@SuppressWarnings("unused")
public class WitchesOven {
	@ZenMethod
	public static void addRecipe(IIngredient input, IItemStack output, IItemStack byproduct, @Optional(valueDouble = (double)1.0F) final float chance, @Optional final boolean requiresJar) {
		final ItemStack[] in = CraftTweakerMC.getExamples(input);
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		final ItemStack by = CraftTweakerMC.getItemStack(byproduct);
		DeferredLoader.postInit(() -> {
			for(ItemStack stack : in) {
				MoreTweaker.forgeAdd(OvenRecipe.class, new OvenRecipe(MoreTweaker.newRL(), stack, out, by, chance, requiresJar));
			}
		});
	}

	@ZenMethod
	public static void removeRecipe(IIngredient output) {
		final Predicate<ItemStack> out = Utils.getStackMatcher(output);
		DeferredLoader.postInit(MoreTweaker.forgeRemoveLambda(OvenRecipe.class, recipe -> out.test(recipe.output)));
	}

	@ZenMethod
	public static void removeAll() {
		DeferredLoader.postInit(new RegistryClear(OvenRecipe.class, "witches oven"));
	}
}
