package moretweaker.erebus;

import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import moretweaker.MoreTweaker;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.mixins.erebus.ComposterRegistryAccessor;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ZenClass("moretweaker.erebus.Composter")
@ZenRegister
@ModOnly("erebus")
@SuppressWarnings("unused")
public class Composter {

	@ZenMethod
	public static void add(IItemStack compostable) {
		final ItemStack c = CraftTweakerMC.getItemStack(compostable);
		c.setCount(1);
		MoreTweaker.queuePostInitAction(new IAction() {
			public void apply() {
				ComposterRegistryAccessor.getRegistry().add(c);
				ComposterRegistryAccessor.getBlacklist().removeIf((stack) -> Composter.isMatching(stack, c));
			}

			public String describe() {
				return "Adds an item to the composter.";
			}
		});
	}

	@ZenMethod
	public static void addAll(IItemStack[] compostables) {
		ItemStack[] c = CraftTweakerMC.getItemStacks(compostables);
		final ArrayList<ItemStack> all = new ArrayList<>();
		Collections.addAll(all, c);
		all.forEach((stack) -> stack.setCount(1));
		MoreTweaker.queuePostInitAction(new IAction() {
			public void apply() {
				ComposterRegistryAccessor.getRegistry().addAll(all);
				ComposterRegistryAccessor.getBlacklist().removeIf((stack) -> Composter.hasOneMatching(stack, all));
			}

			public String describe() {
				return "Adds some items to the composter.";
			}
		});
	}

	@ZenMethod
	public static void remove(IItemStack nonCompostable) {
		final ItemStack c = CraftTweakerMC.getItemStack(nonCompostable);
		c.setCount(1);
		MoreTweaker.queuePostInitAction(new IAction() {
			public void apply() {
				ComposterRegistryAccessor.getBlacklist().add(c);
				ComposterRegistryAccessor.getRegistry().removeIf((stack) -> Composter.isMatching(stack, c));
			}

			public String describe() {
				return "Removes an item from the composter.";
			}
		});
	}

	@ZenMethod
	public static void removeAll(IItemStack[] nonCompostables) {
		ItemStack[] c = CraftTweakerMC.getItemStacks(nonCompostables);
		final ArrayList<ItemStack> all = new ArrayList<>();
		Collections.addAll(all, c);
		all.forEach((stack) -> stack.setCount(1));
		MoreTweaker.queuePostInitAction(new IAction() {
			public void apply() {
				ComposterRegistryAccessor.getBlacklist().addAll(all);
				ComposterRegistryAccessor.getRegistry().removeIf((stack) -> Composter.hasOneMatching(stack, all));
			}

			public String describe() {
				return "Removes some items from the composter.";
			}
		});
	}

	private static boolean hasOneMatching(ItemStack in, List<ItemStack> matcher) {
		for(ItemStack stack : matcher) {
			if (isMatching(in, stack)) {
				return true;
			}
		}

		return false;
	}

	private static boolean isMatching(ItemStack in, ItemStack matcher) {
		return ItemStack.areItemsEqual(in, matcher) && (in.getMetadata() == matcher.getMetadata() || 32767 == matcher.getMetadata());
	}
}
