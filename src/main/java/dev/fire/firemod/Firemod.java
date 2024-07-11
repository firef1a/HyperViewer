package dev.fire.firemod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import dev.fire.firemod.screen.chat.ChatManager;
import dev.fire.firemod.screen.external.SimplepushNotificationManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;

public class Firemod implements ModInitializer {
	public static final String MOD_NAME = "Fire Mod";
	public static final String MOD_ID = "firemod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
	public static final MinecraftClient MC = MinecraftClient.getInstance();
	public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public static SimplepushNotificationManager MOBILE_NOTIFICATION_MANAGER = new SimplepushNotificationManager("WTSv6D");

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



		LOGGER.info("firemod initalized!");
	}

	public void onClose() {
		LOGGER.info("Closed");
	}

}