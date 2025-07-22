package xyz.tcreopargh.ctintegration.vanilla.expansion;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.server.IServer;
import crafttweaker.api.text.ITextComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.UUID;

@ZenExpansion("crafttweaker.server.IServer")
@ZenRegister
@SuppressWarnings("unused")
public class IServerExpansion {

    @ZenGetter("players")
    @ZenMethod
    public static IPlayer[] getPlayers(IServer server) {
        return CraftTweakerMC.getMCServer(server).getPlayerList().getPlayers().stream()
            .map(CraftTweakerMC::getIPlayer)
            .toArray(IPlayer[]::new);
    }

    @ZenMethod
    public static IPlayer getPlayerByUUID(IServer server, String uuid) {
        MinecraftServer mcServer = CraftTweakerMC.getMCServer(server);
        EntityPlayer mcPlayer = mcServer.getPlayerList().getPlayerByUUID(UUID.fromString(uuid));
        return CraftTweakerMC.getIPlayer(mcPlayer);
    }

    @ZenMethod
    public static IPlayer getPlayerByUsername(IServer server, String name) {
        MinecraftServer mcServer = CraftTweakerMC.getMCServer(server);
        EntityPlayer mcPlayer = mcServer.getPlayerList().getPlayerByUsername(name);
        if (mcPlayer == null) {
            return null;
        }
        return CraftTweakerMC.getIPlayer(mcPlayer);
    }

    @ZenGetter("playerCount")
    @ZenMethod
    public static int getPlayerCount(IServer server) {
        return CraftTweakerMC.getMCServer(server).getCurrentPlayerCount();
    }

    @ZenGetter("maxPlayers")
    @ZenMethod
    public static int getMaxPlayers(IServer server) {
        MinecraftServer mcServer = CraftTweakerMC.getMCServer(server);
        return mcServer.getMaxPlayers();
    }

    @ZenMethod
    public static void changePlayerDimension(IServer server, IPlayer player, int dimensionId) {
        MinecraftServer mcServer = CraftTweakerMC.getMCServer(server);
        EntityPlayer mcPlayer = CraftTweakerMC.getPlayer(player);
        if (mcPlayer instanceof EntityPlayerMP) {
            mcServer.getPlayerList().changePlayerDimension((EntityPlayerMP) mcPlayer, dimensionId);
        }
    }

    @ZenMethod
    public static void broadcastMessage(IServer server, ITextComponent message, @Optional boolean isSystem) {
        MinecraftServer mcServer = CraftTweakerMC.getMCServer(server);
        net.minecraft.util.text.ITextComponent mcTextComponent = CraftTweakerMC.getITextComponent(message);
        mcServer.getPlayerList().sendMessage(mcTextComponent, isSystem);
    }
}
