package roidrole.roidtweaker.utils.predicates;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

public class IIngredientMatching implements Predicate<ItemStack> {
	final IIngredient target;

	public IIngredientMatching(IIngredient target){
		this.target = target;
	}

	@Override
	public boolean test(ItemStack itemStack) {
		if(itemStack == null || itemStack.isEmpty()){
			return false;
		}
		return target.matches(CraftTweakerMC.getIItemStackForMatching(itemStack));
	}

	@Override
	public int hashCode() {
		return target.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof IIngredientMatching)){
			return false;
		}
		return target.equals(((IIngredientMatching) obj).target);
	}
}
