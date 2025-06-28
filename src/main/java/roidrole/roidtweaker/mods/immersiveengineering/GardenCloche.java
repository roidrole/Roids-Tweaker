package roidrole.roidtweaker.mods.immersiveengineering;

import blusunrize.immersiveengineering.api.ComparableItemStack;
import blusunrize.immersiveengineering.api.crafting.IngredientStack;
import blusunrize.immersiveengineering.api.tool.BelljarHandler;
import blusunrize.immersiveengineering.common.util.compat.crafttweaker.CraftTweakerHelper;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlock;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import roidrole.roidtweaker.mixins.immersiveengineering.IBellJarHandlerAccessor;
import roidrole.roidtweaker.mixins.immersiveengineering.IDefaultPlantHandlerAccessor;
import roidrole.roidtweaker.mods.immersiveengineering.helpers.CTFertilizerHandler;
import roidrole.roidtweaker.mods.immersiveengineering.helpers.GardenClocheMultiplierFunction;
import roidrole.roidtweaker.mods.immersiveengineering.helpers.IECTHelper;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ModOnly("immersiveengineering")
@ZenRegister
@ZenClass("mods.roidtweaker.immersiveengineering.GardenCloche")
@SuppressWarnings("unused")
public class GardenCloche {
    @ZenMethod
    public static void addFertilizer(IIngredient fertilizer, float multiplier){
        new CTFertilizerHandler(fertilizer, multiplier);
    }
    @ZenMethod
    public static void addFertilizer(IIngredient fertilizer, GardenClocheMultiplierFunction multiplier){
        new CTFertilizerHandler(fertilizer, multiplier);
    }
    @ZenMethod
    public static void removeFertilizer(IIngredient fertilizer){
        if(fertilizer.getLiquids().isEmpty()){
            for(ILiquidStack stack : fertilizer.getLiquids()){
                IBellJarHandlerAccessor.getFluidFertilizers().remove(BelljarHandler.getFluidFertilizerHandler(CraftTweakerMC.getLiquidStack(stack)));
            }
        } else {
            for(IItemStack stack : fertilizer.getItems()){
                IBellJarHandlerAccessor.getItemFertilizers().remove(BelljarHandler.getItemFertilizerHandler(CraftTweakerMC.getItemStack(stack)));
            }
        }
    }
    @ZenMethod
    public static void clearFertilizers(@Optional @Nullable Boolean restrictionReadWiki){
        if(Boolean.FALSE.equals(restrictionReadWiki)){
            IBellJarHandlerAccessor.getFluidFertilizers().clear();
        }
        if(Boolean.TRUE.equals(restrictionReadWiki)){
            IBellJarHandlerAccessor.getItemFertilizers().clear();
        }
    }
    @ZenMethod
    public static List<String> listAllFluidFertilizers(){
        return FluidRegistry.getRegisteredFluids().values().stream().filter(
            fluid -> BelljarHandler.getFluidFertilizerHandler(new FluidStack(fluid, 1000)) != null
        ).map(Fluid::getName).collect(Collectors.toList());
    }
    @ZenMethod
    public static List<String> listAllCrops(){
        return IBellJarHandlerAccessor.getPlantHandlers().stream().flatMap(handler -> {
            if(handler instanceof BelljarHandler.DefaultPlantHandler){
                return ((IDefaultPlantHandlerAccessor) handler).invokeGetSeedSet().stream().map(seed -> {
                    //This part was entirely taken from ZenCloche, modified to use Accessor mixins instead of reflection
                    IngredientStack soil = IBellJarHandlerAccessor.getSeedSoilMap().get(seed);
                    ItemStack[] outputs = IBellJarHandlerAccessor.getSeedOutputMap().get(seed);
                    net.minecraft.block.state.IBlockState[] display = IBellJarHandlerAccessor.getSeedRenderMap().get(seed);
                    String soilStr;
                    ItemStack soilStack = soil.stack;
                    if (soilStack.getItem().getRegistryName().toString().equals("minecraft:air")) {
                        soilStr = soil.oreName == null ? "<IIngredient>" : "<ore:" + soil.oreName;
                    } else {
                        soilStr = soil.stack.getItem().getRegistryName() + ":" + soil.stack.getMetadata();
                    }
                    StringBuilder builder = new StringBuilder();
                    builder.append("§2");
                    builder.append(seed.stack.getItem().getRegistryName());
                    builder.append(":");
                    builder.append(seed.stack.getMetadata());
                    builder.append("§r - §3[");
                    Arrays.stream(outputs).forEach(output -> {
                        builder.append(output.getItem().getRegistryName());
                        builder.append(":");
                        builder.append(output.getMetadata());
                        builder.append(", ");
                    });
                    builder.delete(builder.length() - 2, builder.length());
                    builder.append("]§r - §4");
                    builder.append(soilStr);
                    builder.append("§r");
                    return builder.toString();
                });
            }
            else{
                return Stream.of(handler.getClass().getName());
            }
        }).collect(Collectors.toList());
    }

    @ZenMethod
    public static void addPlantHandler(String type){
        BelljarHandler.DefaultPlantHandler temp = new BelljarHandler.DefaultPlantHandler() {
            HashSet<ComparableItemStack> seedSet = new HashSet<>();
            @Override
            protected HashSet<ComparableItemStack> getSeedSet() {
                return seedSet;
            }
        };
        IECTHelper.HandlerMap.put(type, temp);
        BelljarHandler.registerHandler(temp);
    }
    @ZenMethod
    public static void addCrop(String type, IItemStack seed, IItemStack[] drops, @Optional IIngredient soil, @Optional @Nullable IBlock display){
        net.minecraft.block.state.IBlockState[] toDisplay;
        if(display == null){
            toDisplay = seed.getItems().stream()
                .filter(IItemStack::isItemBlock)
                .map(item -> CraftTweakerMC.getBlock(item).getDefaultState())
                .toArray(net.minecraft.block.state.IBlockState[]::new);
        } else {
            toDisplay = display.getBlocks().stream()
                .map(item -> CraftTweakerMC.getBlock(item).getDefaultState())
                .toArray(net.minecraft.block.state.IBlockState[]::new);
        }
        if(toDisplay.length == 0){
            toDisplay = new net.minecraft.block.state.IBlockState[]{Blocks.AIR.getDefaultState()};
        }
        IngredientStack actualSoil;
        if(soil == null){
            actualSoil = new IngredientStack(new ItemStack(Blocks.DIRT));
        } else {
            actualSoil = CraftTweakerHelper.toIEIngredientStack(soil);
        }
        ComparableItemStack compSeed = IECTHelper.toComparableItemStack(seed);
        assert IECTHelper.HandlerMap.containsKey(type);
        ((IDefaultPlantHandlerAccessor)IECTHelper.HandlerMap.get(type)).invokeGetSeedSet().add(compSeed);
        IBellJarHandlerAccessor.getSeedSoilMap().put(compSeed, actualSoil);
        IBellJarHandlerAccessor.getSeedOutputMap().put(compSeed, Arrays.stream(drops).map(CraftTweakerMC::getItemStack).toArray(ItemStack[]::new));
        IBellJarHandlerAccessor.getSeedRenderMap().put(compSeed, toDisplay);
    }
    @ZenMethod
    public static void addCrop(String type, IIngredient seed, IItemStack[] drops, IIngredient soil, IBlockState display){
        ComparableItemStack compSeed = IECTHelper.toComparableItemStack(seed);
        ((IDefaultPlantHandlerAccessor)IECTHelper.HandlerMap.get(type)).invokeGetSeedSet().add(compSeed);
        IBellJarHandlerAccessor.getSeedSoilMap().put(compSeed, CraftTweakerHelper.toIEIngredientStack(soil));
        IBellJarHandlerAccessor.getSeedOutputMap().put(compSeed, Arrays.stream(drops).map(CraftTweakerMC::getItemStack).toArray(ItemStack[]::new));
        IBellJarHandlerAccessor.getSeedRenderMap().put(compSeed, new net.minecraft.block.state.IBlockState[]{CraftTweakerMC.getBlockState(display)});
    }
    @ZenMethod
    public static void removeCrop(IIngredient crop, @Optional String type){
        ComparableItemStack cropComp = IECTHelper.toComparableItemStack(crop);
        if(type != null){
            ((IDefaultPlantHandlerAccessor)IECTHelper.HandlerMap.get(type)).invokeGetSeedSet().remove(cropComp);
        } else {
            List<BelljarHandler.DefaultPlantHandler> handlers = new ArrayList<>(1);
            for (IItemStack cropIStack : crop.getItems()){
                ItemStack cropStack = CraftTweakerMC.getItemStack(cropIStack);
                handlers.add((BelljarHandler.DefaultPlantHandler) BelljarHandler.getHandler(cropStack));
            }
            handlers.forEach(handler -> ((IDefaultPlantHandlerAccessor)handler).invokeGetSeedSet().remove(cropComp));
        }
    }
    @ZenMethod
    public static void setSoilTexture(IIngredient soil, String texture){
        IBellJarHandlerAccessor.getSoilTextureMap().put(IECTHelper.toComparableItemStack(soil), new ResourceLocation(texture));
    }
}
