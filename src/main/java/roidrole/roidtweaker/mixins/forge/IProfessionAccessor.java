package roidrole.roidtweaker.mixins.forge;

import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(VillagerProfession.class)
public interface IProfessionAccessor {
    @Accessor(value = "careers", remap = false)
    List<VillagerCareer> getCareers();
}
