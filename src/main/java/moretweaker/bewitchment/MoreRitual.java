package moretweaker.bewitchment;

import com.bewitchment.api.registry.Ritual;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;

public class MoreRitual extends Ritual {
	private final ResourceLocation spawnEntity;

	public MoreRitual(ResourceLocation rl, List<Ingredient> in, List<ItemStack> out, String entityIn, String entityOut, int power, int small, int medium, int large) {
		super(rl, in, entityIn == null ? null : (e) -> {
			ResourceLocation entity_key = EntityList.getKey(e);
			return entity_key != null && entity_key.equals(new ResourceLocation(entityIn));
		}, out, false, 10, power, 1, small, medium, large);
		this.spawnEntity = entityOut == null ? null : new ResourceLocation(entityOut);
	}

	public String getPreconditionMessage() {
		return this.spawnEntity == null ? "ritual.precondition.empty" : "ritual.precondition.sacrifice";
	}

	public void onFinished(World world, BlockPos anotherPos, BlockPos ritualPos, EntityPlayer player, ItemStackHandler something) {
		super.onFinished(world, anotherPos, ritualPos, player, something);
		if (this.spawnEntity != null && !world.isRemote) {
			EntityEntry spawnEntry = ForgeRegistries.ENTITIES.getValue(this.spawnEntity);
			if (spawnEntry != null) {
				Entity toSpawn = spawnEntry.newInstance(world);
				toSpawn.setLocationAndAngles((double)ritualPos.getX() + (double)0.5F, (double)ritualPos.getY() + (double)0.5F, (double)ritualPos.getZ() + (double)0.5F, 0.0F, 0.0F);
				world.spawnEntity(toSpawn);
			}
		}

	}
}
