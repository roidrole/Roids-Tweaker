package roidrole.roidtweaker.utils.predicates;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;
import java.util.function.Predicate;

public class OreMatching implements Predicate<ItemStack> {
	List<ItemStack> items;

	public OreMatching(String oreName){
		this.items = OreDictionary.getOres(oreName);
	}

	@Override
	public boolean test(ItemStack input) {
		if (input == null) {
			return false;
		}

		for (ItemStack target : items) {
			if (OreDictionary.itemMatches(target, input, false)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return items.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof OreMatching)){
			return false;
		}
		return items == ((OreMatching) obj).items;
	}
}
