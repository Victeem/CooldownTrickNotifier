package gay.plat.ctn;

import gay.plat.ctn.config.CtnConfigScreen;
import net.minecraft.client.gui.screen.Screen;
import net.uku3lig.ukulib.api.UkulibAPI;
import net.uku3lig.ukulib.config.screen.AbstractConfigScreen;

import java.util.function.Function;

public class UkulibHook implements UkulibAPI {
    @Override
    public Function<Screen, AbstractConfigScreen<?>> supplyConfigScreen() {
        return parent -> new CtnConfigScreen(parent, CooldownTrickNotifier.configManager);
    }
}
