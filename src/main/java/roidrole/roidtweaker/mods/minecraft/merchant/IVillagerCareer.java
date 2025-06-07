package roidrole.roidtweaker.mods.minecraft.merchant;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;

public class IVillagerCareer {
    VillagerRegistry.VillagerCareer career;

    public IVillagerCareer(VillagerRegistry.VillagerCareer career){
        this.career = career;
    }

    @ZenMethod
    public void addTrade(int level, List<EntityVillager.ITradeList> trades){
        career.trades.add(level, trades);
    }
}
