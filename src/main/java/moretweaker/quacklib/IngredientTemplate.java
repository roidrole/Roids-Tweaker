package moretweaker.quacklib;

import crafttweaker.api.item.IIngredient;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.utils.Utils;
import therealfarfetchd.quacklib.common.api.recipe.ItemTemplate;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

public class IngredientTemplate implements ItemTemplate {
	private final Predicate<ItemStack> matcher;
	private final ItemStack outStack;


	public IngredientTemplate(IIngredient ingredient) {
		this.matcher = Utils.getStackMatcher(ingredient);
		this.outStack = Utils.getFirstStack(ingredient);
	}

	public boolean isSameItem(@Nonnull ItemStack itemStack) {
		return this.matcher.test(itemStack);
	}

	public boolean isValid() {
		return !outStack.isEmpty();
	}

	public @Nonnull ItemStack makeStack() {
		return this.outStack.copy();
	}
}
