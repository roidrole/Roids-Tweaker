package crafttweakerutils;


import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IPlayer;
import net.minecraftforge.common.util.FakePlayer;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenExpansion("crafttweaker.player.IPlayer")
@SuppressWarnings("unused")
public class IPlayerExpansion {

	@ZenMethod
	public static boolean isFake(IPlayer player)
	{
		return (player.getInternal() instanceof FakePlayer);
	}

}
