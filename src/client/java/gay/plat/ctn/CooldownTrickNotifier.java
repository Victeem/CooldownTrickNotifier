package gay.plat.ctn;

import eu.midnightdust.lib.config.MidnightConfig;
import gay.plat.ctn.config.CooldownTrickNotifierConfig;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CooldownTrickNotifier implements ClientModInitializer {
	public static final String MODID = "ctn";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitializeClient() {
		LOGGER.info("ModInit");
		MidnightConfig.init(MODID, CooldownTrickNotifierConfig.class);
	}
}