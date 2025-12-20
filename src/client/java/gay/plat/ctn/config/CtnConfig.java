package gay.plat.ctn.config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.function.ValueLists;
import net.uku3lig.ukulib.config.IConfig;

import java.util.function.IntFunction;

public class CtnConfig implements IConfig<CtnConfig> {
    public boolean enabled = true;
    public float volume = 1F;
    public float pitch = 1F;
    public String sound = "entity.arrow.hit_player";
    public PlayMode playFor = PlayMode.SELF;

    @Override
    public CtnConfig defaultConfig() {
        return new CtnConfig();
    }

    public enum PlayMode {
        SELF(0),
        @SuppressWarnings("unused") ALL(1),
        OTHERS(2);

        public static final IntFunction<PlayMode> BY_ID = ValueLists.createIdToValueFunction(PlayMode::getId, values(), ValueLists.OutOfBoundsHandling.WRAP);

        public final int id;
        public final String translationKey;

        PlayMode(int id) {
            this.id = id;
            this.translationKey = "ctn.config.playFor."+this.name().toLowerCase();
        }

        public static int getId(PlayMode playMode) {
            return playMode.id;
        }
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
