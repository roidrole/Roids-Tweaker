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

@SideOnly(Side.CLIENT)
public class FluidStateMapper extends StateMapperBase implements ItemMeshDefinition {

    public final Fluid fluid;
    public final ModelResourceLocation location;

    public FluidStateMapper(Fluid fluid){
        this.fluid = fluid;

        this.location = new ModelResourceLocation(new ResourceLocation("mekatweaker", "fluids"), fluid.getName());
    }

    @Override
    protected ModelResourceLocation getModelResourceLocation(IBlockState state){
        return this.location;
    }

    @Override
    public ModelResourceLocation getModelLocation(ItemStack stack){
        return this.location;
    }
}