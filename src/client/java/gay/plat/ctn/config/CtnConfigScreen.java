package gay.plat.ctn.config;

import com.mojang.serialization.Codec;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import net.uku3lig.ukulib.config.ConfigManager;
import net.uku3lig.ukulib.config.screen.AbstractConfigScreen;

import java.util.Arrays;

public class CtnConfigScreen extends AbstractConfigScreen<CtnConfig> {
    public CtnConfigScreen(Screen parent, ConfigManager<CtnConfig> manager) {
        super(parent, Text.of("ctn.config.title"), manager);
    }

    @Override
    protected SimpleOption<?>[] getOptions(CtnConfig config) {
        return new SimpleOption[]{
                SimpleOption.ofBoolean("ctn.config.enabled", config.enabled, b -> config.enabled = b),
                new SimpleOption<>("ctn.config.volume", SimpleOption.emptyTooltip(), CtnConfigScreen::doubleValue, new SimpleOption.ValidatingIntSliderCallbacks(0, 100).withModifier(i -> i / 20.0, d -> (int) (d * 20)), Codec.doubleRange(0, 100), (double) config.volume, d -> config.volume = (float)(d*100)),
                new SimpleOption<>("ctn.config.pitch", SimpleOption.emptyTooltip(), CtnConfigScreen::doubleValue, new SimpleOption.ValidatingIntSliderCallbacks(0, 200).withModifier(i -> i / 20.0, d -> (int) (d * 20)), Codec.doubleRange(0, 200), (double) config.pitch, d -> config.pitch = (float)(d*200)),
                new SimpleOption<>("ctn.config.playFor", SimpleOption.emptyTooltip(), (t, v) -> GameOptions.getGenericValueText(t, Text.translatable(v.translationKey)), new SimpleOption.PotentialValuesBasedCallbacks<>(Arrays.asList(CtnConfig.PlayMode.values()), Codec.INT.xmap(CtnConfig.PlayMode.BY_ID::apply, CtnConfig.PlayMode::getId)), config.playFor, pm -> config.playFor = pm)
        };
    }

    private static Text doubleValue(Text text, double value) {
        return GameOptions.getGenericValueText(text, Text.literal(String.format("%.2f", value)));
    }
}
