package dev.fire.hyperviewer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.fire.hyperviewer.screen.chat.ChatLogger;
import dev.fire.hyperviewer.viewer.FunctionDataManager;
import dev.fire.hyperviewer.viewer.FunctionFinder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public class HyperViewer implements ModInitializer {
	public static final String MOD_NAME = "HyperViewer";
	public static final String MOD_ID = "hyperviewer";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
	public static final MinecraftClient MC = MinecraftClient.getInstance();
	public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

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

		LOGGER.info(getRandomDebugMessage());
	}

	private static String getRandomDebugMessage() {
		ArrayList<String> messageList = new ArrayList<>(List.of(
				"smoke me a kipper, i'll be back for breakfast",
				"blazingly fast since 2024",
				"not a tokenlogger tm"
		));

		Random rand = new Random();

		int n = rand.nextInt(messageList.size()-1);
		return messageList.get(n);
	}

	public void onClose() {
		LOGGER.info("Closed");
	}

	public static void onTick() {
		functionFinder.tick();
	}
}