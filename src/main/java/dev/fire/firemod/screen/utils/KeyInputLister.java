package dev.fire.firemod.screen.utils;


import com.sun.jna.platform.KeyboardUtils;
import it.unimi.dsi.fastutil.Hash;
import net.minecraft.client.Keyboard;
import net.minecraft.client.input.KeyCodes;
import org.lwjgl.glfw.GLFW;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyInputLister {
    private static Integer KeyCodes;
    public static ArrayList<Integer> validInputKeys = new ArrayList<>(List.of(
            GLFW.GLFW_KEY_A,
            GLFW.GLFW_KEY_B,
            GLFW.GLFW_KEY_C,
            GLFW.GLFW_KEY_D,
            GLFW.GLFW_KEY_E,
            GLFW.GLFW_KEY_F,
            GLFW.GLFW_KEY_G,
            GLFW.GLFW_KEY_H,
            GLFW.GLFW_KEY_I,
            GLFW.GLFW_KEY_J,
            GLFW.GLFW_KEY_K,
            GLFW.GLFW_KEY_L,
            GLFW.GLFW_KEY_M,
            GLFW.GLFW_KEY_N,
            GLFW.GLFW_KEY_O,
            GLFW.GLFW_KEY_P,
            GLFW.GLFW_KEY_Q,
            GLFW.GLFW_KEY_R,
            GLFW.GLFW_KEY_S,
            GLFW.GLFW_KEY_T,
            GLFW.GLFW_KEY_U,
            GLFW.GLFW_KEY_V,
            GLFW.GLFW_KEY_W,
            GLFW.GLFW_KEY_X,
            GLFW.GLFW_KEY_Y,
            GLFW.GLFW_KEY_Z,

            GLFW.GLFW_KEY_1,
            GLFW.GLFW_KEY_2,
            GLFW.GLFW_KEY_3,
            GLFW.GLFW_KEY_4,
            GLFW.GLFW_KEY_5,
            GLFW.GLFW_KEY_6,
            GLFW.GLFW_KEY_7,
            GLFW.GLFW_KEY_8,
            GLFW.GLFW_KEY_9,
            GLFW.GLFW_KEY_0,

            GLFW.GLFW_KEY_GRAVE_ACCENT,
            GLFW.GLFW_KEY_MINUS,
            GLFW.GLFW_KEY_EQUAL,
            //GLFW.GLFW_KEY_TAB,
            GLFW.GLFW_KEY_LEFT_BRACKET,
            GLFW.GLFW_KEY_RIGHT_BRACKET,
            GLFW.GLFW_KEY_BACKSLASH,
            GLFW.GLFW_KEY_SEMICOLON,
            GLFW.GLFW_KEY_APOSTROPHE,
            GLFW.GLFW_KEY_COMMA,
            GLFW.GLFW_KEY_PERIOD,
            GLFW.GLFW_KEY_SLASH,
            GLFW.GLFW_KEY_SPACE,
            192 //tilda
    ));

    public static HashMap<String, String> uppercaseSpecialCharaters = new HashMap<>(Map.ofEntries(
            Map.entry("1", "!"),
            Map.entry("2", "@"),
            Map.entry("3", "#"),
            Map.entry("4", "$"),
            Map.entry("5", "%"),
            Map.entry("6", "^"),
            Map.entry("7", "&"),
            Map.entry("8", "*"),
            Map.entry("9", "("),
            Map.entry("0", ")"),

            Map.entry("-", "_"),
            Map.entry("=", "+"),
            Map.entry("`", "~"),

            Map.entry("[", "{"),
            Map.entry("]", "}"),
            Map.entry("\\", "|"),

            Map.entry(";", ":"),
            Map.entry("'", "\""),

            Map.entry(",", "<"),
            Map.entry(".", ">"),
            Map.entry("/", "?")


            ));
}
