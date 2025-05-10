package mat.chatcalc;

import mat.chatcalc.parser.ParseException;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(MainMod.MODID)
public class MainMod {
	public static final String MODID = "chatcalculator";

	public MainMod() {
		MinecraftForge.EVENT_BUS.register(this);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC, "chatcalculator.toml");
	}

	private static Calculator calc = new Calculator();

	@SubscribeEvent
	public void onClientChat(ClientChatEvent event) {
		if (event.getMessage().startsWith("=") && Config.enableMod) {
			var player = Minecraft.getInstance().player;
			if (player == null) {
				return;
			}
			var input = event.getMessage().substring(1).trim();
			player.sendSystemMessage(Component.literal("= " + input));
			try {
				double value = calc.execute(input);
				String output = "" + value;
				// Dont worry about it
				if (output.endsWith(".0")) {
					output = output.substring(0, output.length() - 2);
				}
				player.sendSystemMessage(Component.literal("§a§o " + output));
			} catch (ParseException e) {
				player.sendSystemMessage(Component.literal("§c§o Error: " + e.getMessage()));
			}
			event.setCanceled(true);
		}
	}
}
