package mat.chatcalc;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = MainMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

	private static final ForgeConfigSpec.BooleanValue ENABLE_MOD =
		BUILDER.comment("Whether the mod is enabled and the chat messages are eaten.").define("enableMod", true);

	static final ForgeConfigSpec SPEC = BUILDER.build();

	public static boolean enableMod;

	@SubscribeEvent
	static void onLoad(final ModConfigEvent event) {
		enableMod = ENABLE_MOD.get();
	}
}
