package dev.fire.hyperviewer.screen.chat;

import dev.fire.hyperviewer.HyperViewer;
import net.minecraft.text.Text;

import java.util.Objects;

public class ChatManager {
    public static void sendMessageAsPlayer(String content) {
        if (content.charAt(0) == '/') {
            Objects.requireNonNull(HyperViewer.MC.getNetworkHandler()).sendChatCommand(content.substring(1));
        } else {
            Objects.requireNonNull(HyperViewer.MC.getNetworkHandler()).sendChatMessage(content);
        }
    }
    private static void sendMessageToPlayerDisplay(Text content) {
        assert HyperViewer.MC.player != null;
        HyperViewer.MC.player.sendMessage(content);
    }

    public static void displayChatMessageToPlayer(Text content) {
        if (HyperViewer.MC.player != null) {
            HyperViewer.MC.player.sendMessage(Text.literal("[HYPERVIEWER]").withColor(0xed743b).append(Text.literal(" ").append(content)));
        }
    }

}
