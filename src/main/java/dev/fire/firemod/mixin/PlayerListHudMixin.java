package dev.fire.firemod.mixin;

import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerListHud.class)
public class PlayerListHudMixin {

    @Inject(method = "getPlayerName", at = @At("RETURN"), cancellable = true)
    protected void getPlayerName(CallbackInfoReturnable<Text> cir) {
        //Text new_tag_name = TextUtil.replaceTags(cir.getReturnValue(), true);
        //cir.setReturnValue(new_tag_name);
    }
}