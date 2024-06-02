package dev.fire.firemod.client;

import dev.fire.firemod.event.KeyInputHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class FiremodClient implements ClientModInitializer {
    private static KeyBinding keyBinding;

    // The KeyBinding declaration and registration are commonly executed here statically

    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();


    }
}