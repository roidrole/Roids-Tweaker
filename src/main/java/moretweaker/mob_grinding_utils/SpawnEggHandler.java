package moretweaker.mob_grinding_utils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

public class SpawnEggHandler {
   public static final SpawnEggHandler INSTANCE = new SpawnEggHandler();
   private final Map<ResourceLocation, ItemStack> spawnEggs = new HashMap<>();

   private SpawnEggHandler() {
   }

   public void setSpawnEgg(String mguName, ItemStack spawnEgg) {
      if (spawnEgg == null) {
         spawnEgg = ItemStack.EMPTY;
      }

      this.spawnEggs.put(new ResourceLocation(mguName), spawnEgg);
   }

   public void setDefaultEgg(String mguName) {
      this.spawnEggs.remove(new ResourceLocation(mguName));
   }

   @SubscribeEvent(
      priority = EventPriority.HIGH
   )
   public void living(LivingEvent event) {
      EntityLivingBase entity = (EntityLivingBase)event.getEntity();
      if (entity instanceof EntityChicken) {
         World world = entity.getEntityWorld();
         if (!world.isRemote) {
            NBTTagCompound nbt = entity.getEntityData();
            if (nbt.hasKey("shouldExplode")) {
               int countDown = nbt.getInteger("countDown");
               if (countDown >= 20) {
                  String mobId = nbt.getString("mguMobName");
                  ResourceLocation rl = new ResourceLocation(mobId);
                  if (this.spawnEggs.containsKey(rl)) {
                     nbt.setString("mguMobName", "minecraft:invalid");
                     ItemStack drop = this.spawnEggs.getOrDefault(rl, ItemStack.EMPTY);
                     if (!drop.isEmpty()) {
                        entity.entityDropItem(drop, 0.0F);
                     }
                  }
               }
            }
         }
      }

   }
}
