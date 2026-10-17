package roidrole.roidtweaker.mods.baubles;

import baubles.api.BaubleType;
import baubles.api.IBaubleType;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import xyz.tcreopargh.ctintegration.cot.BaubleItemContent;
import xyz.tcreopargh.ctintegration.cot.BaubleItemRepresentation;

public class BubbleItemContent extends BaubleItemContent {

    public BubbleItemContent(BaubleItemRepresentation itemRepresentation) {
        super(itemRepresentation);
    }

    @Override
    public IBaubleType getType(ItemStack itemStack) {
        IBaubleType out;
        if(getBaubleType != null){
            out = BubbleUtils.getBaublesType(getBaubleType.handle(CraftTweakerMC.getIItemStack(itemStack)));
        } else {
            out = BubbleUtils.getBaublesType(baubleType);
        }
        if(out == null){
            return BaubleType.TRINKET;
        }
        return out;
    }
}
