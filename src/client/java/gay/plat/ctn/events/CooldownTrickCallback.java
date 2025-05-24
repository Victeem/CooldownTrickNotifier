package gay.plat.ctn.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.world.World;

public interface CooldownTrickCallback {
    Event<CooldownTrickCallback> EVENT = EventFactory.createArrayBacked(CooldownTrickCallback.class,
            (listeners) -> (player, target, world, stack) -> {
                for (CooldownTrickCallback listener : listeners) {
                    listener.interact(player, target, world, stack);
                }
            });

    void interact(PlayerEntity player, Entity target, World world, ItemStack stack);
}
