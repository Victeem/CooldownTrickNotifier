package gay.plat.ctn;

import gay.plat.ctn.config.CtnConfig;
import net.fabricmc.api.ClientModInitializer;
import net.uku3lig.ukulib.config.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CooldownTrickNotifier implements ClientModInitializer {
    public static final String MOD_ID = "ctn";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final ConfigManager<CtnConfig> configManager = ConfigManager.create(CtnConfig.class, MOD_ID);

	@Override
	public void onInitializeClient() {
        LOGGER.info("ClientInit");
	}
}