package crafttweakerutils.server;


import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.command.ICommandSender;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethodStatic;

@ZenRegister
@ZenExpansion("crafttweaker.server.IServer")
@SuppressWarnings("unused")
public class IServerExpansion {
	@ZenMethodStatic
	public static ICommandSender getServerCommandSender() {
		return ServerCommandSender.INSTANCE;
	}

}