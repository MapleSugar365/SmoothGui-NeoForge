package ezzenix.smoothgui;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@OnlyIn(Dist.CLIENT)
@Mod(SmoothGui.MOD_ID)
public class SmoothGui {
	public static final String MOD_ID = "smoothgui";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static long lastGuiOpenedTime = 0;

	public SmoothGui(IEventBus modEventBus) {
		LOGGER.info("SmoothGui initialized!");
	}

	private static float easeInBack(float t) {
		float c1 = 1.70158f;
		float c3 = c1 + 1;
		return c3 * t * t * t - c1 * t * t;
	}

	public static float getOffsetY() {
		Minecraft client = Minecraft.getInstance();
		float FADE_TIME = 220;
		float FADE_OFFSET = 9;
		float screenFactor = (float) client.getWindow().getHeight() / 1080;
		float timeSinceOpen = Math.min((float) (System.currentTimeMillis() - SmoothGui.lastGuiOpenedTime), FADE_TIME);
		float alpha = 1 - (timeSinceOpen / FADE_TIME);
		float modifiedAlpha = easeInBack(alpha);
		return modifiedAlpha * FADE_OFFSET * screenFactor;
	}

	public static boolean isInMenu() {
		Minecraft client = Minecraft.getInstance();
		return client.level == null && client.player == null;
	}
}