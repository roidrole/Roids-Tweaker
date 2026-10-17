package roidrole.roidtweaker.mods.baubles;

import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethodStatic;

@ZenExpansion("mods.ctintegration.baubles.IBauble")
public class CTBaubleExpansion {
	@ZenMethodStatic
	public static CTInjectableBauble createBauble(String type){
		return new CTInjectableBauble(BubbleUtils.getBaublesType(type));
	}
}
