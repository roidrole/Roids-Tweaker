package roidrole.roidtweaker.mods.baubles;

import baubles.api.IBaubleType;
import baubles.common.Baubles;
import baubles.common.init.BaubleTypes;
import net.minecraft.util.ResourceLocation;

public class BubbleUtils {
	private static Boolean bubblesInstalled = null;
	public static IBaubleType getBaublesType(String type){
		int indexOfColon = type.indexOf(':');
		if(indexOfColon == -1){
			return BaubleTypes.get(new ResourceLocation(Baubles.MODID, type));
		} else {
			return BaubleTypes.get(new ResourceLocation(type.substring(0, indexOfColon), type.substring(indexOfColon + 1)));
		}
	}

	public static boolean isBubblesInstalled(){
		if(bubblesInstalled == null){
			try {
				Class.forName("baubles.api.IBaubleType");
				bubblesInstalled = true;
			} catch (ClassNotFoundException e) {
				bubblesInstalled = false;
			}
		}
		return bubblesInstalled;
	}
}
