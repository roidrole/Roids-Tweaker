package roidrole.roidtweaker.mods.chisel;

import com.blamejared.ModTweaker;
import crafttweaker.IAction;
import crafttweaker.annotations.ModsOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethodStatic;
import team.chisel.api.carving.CarvingUtils;

@ZenExpansion("mods.chisel.Carving")
@ZenRegister
@ModsOnly({"chisel", "modtweaker"})
public class CarvingExtension {
	@ZenMethodStatic
	public static void addVariationAndGroup(String group, IItemStack item){
		ModTweaker.LATE_ADDITIONS.add(new ActionAddVariationAndGroup(group, CraftTweakerMC.getItemStack(item)));
	}

	private static class ActionAddVariationAndGroup implements IAction {

		private final String groupName;
		private final ItemStack stack;

		protected ActionAddVariationAndGroup(String groupName, ItemStack stack) {
			this.groupName = groupName;
			this.stack = stack;
		}

		@Override
		public void apply() {
			CarvingUtils.getChiselRegistry().addGroup(CarvingUtils.getDefaultGroupFor(groupName));
			CarvingUtils.getChiselRegistry().addVariation(groupName, CarvingUtils.variationFor(stack, 0));
		}

		@Override
		public String describe() {
			return "Adding chisel group called: " + groupName;
		}

	}

}
