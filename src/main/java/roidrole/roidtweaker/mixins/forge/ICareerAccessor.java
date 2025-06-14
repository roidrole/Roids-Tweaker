package roidrole.roidtweaker.mixins.forge;

import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(VillagerCareer.class)
public interface ICareerAccessor {
    @Accessor(value = "trades", remap = false)
    List<List<ITradeList>> getTrades();
}
