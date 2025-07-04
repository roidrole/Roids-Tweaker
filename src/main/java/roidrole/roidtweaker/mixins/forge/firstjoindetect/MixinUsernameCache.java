package roidrole.roidtweaker.mixins.forge.firstjoindetect;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import net.minecraftforge.common.UsernameCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static roidrole.roidtweaker.mods.forge.firstjoin.PlayerFirstJoinEvent.EventFirer.firstJoins;

@Mixin(UsernameCache.class)
public abstract class MixinUsernameCache {
    @ModifyReceiver(
        method = "setUsername",
        at=@At(
            value = "INVOKE_ASSIGN",
            target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"
        ),
        remap = false
    )
    private static String roidTweaker_checkFirstJoin(String name, Object UUID){
        if(UUID == null){
            System.out.println("Player "+name +" joined for the first time!");
            firstJoins.add(name);
        }
        return name;
    }
}
