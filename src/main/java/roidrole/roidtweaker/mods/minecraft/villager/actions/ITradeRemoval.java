package roidrole.roidtweaker.mods.minecraft.villager.actions;

import net.minecraftforge.fml.common.registry.VillagerRegistry;
import roidrole.roidtweaker.mixins.forge.villager.AccVillagerCareer;

import java.util.ArrayList;

public interface ITradeRemoval {
    void load(VillagerRegistry.VillagerCareer career);

    class Clear implements ITradeRemoval{
        @Override
        public void load(VillagerRegistry.VillagerCareer career) {
            ((AccVillagerCareer)career).setTrades(new ArrayList<>(5));
        }
    }

    class ClearLevel implements ITradeRemoval{
        final int level;

        public ClearLevel(int level){
            this.level = level;
        }

        @Override
        public void load(VillagerRegistry.VillagerCareer career) {
            ((AccVillagerCareer)career).getTrades().set(level - 1, new ArrayList<>());
        }
    }

    class RemoveTrade implements ITradeRemoval{
        final int level;
        final int tradeIndex;

        public RemoveTrade(int level, int index){
            this.level = level;
            this.tradeIndex = index;
        }

        @Override
        public void load(VillagerRegistry.VillagerCareer career){
            ((AccVillagerCareer)career).getTrades().get(level - 1).remove(tradeIndex);
        }
    }
}
