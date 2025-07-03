package roidrole.roidtweaker.mods.immersiveengineering;

import com.google.common.collect.Lists;
import crafttweaker.mc1120.commands.CraftTweakerCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.List;

//Entirely taken from ZenCloche
public class GardenClocheCommand extends CraftTweakerCommand {
    private final List<String> arguments = Lists.newArrayList("crop", "fluidFertilizers");

    public GardenClocheCommand() {
        super("gardencloche");
    }

    @Override
    protected void init() {
        setDescription(new TextComponentString("Prints cloche registered fluidFertilizers or crops"));
    }

    @Override
    public List<String> getSubSubCommand(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return arguments;
    }

    @Override
    public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {

        if (sender.getEntityWorld().isRemote) return;

        if (args.length != 1) {
            sender.sendMessage(new TextComponentString("Command requires one argument: " +
                "\nUse 'fluidFertilizers' to print fluid fertilizers" +
                "\nUse 'crops' to print crops"));
            return;
        }

        switch (args[0]) {
            case "fluidFertilizers":
                sender.sendMessage(new TextComponentString(String.join("\n", GardenCloche.listAllFluidFertilizers())));
                break;
            case "crop":
                sender.sendMessage(new TextComponentString(String.join("\n", GardenCloche.listAllCrops())));
                break;
            default:
                sender.sendMessage(new TextComponentString("Could not recognize argument. Valid arguments are, 'fluidFertilizers' and 'crop' (without quotes)"));
        }


    }
}
