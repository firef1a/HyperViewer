package dev.fire.firemod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ModInitializer;
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

	public static String MOD_VERSION;

	public static String PLAYER_UUID = null;
	public static String PLAYER_NAME = null;

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing");
		Runtime.getRuntime().addShutdownHook(new Thread(this::onClose));

		// allows FileDialog class to open without a HeadlessException
		System.setProperty("java.awt.headless", "false");

		PLAYER_UUID = MC.getSession().getUuidOrNull().toString();
		PLAYER_NAME = MC.getSession().getUsername();

		MOD_VERSION = FabricLoader.getInstance().getModContainer(MOD_ID).get().getMetadata().getVersion().getFriendlyString();

		LOGGER.info("init");
	}

	public void onClose() {
		LOGGER.info("Closed");
	}

}