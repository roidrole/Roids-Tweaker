package moretweaker.bewitchment;

import com.bewitchment.api.registry.Ritual;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import moretweaker.MoreTweaker;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.RegistryClear;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenProperty;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@ZenClass("moretweaker.bewitchment.WitchesRitual")
@ZenRegister
@ModOnly("bewitchment")
@SuppressWarnings("unused")
public class WitchesRitual {
	@ZenProperty
	public static final int NONE = -1;
	@ZenProperty
	public static final int GOLDEN = 0;
	@ZenProperty
	public static final int RITUAL = 1;
	@ZenProperty
	public static final int FIERY = 2;
	@ZenProperty
	public static final int PHASING = 3;
	@ZenProperty
	public static final int ANY = 4;

	@ZenMethod
	public static void addRecipe(String name, IItemStack[] outputs, IIngredient[] inputs, final String entityOutput, final String entityInput, final int power, final int ringSmall, final int ringMedium, final int ringLarge) {
		if (outputs.length > 5) {
			CraftTweakerAPI.logError("Witches rituals may not have more than 5 outputs.");
		}

		if (inputs.length > 10) {
			CraftTweakerAPI.logError("Witches rituals may not have more than 10 inputs.");
		}

		if (outputs.length <= 5 && inputs.length <= 10) {
			final List<ItemStack> out = Arrays.asList(CraftTweakerMC.getItemStacks(outputs));
			final List<Ingredient> in = Arrays.asList(Utils.getIngredients(inputs));
			final ResourceLocation rl = MoreTweaker.newRL();

			final MoreRitual ritual = new MoreRitual(rl, in, out, entityInput, entityOutput, power, ringSmall, ringMedium, ringLarge);
			MoreTweaker.injectTranslation("ritual." + rl.getNamespace() + "." + rl.getPath(), name);

			DeferredLoader.postInit(MoreTweaker.forgeAddLambda(Ritual.class, ritual));
		}
	}

	@ZenMethod
	public static void removeByOutput(IIngredient output) {
		final Predicate<ItemStack> out = Utils.getStackMatcher(output);
		DeferredLoader.postInit(MoreTweaker.forgeRemoveLambda(Ritual.class, recipe -> Utils.anyMatch(out, recipe.output)));
	}

	@ZenMethod
	public static void removeByInput(IIngredient input) {
		final Predicate<ItemStack> inputFilter = Utils.getStackMatcher(input);
		DeferredLoader.postInit(MoreTweaker.forgeRemoveLambda(Ritual.class, recipe ->
			Utils.anyMatchIngredient(inputFilter, recipe.input)
		));
	}

	@ZenMethod
	public static void removeAll() {
		DeferredLoader.postInit(new RegistryClear(Ritual.class, "witches ritual"));
	}
}
