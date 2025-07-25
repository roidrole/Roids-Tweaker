package xyz.tcreopargh.ctintegration.projecte;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import moze_intel.projecte.api.ProjectEAPI;
import moze_intel.projecte.api.capabilities.IKnowledgeProvider;
import net.minecraft.entity.player.EntityPlayer;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

@ModOnly("projecte")
@ZenExpansion("crafttweaker.player.IPlayer")
@ZenRegister
@SuppressWarnings("unused")
public class PlayerExpansionProjectE {

    @ZenGetter("personalEMC")
    @ZenMethod
    public static long getPersonalEMC(IPlayer player) {
        EntityPlayer mcPlayer = CraftTweakerMC.getPlayer(player);
        IKnowledgeProvider iKnowledgeProvider = mcPlayer.getCapability(ProjectEAPI.KNOWLEDGE_CAPABILITY, null);
        if (iKnowledgeProvider == null) {
            return 0;
        }
        return iKnowledgeProvider.getEmc();
    }

    @ZenSetter("personalEMC")
    @ZenMethod
    public static void setPersonalEMC(IPlayer player, long emc) {
        EntityPlayer mcPlayer = CraftTweakerMC.getPlayer(player);
        IKnowledgeProvider iKnowledgeProvider = mcPlayer.getCapability(ProjectEAPI.KNOWLEDGE_CAPABILITY, null);
        if (iKnowledgeProvider != null) {
            iKnowledgeProvider.setEmc(emc);
        }
    }
}
