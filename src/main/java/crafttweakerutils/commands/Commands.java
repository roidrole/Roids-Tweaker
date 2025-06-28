package crafttweakerutils.commands;


import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.command.ICommandSender;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.ctutils.commands.Commands")
@SuppressWarnings("unused")
public class Commands {

	@ZenMethod
	public static ICommandSender getServerCommandSender() {
		return ServerCommandSender.INSTANCE;
	}

}