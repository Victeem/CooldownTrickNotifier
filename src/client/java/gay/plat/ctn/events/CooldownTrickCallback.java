package gay.plat.ctn.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface CooldownTrickCallback {
    Event<CooldownTrickCallback> EVENT = EventFactory.createArrayBacked(CooldownTrickCallback.class,
            (listeners) -> (player, target) -> {
                for (CooldownTrickCallback listener : listeners) {
                    listener.interact(player, target);
                }
            });

    void interact(PlayerEntity player, Entity target);
}
