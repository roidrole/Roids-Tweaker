package roidrole.roidtweaker.mods.baubles;

import baubles.api.cap.BaublesCapabilities;
import baubles.api.cap.InjectableBauble;
import baubles.common.Baubles;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class BaubleEventHandler {
	private static final ResourceLocation capabilityResourceLocation = new ResourceLocation(Baubles.MODID, "bauble_cap");
	public static Map<Item, InjectableBauble> additionalBaubles = new HashMap<>();

	@SubscribeEvent
	public void baubleCreation(AttachCapabilitiesEvent<ItemStack> event){
		ItemStack stack = event.getObject();
		if(stack.isEmpty()){return;}
		InjectableBauble bauble = additionalBaubles.get(stack.getItem());
		if(bauble != null){
			event.addCapability(capabilityResourceLocation, new ICapabilityProvider() {
				@Override
				public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
					return capability == BaublesCapabilities.CAPABILITY_ITEM_BAUBLE;
				}

				@Override
				public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
					return capability == BaublesCapabilities.CAPABILITY_ITEM_BAUBLE ? BaublesCapabilities.CAPABILITY_ITEM_BAUBLE.cast(bauble) : null;
				}
			});
		}
	}
}
