package gay.plat.ctn;

import gay.plat.ctn.config.CtnConfigScreen;
import net.minecraft.client.gui.screens.Screen;
import net.uku3lig.ukulib.api.UkulibAPI;

import java.util.function.UnaryOperator;

public class UkulibHook implements UkulibAPI {
    @Override
    public UnaryOperator<Screen> supplyConfigScreen() {
        return CtnConfigScreen::new;
    }
}
