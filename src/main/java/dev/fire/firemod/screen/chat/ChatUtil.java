package dev.fire.firemod.screen.chat;
import dev.fire.firemod.Firemod;
import net.minecraft.text.*;
import net.minecraft.util.Colors;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ChatUtil {
    public static int SUPPORT_COLOR = 0x557FD4;
    public static int VIP_COLOR = 0xFFD47F;
    public static Text supportAddons(Text message) {
        Text return_message = message;

        List<Text> siblings = message.getSiblings();
        Firemod.LOGGER.info(siblings.toString());
        if (siblings.get(0) == Text.literal("[SUPPORT] ").withColor(SUPPORT_COLOR) &&
            siblings.get(2) == Text.literal(" joined the support queue. ").withColor(Colors.GRAY) &&
            siblings.get(3) == Text.literal("▶ ").withColor(0xAAD4FF)
            ) {
            String player = siblings.get(1).getLiteralString();
            Firemod.LOGGER.info(player + " entered the queue!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            return_message = Text.empty()
                    .append(message)
                    .append(Text.literal(" [ACCEPT]").withColor(VIP_COLOR))
                    .styled(style -> style
                            .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/support accept " + player))
                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("Click to accept support session.").withColor(VIP_COLOR)))
                    );
        }

        return return_message;
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