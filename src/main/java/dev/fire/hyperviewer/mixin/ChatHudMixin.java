package dev.fire.hyperviewer.mixin;

import dev.fire.hyperviewer.HyperViewer;
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
        HyperViewer.CHAT_LOGGER.appendChat(message);
        HyperViewer.LOGGER.info(String.valueOf(message));

        return message;
    }
}