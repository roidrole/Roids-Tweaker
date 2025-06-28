package crafttweakerutils.entity;


import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityXp;
import net.minecraft.entity.item.EntityXPOrb;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenExpansion("crafttweaker.entity.IEntityXp")
@SuppressWarnings("unused")
public class IEntityXPExpansion {

	@ZenMethod
	public static void setXpValue(IEntityXp xp, int value)
	{
		EntityXPOrb orb = (EntityXPOrb)xp.getInternal();
		orb.xpValue = value;
	}

}
