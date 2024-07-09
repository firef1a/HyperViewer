package dev.fire.firemod.screen.chat;

import dev.fire.firemod.Firemod;
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
    public static void sendMessageToPlayerDisplay(Text content) {
        assert Firemod.MC.player != null;
        Firemod.MC.player.sendMessage(content);
    }
}
