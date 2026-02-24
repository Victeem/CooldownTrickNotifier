package gay.plat.ctn.config;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

import java.io.Serializable;

public class CtnConfig implements Serializable {
    public boolean enabled = true;
    public float volume = 1F;
    public float pitch = 1F;
    public String sound = "entity.arrow.hit_player";
    public PlayMode playFor = PlayMode.SELF;
    public enum PlayMode {
        SELF, @SuppressWarnings("unused") ALL, OTHERS
    }

    public boolean shouldPlaySound(Player player) {
        if (!enabled || player == null || Minecraft.getInstance().player == null) return false;
        switch (playFor) {
            case SELF -> {
                return player.getUUID().equals(Minecraft.getInstance().player.getUUID());
            }
            case OTHERS -> {
                return !player.getUUID().equals(Minecraft.getInstance().player.getUUID());
            }
            case null, default -> {
                return true;
            }
        }
    }
}
