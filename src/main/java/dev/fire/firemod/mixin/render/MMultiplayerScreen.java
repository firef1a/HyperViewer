package dev.fire.firemod.mixin.render;

import dev.fire.firemod.Firemod;
import dev.fire.firemod.screen.CodeScreen;
import dev.fire.firemod.screen.widget.BlendableTexturedButtonWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(MultiplayerScreen.class)
public class MMultiplayerScreen extends Screen {
    @Unique
    private final Identifier identifier_main = new Identifier(Firemod.MOD_ID + ":scripts");
    @Unique
    private final Identifier identifier_main_highlight = new Identifier(Firemod.MOD_ID + ":scripts_highlight");

    protected MMultiplayerScreen(Text title) {
        super(title);
    }
    @Inject(at = @At("HEAD"), method = "init", cancellable = true)
    private void init(CallbackInfo ci) {
        Firemod.LOGGER.info("testing");
        this.addDrawableChild(new BlendableTexturedButtonWidget(100, 5, 20, 20, identifier_main, identifier_main_highlight, (button) -> {
            CodeScreen screen = new CodeScreen(Text.literal("hi"), this);
            Firemod.MC.setScreen(screen);
        }));
    }
}