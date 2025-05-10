package mat.chatcalc;

import com.mojang.logging.LogUtils;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ExampleMod.MODID)
public class ExampleMod {
	public static final String MODID = "chatcalculator";
	private static final Logger LOGGER = LogUtils.getLogger();

	public ExampleMod(FMLJavaModLoadingContext context) {
		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);

		// Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
		context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
	}

	@SubscribeEvent
	public void onClientChat(ClientChatEvent event) {
		if (event.getMessage().startsWith("=")) {
			// Minecraft.getInstance().player.sendSystemMessage("ChatCalc is running!");
			LOGGER.info("Hello! " + event.getMessage());
			event.setCanceled(true);
		}
	}
}
