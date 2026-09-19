package moretweaker.infinitycraft;

import com.rowan662.infinitycraft.machines.CompressorRecipes;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.ListClear;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.function.Predicate;

@ZenClass("moretweaker.infinitycraft.Compressor")
@ZenRegister
@ModOnly("infinitycraft")
@SuppressWarnings("unused")
public class Compressor {
	@ZenMethod
	public static void addRecipe(IIngredient input, IItemStack output, @Optional final int experience) {
		final ItemStack[] in = CraftTweakerMC.getExamples(input);
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		for(ItemStack stack : in) {
			CompressorRecipes.instance().addCompressingRecipe(stack, out, (float)experience);
		}
	}

	@ZenMethod
	public static void removeRecipe(IIngredient output) {
		final Predicate<ItemStack> out = Utils.getStackMatcher(output);
		CompressorRecipes.instance().getCompressingList().values().removeIf(out);
	}

	@ZenMethod
	public static void removeAll() {
		CompressorRecipes.instance().getCompressingList().clear();
		ListClear.logMessage("infinitycraft Compressor");
	}
}
