package dev.fire.firemod.event;

import dev.fire.firemod.Firemod;
import dev.fire.firemod.screen.CodeScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY = Firemod.MOD_NAME;

    public static KeyBinding openMenuKeybinding;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (openMenuKeybinding.wasPressed()) {
                if (!(Firemod.MC.currentScreen instanceof CodeScreen)) {
                    CodeScreen screen = new CodeScreen(Text.literal("hi"), Firemod.MC.currentScreen);
                    Firemod.MC.setScreen(screen);
                }
            }
        });
    }
    public static void register() {
        openMenuKeybinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Open menu", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_Y, // The keycode of the key
                KEY_CATEGORY // The translation key of the keybinding's category.
        ));
        registerKeyInputs();

    }
}
