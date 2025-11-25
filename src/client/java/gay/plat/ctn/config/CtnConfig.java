package gay.plat.ctn.config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

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

    public boolean shouldPlaySound(PlayerEntity player) {
        if (!enabled || player == null || MinecraftClient.getInstance().player == null) return false;
        switch (playFor) {
            case SELF -> {
                return player.getUuid().equals(MinecraftClient.getInstance().player.getUuid());
            }
            case OTHERS -> {
                return !player.getUuid().equals(MinecraftClient.getInstance().player.getUuid());
            }
            case null, default -> {
                return true;
            }
        }
    }
}
