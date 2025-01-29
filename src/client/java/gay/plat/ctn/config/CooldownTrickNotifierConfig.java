package gay.plat.ctn.config;

import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;

public class CooldownTrickNotifierConfig extends MidnightConfig {
    public static final String COOLDOWN_TRICK_NOTIFIER = "cooldown_trick_notifier";

    @Entry(category = COOLDOWN_TRICK_NOTIFIER, name = "Mod Enabled")
    public static boolean isEnabled = true;

    @Entry(category = COOLDOWN_TRICK_NOTIFIER, name = "Volume", isSlider = true, min = 0F, max = 1F, precision = 20)
    public static float volume = 1F;

    @Entry(category = COOLDOWN_TRICK_NOTIFIER, name = "Pitch", isSlider = true, min = 0F, max = 1F, precision = 20)
    public static float pitch = 1F;

    @Entry(category = COOLDOWN_TRICK_NOTIFIER, name = "Sound")
    public static String soundID = "entity.arrow.hit_player";

    @Entry(category = COOLDOWN_TRICK_NOTIFIER, name = "Sound will play when who cooldowntricks")
    public static ForWhoPlaySound forWhoPlaySound = ForWhoPlaySound.SELF;
    public enum ForWhoPlaySound {
        SELF, ALL, OTHERS
    }

    public static boolean shouldPlaySoundForPlayer(UUID uuid) {
        if (MinecraftClient.getInstance().player == null) {return false;}
        UUID clientuuid = MinecraftClient.getInstance().player.getUuid();
        switch (forWhoPlaySound) {
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
        return isEnabled && CooldownTrickNotifierConfig.shouldPlaySoundForPlayer(player.getUuid());
    }
}
