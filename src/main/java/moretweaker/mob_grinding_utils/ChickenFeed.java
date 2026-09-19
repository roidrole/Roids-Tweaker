package moretweaker.mob_grinding_utils;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.HashMap;
import java.util.Map;

@ZenClass("moretweaker.mob_grinding_utils.ChickenFeed")
@ZenRegister
@ModOnly("mob_grinding_utils")
@SuppressWarnings("unused")
public class ChickenFeed {
	public static final Map<String, ItemStack> spawnEggOverrides = new HashMap<>();

	@ZenMethod
	public static void setSpawnEgg(final String mobId, IItemStack spawnegg) {
		spawnEggOverrides.put(mobId, CraftTweakerMC.getItemStack(spawnegg));
	}

	@ZenMethod
	public static void resetSpawnEgg(final String mobId) {
		spawnEggOverrides.remove(mobId);
	}
}
