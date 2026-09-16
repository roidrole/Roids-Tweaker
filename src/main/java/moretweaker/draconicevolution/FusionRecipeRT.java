package moretweaker.draconicevolution;

import com.brandon3055.draconicevolution.api.fusioncrafting.ICraftingInjector;
import com.brandon3055.draconicevolution.api.fusioncrafting.IFusionCraftingInventory;
import com.brandon3055.draconicevolution.api.fusioncrafting.IFusionRecipe;
import com.brandon3055.draconicevolution.lib.WTFException;
import com.brandon3055.draconicevolution.utils.LogHelper;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import roidrole.roidtweaker.utils.Utils;

import javax.annotation.Nullable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class FusionRecipeRT implements IFusionRecipe {
	protected ItemStack result;
	protected ItemStack catalystDisplay;
	protected Predicate<ItemStack> catalyst;
	protected List<Object> ingredientsDisplay;
	protected Ingredient[] ingredients;
	protected long energyCost;
	protected int craftingTier;

	public FusionRecipeRT(IItemStack result, IIngredient catalyst, long energyCost, int craftingTier, IIngredient[] ingredients) {
		this.result = CraftTweakerMC.getItemStack(result);
		this.catalystDisplay = Utils.getFirstStack(catalyst);
		this.catalystDisplay.setCount(catalyst.getAmount());
		this.catalyst = Utils.getStackMatcher(catalyst);
		this.ingredients = Utils.getIngredients(ingredients);
		this.ingredientsDisplay = Arrays.asList(Utils.getObjects(ingredients));
		this.energyCost = energyCost;
		this.craftingTier = craftingTier;
		long maxCost = Long.MAX_VALUE / (long)ingredients.length;

		if (energyCost > maxCost) {
			StringBuilder r = new StringBuilder("Result: " + result + "\nCatalyst: " + catalyst + "\nTier: " + craftingTier + "\nEnergy: (per ingredient) " + energyCost + ", (total) " + BigInteger.valueOf(energyCost).multiply(BigInteger.valueOf(ingredients.length)));

			for(Object i : ingredients) {
				r.append("\n").append(i);
			}

			LogHelper.error("An error occurred while registering the following recipe. \n" + r);
			throw new WTFException("Invalid Recipe: The combined energy cost of your recipe exceeds Long.MAX_VALUE (9223372036854775807) WTF are you doing?");
		}
	}

	public ItemStack getRecipeOutput(@Nullable ItemStack catalyst) {
		return this.result;
	}

	public boolean isRecipeCatalyst(ItemStack catalyst) {
		return this.catalyst.test(catalyst);
	}

	public ItemStack getRecipeCatalyst() {
		return this.catalystDisplay;
	}

	public int getRecipeTier() {
		return this.craftingTier;
	}

	public List<Object> getRecipeIngredients() {
		return ingredientsDisplay;
	}

	public boolean matches(IFusionCraftingInventory inventory, World world, BlockPos pos) {
		if(!this.catalyst.test(inventory.getStackInCore(0))){
			return false;
		}
		List<ICraftingInjector> pedestals = new ArrayList<>(inventory.getInjectors());
		pedestals.removeIf(pedestal -> pedestal.getStackInPedestal().isEmpty());
		for(Ingredient ingredient : this.ingredients) {
			boolean foundIngredient = false;

			for(ICraftingInjector pedestal : pedestals) {
				if (ingredient.test(pedestal.getStackInPedestal())) {
					foundIngredient = true;
					pedestals.remove(pedestal);
					break;
				}
			}

			if (!foundIngredient) {
				return false;
			}
		}

		return true;
	}

	public void craft(IFusionCraftingInventory inventory, World world, BlockPos pos) {
		if(!this.matches(inventory, world, pos)){
			return;
		}
		List<ICraftingInjector> pedestals = new ArrayList<>(inventory.getInjectors());
		pedestals.removeIf(pedestal -> pedestal.getStackInPedestal().isEmpty());

		for(Ingredient ingredient : this.ingredients) {
			for(ICraftingInjector pedestal : pedestals) {
				ItemStack stack = pedestal.getStackInPedestal();
				if (pedestal.getPedestalTier() >= this.craftingTier && ingredient.test(stack)) {
					if (stack.getItem().hasContainerItem(stack)) {
						stack = stack.getItem().getContainerItem(stack);
					} else {
						stack.shrink(1);
						if (stack.getCount() <= 0) {
							stack = ItemStack.EMPTY;
						}
					}

					pedestal.setStackInPedestal(stack);
					pedestals.remove(pedestal);
					break;
				}
			}
		}

		ItemStack catalyst = inventory.getStackInCore(0);
		ItemStack result = this.getRecipeOutput(catalyst);
		catalyst.shrink(this.catalystDisplay.getCount());
		if (catalyst.getCount() <= 0) {
			catalyst = ItemStack.EMPTY;
		}

		inventory.setStackInCore(0, catalyst);
		inventory.setStackInCore(1, result.copy());
	}

	public long getIngredientEnergyCost() {
		return this.energyCost;
	}

	public void onCraftingTick(IFusionCraftingInventory inventory, World world, BlockPos pos) {
		//No-op
	}

	public String canCraft(IFusionCraftingInventory inventory, World world, BlockPos pos) {
		if (!inventory.getStackInCore(1).isEmpty()) {
			return "outputObstructed";
		}
		for(ICraftingInjector pedestal : inventory.getInjectors()) {
			if (!pedestal.getStackInPedestal().isEmpty() && pedestal.getPedestalTier() < this.craftingTier) {
				return "tierLow";
			}
		}

		return "true";
	}

	public String toString() {
		return String.format("RoidTweakerFusionRecipe: {Result: %s, Catalyst: %s}", this.result, this.catalystDisplay);
	}
}
