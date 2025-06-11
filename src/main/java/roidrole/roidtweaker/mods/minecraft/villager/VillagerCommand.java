package roidrole.roidtweaker.mods.minecraft.villager;

import com.google.common.collect.Lists;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.mc1120.commands.CraftTweakerCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import org.apache.commons.lang3.text.StrBuilder;
import rocks.gameonthe.rockytweaks.crafttweaker.merchant.VillagerHelper;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class VillagerCommand extends CraftTweakerCommand {


    private final List<String> arguments = Lists.newArrayList("professions", "careers", "trades"); // TODO: Add "trades"

    public VillagerCommand() {
        super("merchant");
        setDescription(new TextComponentString("Provides a list of valid merchant professions or careers"));
    }

    @Override
    protected void init() {}

    @Override
    public List<String> getSubSubCommand(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return arguments;
    }

    @Override
    public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
        if (args.length == 0 || !arguments.contains(args[0])) {
            sender.sendMessage(new TextComponentString("Invalid arguments for command. Valid arguments: " + String.join(", ", arguments)));
            return;
        }
        StrBuilder builder = new StrBuilder();
        switch (args[0]){
            case "professions":{
                builder.appendln("List of Merchant Professions:");
                ForgeRegistries.VILLAGER_PROFESSIONS.getValuesCollection().forEach(p -> builder.appendln(p.getRegistryName()));
                break;
            }
            case "careers":{
                builder.appendln("List of Merchant Careers:");
                Collection<VillagerRegistry.VillagerProfession> professions;
                if(args.length > 1){
                    professions = Collections.singleton(ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation(args[2])));
                } else {
                    professions = ForgeRegistries.VILLAGER_PROFESSIONS.getValuesCollection();
                }
                professions.forEach(prof -> {
                    builder.appendln(prof.getRegistryName());
                    VillagerHelper.getVillagerCareers(prof).forEach(c -> builder.append(" - ").appendln(c.getName()));
                });
                CraftTweakerAPI.logCommand(builder.build());
                sender.sendMessage(new TextComponentString("List generated; see crafttweaker.log in your minecraft dir."));
                break;
            }
            case "trades":{
                builder.appendln("List of Merchant Trades:");
                builder.appendln("It is recommended to use JEIVillagers to see them all");
                ForgeRegistries.VILLAGER_PROFESSIONS.getValuesCollection().forEach(prof -> {
                    //Use prof.carrers here, but AT is not cooperating
                });
                CraftTweakerAPI.logCommand(builder.build());
                break;
            }
            default: {
                builder.appendln("Command wrongly written. Available sub commands are : ");
                arguments.forEach(arg -> builder.append(" - ").appendln(arg));
            }
        }
        CraftTweakerAPI.logCommand(builder.build());
        sender.sendMessage(new TextComponentString("List generated; see crafttweaker.log in your minecraft dir."));
    }
}
