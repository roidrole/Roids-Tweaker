package moretweaker.erebus;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.mixins.erebus.ComposterRegistryAccessor;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.ListAdd;
import roidrole.roidtweaker.utils.actions.ListRemove;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.function.Predicate;

@ZenClass("moretweaker.erebus.Composter")
@ZenRegister
@ModOnly("erebus")
@SuppressWarnings("unused")
public class Composter {

	@ZenMethod
	public static void add(IItemStack compostable) {
		final ItemStack c = CraftTweakerMC.getItemStack(compostable);
		final Predicate<ItemStack> blackListFilter = Utils.getStackMatcher(compostable.withTag(null, false));
		c.setCount(1);
		DeferredLoader.postInit(new ListAdd<>(ComposterRegistryAccessor.getRegistry(), c));
		DeferredLoader.postInit(new ListRemove<>(ComposterRegistryAccessor.getBlacklist(), blackListFilter));
	}

	@ZenMethod
	public static void addAll(IItemStack[] compostables) {
		final ItemStack[] list = CraftTweakerMC.getItemStacks(compostables);
		for (ItemStack stack : list){
			stack.setCount(1);
		}
		DeferredLoader.postInit(new ListAdd<>(ComposterRegistryAccessor.getRegistry(), list));
		DeferredLoader.postInit(new ListRemove<>(ComposterRegistryAccessor.getRegistry(), Utils.anyMatch(list)));
	}

	@ZenMethod
	public static void remove(IItemStack nonCompostable) {
		final Predicate<ItemStack> registryFilter = Utils.getStackMatcher(nonCompostable.withTag(null, false));
		final ItemStack c = CraftTweakerMC.getItemStack(nonCompostable);
		c.setCount(1);
		DeferredLoader.postInit(new ListAdd<>(ComposterRegistryAccessor.getBlacklist(), c));
		DeferredLoader.postInit(new ListRemove<>(ComposterRegistryAccessor.getRegistry(), registryFilter));
	}

	@ZenMethod
	public static void removeAll(IItemStack[] nonCompostables) {
		final ItemStack[] list = CraftTweakerMC.getItemStacks(nonCompostables);
		for (ItemStack stack : list){
			stack.setCount(1);
		}
		DeferredLoader.postInit(new ListAdd<>(ComposterRegistryAccessor.getBlacklist(), list));
		DeferredLoader.postInit(new ListRemove<>(ComposterRegistryAccessor.getRegistry(), Utils.anyMatch(list)));
	}
}
