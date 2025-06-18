package roidrole.roidtweaker.mods.minecraft.villager;

import com.google.common.collect.Lists;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.mc1120.commands.CraftTweakerCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import org.apache.commons.lang3.text.StrBuilder;
import roidrole.roidtweaker.mixins.forge.villager.ICareerAccessor;
import roidrole.roidtweaker.mixins.forge.villager.IProfessionAccessor;

import javax.annotation.Nullable;
import java.util.*;

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
        VillagerLogBuilder builder;
        switch (args[0]){
            case "professions":{
                builder = new VillagerLogBuilder(146, 1);
                builder.appendln("List of Villager Professions:");
                ForgeRegistries.VILLAGER_PROFESSIONS.forEach(builder::showProfession);
                break;
            }
            case "careers":{
                builder = new VillagerLogBuilder(313, 2);
                builder.appendln("List of Villager Careers:");
                Iterable<VillagerRegistry.VillagerProfession> professions;
                if(args.length > 1){
                    professions = Collections.singleton((ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation(args[1]))));
                } else {
                    professions = ForgeRegistries.VILLAGER_PROFESSIONS;
                }
                professions.forEach(prof -> {
                    builder.showProfession(prof);
                    List<VillagerRegistry.VillagerCareer> careers = ((IProfessionAccessor) prof).getCareers();
                    careers.forEach(builder::showCareer);
                });
                break;
            }
            case "trades":{
                sender.sendMessage(new TextComponentString("Listing all Villager trades. This may take a short while"));
                builder = new VillagerLogBuilder(args.length==1?17701:805, 5);
                builder.appendln("List of Villager Trades:");
                builder.appendln("It is recommended to use JEIVillagers to see them all");
                Map<VillagerRegistry.VillagerProfession, Collection<VillagerRegistry.VillagerCareer>> careerMap;
                //Builds the map according to how many parameters
                switch(args.length){
                    case 3:{
                        VillagerRegistry.VillagerProfession prof = ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation(args[1]));
                        careerMap = Collections.singletonMap(prof, Collections.singleton(prof.getCareer(Integer.parseInt(args[2]))));
                        break;
                    }
                    case 2:{
                        VillagerRegistry.VillagerProfession prof = ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation(args[1]));
                        careerMap = Collections.singletonMap(prof, ((IProfessionAccessor)prof).getCareers());
                        break;
                    }
                    default:{
                        Map<VillagerRegistry.VillagerProfession, Collection<VillagerRegistry.VillagerCareer>> finalCareerMap = new HashMap<>();
                        ForgeRegistries.VILLAGER_PROFESSIONS.forEach(prof ->
                            finalCareerMap.put(prof, ((IProfessionAccessor)prof).getCareers()));
                        careerMap = finalCareerMap;
                    }
                }

                careerMap.forEach((prof, careers) -> {
                    builder.showProfession(prof);
                    careers.forEach(career -> {
                        builder.showCareer(career);
                        int level = 0;
                        for(List<EntityVillager.ITradeList> levelTrades : ((ICareerAccessor)career).getTrades()){
                            builder.showLevel(++level);
                            levelTrades.forEach(trade -> {
                                builder.showTrade(trade);
                                MerchantRecipeList merchantRecipeList = new MerchantRecipeList();
                                trade.addMerchantRecipe(new EntityVillager(server.getEntityWorld()), merchantRecipeList, sender.getEntityWorld().rand);
                                merchantRecipeList.forEach(
                                    builder::showMerchantRecipe
                                );
                            });
                        }
                    });
                });
                break;
            }
            default: {
                return;
            }
        }
        CraftTweakerAPI.logCommand(builder.build());
        sender.sendMessage(new TextComponentString("List generated; see crafttweaker.log in your minecraft dir."));
    }
    
    //Helpers
    public static class VillagerLogBuilder extends StrBuilder{
        int maxIndent;
        public VillagerLogBuilder(int initialSize, int maxIndent) {
            super(initialSize);
            this.maxIndent = maxIndent;
        }

        public void show(String show, int indent){
            for (int i = 0; i < indent; i++) {
                this.append(" - ");
            }
            this.append(show);
            if(indent != maxIndent){this.append(":");}
            this.appendNewLine();
        }

        public void showProfession(VillagerRegistry.VillagerProfession prof){
            show(prof.getRegistryName().toString(), 1);
        }

        public void showCareer(VillagerRegistry.VillagerCareer career){
            show(career.getName(), 2);
        }

        public void showLevel(int level){
            show(String.valueOf(level), 3);
        }

        public void showTrade(EntityVillager.ITradeList trade){
            show(trade.getClass().getName(), 4);
        }

        public void showMerchantRecipe(MerchantRecipe recipe){
            this.append(" -  -  -  -  - buy1:");
            this.appendln(recipe.getItemToBuy().toString());
            this.append(" -  -  -  -  - buy2:");
            this.appendln(recipe.getSecondItemToBuy().toString());
            this.append(" -  -  -  -  - sell:");
            this.appendln(recipe.getItemToSell().toString());
        }
    }

}
