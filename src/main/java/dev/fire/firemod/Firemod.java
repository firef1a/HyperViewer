package dev.fire.firemod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.fire.firemod.screen.chat.ChatLogger;
import dev.fire.firemod.external.SimplepushNotificationManager;
import dev.fire.firemod.viewer.FunctionDataManager;
import dev.fire.firemod.viewer.FunctionFinder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Firemod implements ModInitializer {
	public static final String MOD_NAME = "Fire Mod";
	public static final String MOD_ID = "firemod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
	public static final MinecraftClient MC = MinecraftClient.getInstance();
	public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public static SimplepushNotificationManager MOBILE_NOTIFICATION_MANAGER = new SimplepushNotificationManager("WTSv6D");
	public static ChatLogger CHAT_LOGGER = new ChatLogger();

	public static String MOD_VERSION;

	public static String PLAYER_UUID = null;
	public static String PLAYER_NAME = null;

	public static FunctionFinder functionFinder;
	public static FunctionDataManager functionDataManager;

	@Override
	public void onInitialize() {
		ClientTickEvents.START_CLIENT_TICK.register(client -> {});
		LOGGER.info("Initializing..");

		Runtime.getRuntime().addShutdownHook(new Thread(this::onClose));
		// allows FileDialog class to open without a HeadlessException
		System.setProperty("java.awt.headless", "false");

		PLAYER_UUID = MC.getSession().getUuidOrNull().toString();
		PLAYER_NAME = MC.getSession().getUsername();

		MOD_VERSION = FabricLoader.getInstance().getModContainer(MOD_ID).get().getMetadata().getVersion().getFriendlyString();

		functionDataManager = new FunctionDataManager();
		functionFinder = new FunctionFinder();
		LOGGER.info("smoke me a kipper, i'll be back for breakfast");
	}

	public void onClose() {
		LOGGER.info("Closed");
	}

	public static void onTick() {
		functionFinder.tick();
	}

}