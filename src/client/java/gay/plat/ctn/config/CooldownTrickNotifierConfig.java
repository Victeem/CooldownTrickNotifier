package gay.plat.ctn.config;

import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;

public class CooldownTrickNotifierConfig extends MidnightConfig {
    public static final String CTN = "ctn";

    @Entry(category = CTN, name = "Mod Enabled")
    public static boolean isEnabled = true;

    @Entry(category = CTN, name = "Volume", isSlider = true, min = 0F, max = 1F, precision = 20)
    public static float volume = 1F;

    @Entry(category = CTN, name = "Pitch", isSlider = true, min = 0.5F, max = 2F, precision = 20)
    public static float pitch = 1F;

    @Entry(category = CTN, name = "Sound ID")
    public static String soundID = "entity.arrow.hit_player";

    @Entry(category = CTN, name = "Play Mode")
    public static PlayMode playMode = PlayMode.SELF;
    public enum PlayMode {
        SELF, @SuppressWarnings("unused") ALL, OTHERS
    }

    public static boolean shouldPlaySoundForPlayer(UUID uuid) {
        if (MinecraftClient.getInstance().player == null) {return false;}
        UUID clientuuid = MinecraftClient.getInstance().player.getUuid();
        switch (playMode) {
            case SELF -> {
                return uuid.equals(clientuuid);
            }
            case OTHERS -> {
                return !uuid.equals(clientuuid);
            }
            case null, default -> {
                return true;
            }
        }
    }

    public static boolean shouldPlaySound(PlayerEntity player) {
        if (player == null) return false;
        return isEnabled && CooldownTrickNotifierConfig.shouldPlaySoundForPlayer(player.getUuid());
    }
}
