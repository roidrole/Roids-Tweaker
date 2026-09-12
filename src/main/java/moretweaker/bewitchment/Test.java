package moretweaker.bewitchment;

import com.bewitchment.Util;
import com.bewitchment.common.block.BlockGlyph;
import com.bewitchment.common.item.tool.ItemAthame;
import com.bewitchment.common.item.tool.ItemBoline;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.List;
import java.util.function.Predicate;

@SuppressWarnings("unused")
public class Test extends IForgeRegistryEntry.Impl<Test> {
   public static final int[][] small = new int[][]{{0, 0, 1, 1, 1, 0, 0}, {0, 1, 0, 0, 0, 1, 0}, {1, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 1}, {0, 1, 0, 0, 0, 1, 0}, {0, 0, 1, 1, 1, 0, 0}};
   public static final int[][] medium = new int[][]{{0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0}, {0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0}, {0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0}, {0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0}, {0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0}};
   public static final int[][] large = new int[][]{{0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0}, {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0}, {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0}, {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0}};
   public final List<Ingredient> input;
   public final List<ItemStack> output;
   public final Predicate<EntityLivingBase> sacrificePredicate;
   public final int[] circles;
   public final int time;
   public final int startingPower;
   public final int runningPower;
   public final boolean canBePerformedRemotely;

   public Test(ResourceLocation name, List<Ingredient> input, Predicate<EntityLivingBase> sacrificePredicate, List<ItemStack> output, boolean canBePerformedRemotely, int time, int startingPower, int runningPower, int small, int medium, int big) {
      this.circles = new int[3];
      this.setRegistryName(name);
      if (input.size() > 10) {
         throw new IllegalArgumentException("Rituals can only have at most 10 input items");
      } else if (output != null && output.size() > 5) {
         throw new IllegalArgumentException("Rituals can only have at most 5 output items");
      } else {
         this.input = input;
         this.sacrificePredicate = sacrificePredicate;
         this.output = output;
         this.canBePerformedRemotely = canBePerformedRemotely;
         this.time = time;
         this.startingPower = startingPower;
         this.runningPower = runningPower;
         if (small < 0) {
            throw new IllegalArgumentException("Cannot have the smaller circle missing");
         } else if (medium < 0 && big > 0) {
            throw new IllegalArgumentException("Cannot have missing middle circle when a big circle is present");
         } else if (small != 0 && medium != 0 && big != 0) {
            this.circles[0] = small;
            this.circles[1] = medium;
            this.circles[2] = big;
         } else {
            throw new IllegalArgumentException("No golden circles allowed");
         }
      }
   }

   public Test(ResourceLocation name, List<Ingredient> input, Predicate<EntityLivingBase> sacrificePredicate, List<ItemStack> output, int time, int startingPower, int runningPower, int small, int medium, int big) {
      this(name, input, sacrificePredicate, output, true, time, startingPower, runningPower, small, medium, big);
   }

   public String getPreconditionMessage() {
      return "ritual.precondition.empty";
   }

   public boolean isValid(World world, BlockPos altarPos, EntityPlayer caster, ItemStackHandler inventory) {
      return this.sacrificePredicate == null;
   }

   public void onStarted(World world, BlockPos pos, EntityPlayer caster, ItemStackHandler inventory) {
      world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
   }

   public void onFinished(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
      world.playSound(null, altarPos, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 0.7F, 0.7F);
      world.playSound(null, effectivePos, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 0.7F, 0.7F);

      for(int i = 0; i < inventory.getSlots(); ++i) {
         ItemStack stack = inventory.getStackInSlot(i);
         if (stack.getItem() instanceof ItemAthame) {
            stack.damageItem(35, caster);
         }

         if (stack.getItem() instanceof ItemBoline) {
            stack.damageItem(50, caster);
         } else {
            inventory.extractItem(i, 1, false);
         }
      }

      if (!world.isRemote && this.output != null) {
         for(ItemStack stack : this.output) {
            InventoryHelper.spawnItemStack(world, altarPos.getX(), altarPos.getY(), altarPos.getZ(), stack.copy());
         }
      }

   }

   public void onHalted(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
      world.playSound(null, altarPos, SoundEvents.ENTITY_CHICKEN_EGG, SoundCategory.BLOCKS, 0.7F, 0.7F);
      if (!world.isRemote) {
         for(int i = 0; i < inventory.getSlots(); ++i) {
            InventoryHelper.spawnItemStack(world, altarPos.getX(), altarPos.getY(), altarPos.getZ(), inventory.extractItem(i, inventory.getStackInSlot(i).getCount(), false));
         }
      }

   }

   public void onUpdate(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
   }

   public final boolean matches(World world, BlockPos pos, ItemStackHandler handler) {
      for(int x = 0; x < small.length; ++x) {
         for(int z = 0; z < small.length; ++z) {
            IBlockState state = world.getBlockState(pos.add(x - small.length / 2, 0, z - small.length / 2));
            if (small[x][z] == 1 && (state.getBlock() != ModObjects.glyph || state.getValue(BlockGlyph.TYPE) == 0 || state.getValue(BlockGlyph.TYPE) != this.circles[0] && this.circles[0] != 4)) {
               return false;
            }
         }
      }

      if (this.circles[1] >= 0) {
         for(int x = 0; x < medium.length; ++x) {
            for(int z = 0; z < medium.length; ++z) {
               IBlockState state = world.getBlockState(pos.add(x - medium.length / 2, 0, z - medium.length / 2));
               if (medium[x][z] == 1 && (state.getBlock() != ModObjects.glyph || state.getValue(BlockGlyph.TYPE) == 0 || state.getValue(BlockGlyph.TYPE) != this.circles[1] && this.circles[1] != 4)) {
                  return false;
               }
            }
         }
      }

      if (this.circles[2] >= 0) {
         for(int x = 0; x < large.length; ++x) {
            for(int z = 0; z < large.length; ++z) {
               IBlockState state = world.getBlockState(pos.add(x - large.length / 2, 0, z - large.length / 2));
               if (large[x][z] == 1 && (state.getBlock() != ModObjects.glyph || state.getValue(BlockGlyph.TYPE) == 0 || state.getValue(BlockGlyph.TYPE) != this.circles[2] && this.circles[2] != 4)) {
                  return false;
               }
            }
         }
      }

      return Util.areISListsEqual(this.input, handler);
   }
}
