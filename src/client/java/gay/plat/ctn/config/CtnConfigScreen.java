package gay.plat.ctn.config;

import gay.plat.ctn.CooldownTrickNotifier;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.uku3lig.ukulib.config.option.*;
import net.uku3lig.ukulib.config.screen.AbstractConfigScreen;

public class CtnConfigScreen extends AbstractConfigScreen<CtnConfig> {
    public CtnConfigScreen(Screen parent) {
        super("ctn.config.title", parent, CooldownTrickNotifier.configManager);
    }

    @Override
    protected WidgetCreator[] getWidgets(CtnConfig config) {
        return new WidgetCreator[]{
                CyclingOption.ofBoolean("ctn.config.enabled", config.enabled, b -> config.enabled = b),
                new SliderOption("ctn.config.volume", config.volume, d -> config.volume = (float)d, SliderOption.PERCENT_VALUE_TO_TEXT, 0, 1, 0.05),
                new SliderOption("ctn.config.pitch", config.pitch, d -> config.pitch = (float)d, SliderOption.PERCENT_VALUE_TO_TEXT, 0, 2, 0.05),
                new InputOption("ctn.config.sound", config.sound, s -> config.sound = s, s -> BuiltInRegistries.SOUND_EVENT.containsKey(Identifier.tryParse(s))),
                CyclingOption.ofEnum("ctn.config.playFor", CtnConfig.PlayMode.class, config.playFor, pm -> config.playFor = pm)
        };
    }
}
