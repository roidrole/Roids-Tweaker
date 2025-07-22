package xyz.tcreopargh.ctintegration.ftbmoney;

import com.feed_the_beast.mods.money.FTBMoney;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraft.entity.player.EntityPlayer;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

@ModOnly("ftbmoney")
@ZenExpansion("crafttweaker.player.IPlayer")
@ZenRegister
@SuppressWarnings("unused")
public class PlayerExpansionFTBM {

    @ZenGetter("ftbMoney")
    @ZenMethod
    public static long getFtbMoney(IPlayer player) {
        EntityPlayer mcPlayer = CraftTweakerMC.getPlayer(player);
        return FTBMoney.getMoney(mcPlayer);
    }

    @ZenSetter("ftbMoney")
    @ZenMethod
    public static void setFtbMoney(IPlayer player, long value) {
        EntityPlayer mcPlayer = CraftTweakerMC.getPlayer(player);
        FTBMoney.setMoney(mcPlayer, value);
    }
}
