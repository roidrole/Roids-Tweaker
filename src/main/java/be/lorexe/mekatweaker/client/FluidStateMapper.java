package be.lorexe.mekatweaker.client;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class FluidStateMapper extends StateMapperBase implements ItemMeshDefinition {

    public final Fluid fluid;
    public final ModelResourceLocation location;

    public FluidStateMapper(Fluid fluid){
        this.fluid = fluid;

        this.location = new ModelResourceLocation(new ResourceLocation("mekatweaker", "fluids"), fluid.getName());
    }

    @Override
    protected @Nonnull ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state){
        return this.location;
    }

    @Override
    public @Nonnull ModelResourceLocation getModelLocation(@Nonnull ItemStack stack){
        return this.location;
    }
}