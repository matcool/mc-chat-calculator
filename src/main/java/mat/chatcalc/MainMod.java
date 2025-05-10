package mat.chatcalc;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MainMod.MODID)
public class MainMod {
	public static final String MODID = "chatcalculator";

	public MainMod(FMLJavaModLoadingContext context) {
		MinecraftForge.EVENT_BUS.register(this);
		context.registerConfig(ModConfig.Type.COMMON, Config.SPEC, "chatcalculator.toml");
	}

	@SubscribeEvent
	public void onClientChat(ClientChatEvent event) {
		if (event.getMessage().startsWith("=")) {
			var player = Minecraft.getInstance().player;
			if (player == null) {
				return;
			}
			var input = event.getMessage().substring(1).trim();
			player.sendSystemMessage(Component.literal("= " + input));
			// var output = mat.chatcalc.parser.Parser.parse(input);
			// player.sendSystemMessage(Component.literal("§7§o" + output));
			event.setCanceled(true);
		}
	}
}
