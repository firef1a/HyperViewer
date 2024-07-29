package dev.fire.firemod.screen.chat;

import dev.fire.firemod.Firemod;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.Objects;

public class ChatManager {
    public static void sendMessageAsPlayer(String content) {
        if (content.charAt(0) == '/') {
            Objects.requireNonNull(Firemod.MC.getNetworkHandler()).sendChatCommand(content.substring(1));
        } else {
            Objects.requireNonNull(Firemod.MC.getNetworkHandler()).sendChatMessage(content);
        }
    }
    private static void sendMessageToPlayerDisplay(Text content) {
        assert Firemod.MC.player != null;
        Firemod.MC.player.sendMessage(content);
    }

    public static void displayChatMessageToPlayer(Text content) {
        if (Firemod.MC.player != null) {
            Firemod.MC.player.sendMessage(Text.literal("[FIREMOD]").withColor(0xed743b).append(Text.literal(" ").append(content)));
        }
    }

}
