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
import roidrole.roidtweaker.mixins.forge.ICareerAccessor;
import roidrole.roidtweaker.mixins.forge.IProfessionAccessor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class VillagerCommand extends CraftTweakerCommand {

    private final List<String> arguments = Lists.newArrayList("professions", "careers", "trades");

    public VillagerCommand() {
        super("villager");
    }

    @Override
    protected void init() {
        setDescription(new TextComponentString("Provides a list of valid villager professions or careers"));
    }

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
        StrBuilder builder;
        switch (args[0]){
            case "professions":{
                builder = new StrBuilder(146);
                builder.appendln("List of Villager Professions:");
                ForgeRegistries.VILLAGER_PROFESSIONS.getValuesCollection().forEach(p -> {
                    builder.append(" - ");
                    builder.appendln(p.getRegistryName());
                });
                break;
            }
            case "careers":{
                builder = new StrBuilder(146);
                builder.appendln("List of Villager Careers:");
                Collection<VillagerRegistry.VillagerProfession> professions;
                if(args.length > 1){
                    professions = Collections.singleton(ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation(args[1])));
                } else {
                    professions = ForgeRegistries.VILLAGER_PROFESSIONS.getValuesCollection();
                }
                professions.forEach(prof -> {
                    builder.append(" - ");
                    builder.appendln(prof.getRegistryName());
                    IProfessionAccessor roidProf = (IProfessionAccessor) prof;
                    List<VillagerRegistry.VillagerCareer> careers = roidProf.getCareers();
                    careers.forEach(c -> builder.append(" -  - ").appendln(c.getName()));
                });
                break;
            }
            case "trades":{
                builder = new StrBuilder(146);
                builder.appendln("List of Villager Trades:");
                builder.appendln("It is recommended to use JEIVillagers to see them all");
                Collection<VillagerRegistry.VillagerProfession> professions;
                Collection<VillagerRegistry.VillagerCareer> careers;
                if(args.length > 1){
                    professions = Collections.singleton(ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation(args[1])));
                } else {
                    professions = ForgeRegistries.VILLAGER_PROFESSIONS.getValuesCollection();
                }
                if(args.length > 2){
                    careers = Collections.singletonList(professions.stream().findFirst().get().getCareer(Integer.parseInt(args[2])));
                } else {
                    careers = new ArrayList<>();
                    professions.forEach(p -> careers.addAll(((IProfessionAccessor)p).getCareers()));
                }
                careers.forEach(career ->
                    ((ICareerAccessor)career).getTrades().forEach(levelTrades ->
                        levelTrades.forEach(trade -> {
                                builder.append(" -  - ");
                                builder.appendln(trade.toString());
                            }
                        )
                    )
                );
                break;
            }
            default: {
                return;
            }
        }
        CraftTweakerAPI.logCommand(builder.build());
        sender.sendMessage(new TextComponentString("List generated; see crafttweaker.log in your minecraft dir."));
    }
}
