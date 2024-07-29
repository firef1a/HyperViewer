package dev.fire.firemod.mixin;

import dev.fire.firemod.Firemod;
import dev.fire.firemod.screen.chat.ChatUtil;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.io.IOException;


@Mixin(ChatHud.class)
public class ChatHudMixin {
    @ModifyVariable(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    public Text inject(Text message) throws IOException, InterruptedException {
        Firemod.CHAT_LOGGER.chatLog.add(message);
        //Firemod.LOGGER.info(String.valueOf(message));

        message = ChatUtil.supportAddons(message);
        return message;
    }
}