package dev.fire.firemod.screen.chat;
import dev.fire.firemod.Firemod;
import dev.fire.firemod.screen.external.DiscordWebhookManager;
import dev.fire.firemod.screen.external.SystemIOManager;
import dev.fire.firemod.screen.utils.FunctionEntry;
import net.minecraft.text.*;
import net.minecraft.util.Colors;
import net.minecraft.util.Formatting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ChatUtil {
    public static int SUPPORT_COLOR = 0x557FD4;
    public static int VIP_COLOR = 0xFFD47F;
    public static int VIP_BRACKET_COLOR = 0xFFAA00;
    public static Text supportAddons(Text message) throws IOException, InterruptedException {
        Text return_message = message;

        List<Text> siblings = message.getSiblings();

        Text supportPrefix = Text.literal("[SUPPORT] ").withColor(SUPPORT_COLOR);

        if (isListIndexEqual(siblings, 0, supportPrefix)) {
            //ChatManager.sendMessageToPlayerDisplay(Text.literal("support message detected!!").withColor(Colors.LIGHT_RED));
            if (isListIndexEqual(siblings, 2, Text.literal(" joined the support queue. ").formatted(Formatting.GRAY))) {
                String player = getContent(siblings.get(1));
                String reason = getContent(siblings.get(4));

                SystemIOManager.sendDesktopNotification(player + " requested a support session", reason, 1000*5, "critical");
                //DiscordWebhookManager.sendWebhookMessage("<@464132019300073479> " + player + " joined the support queue. ▶ " + reason);

                Firemod.MOBILE_NOTIFICATION_MANAGER.sendMobileNotification(player + " entered the queue.", reason);

                return_message = Text.empty()
                        .append(message)
                        .append(Text.literal(" [ACCEPT]").withColor(VIP_COLOR))
                        .styled(style -> style
                                .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/support accept " + player))
                                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("Click to accept support session with ").withColor(VIP_COLOR).append(Text.literal(player.toString()).withColor(VIP_BRACKET_COLOR))))
                        );
            }
        }
        return return_message;
    }


    public static boolean isListIndexEqual(List list, int index, Object value) {
        if (list.size() >= index+1) {
            return list.get(index).equals(value);
        }
        return false;
    }



    private static Text replaceTextInternal(Text text, Text find, Text replace, Boolean skip) {
        MutableText newText = MutableText.of(text.getContent()).setStyle(text.getStyle());

        boolean empty = getContent(text).isEmpty();
        boolean hideSpace = false;
        for (Text sibling : text.getSiblings()) {
            String content = getContent(sibling);
            if (hideSpace) {
                hideSpace = false;
                if (content.equals(" ")) {
                    continue;
                }
            }

            if (sibling.equals(find)) {
                newText.append(Text.empty().append(replace));
                if (skip) {
                    hideSpace = true;
                }
                continue;
            }

            newText.append(replaceTextInternal(sibling, find, replace, skip));
        }
        return newText;
    }

    private static String getContent(Text text) {
        if (text.getContent() instanceof PlainTextContent content) {
            return content.string();
        }
        return "";
    }
}