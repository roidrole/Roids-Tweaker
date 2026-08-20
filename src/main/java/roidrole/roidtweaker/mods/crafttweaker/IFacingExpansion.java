package roidrole.roidtweaker.mods.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.IFacing;
import crafttweaker.mc1120.world.MCFacing;
import net.minecraft.util.EnumFacing;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethodStatic;

@ZenRegister
@ZenExpansion("crafttweaker.world.IFacing")
public class IFacingExpansion {
	@ZenMethodStatic
	public static IFacing[] getHorizontals(){
		EnumFacing[] horizontals = EnumFacing.HORIZONTALS;
		IFacing[] out = new IFacing[4];
		for (int i = 0; i < 4; i++) {
			out[i] = new MCFacing(horizontals[i]);
		}
		return out;
	}

	@ZenMethodStatic
	public static IFacing[] getFacings(){
		EnumFacing[] facings = EnumFacing.VALUES;
		IFacing[] out = new IFacing[6];
		for (int i = 0; i < 6; i++) {
			out[i] = new MCFacing(facings[i]);
		}
		return out;
	}
}
