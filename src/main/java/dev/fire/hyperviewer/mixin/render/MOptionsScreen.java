package dev.fire.hyperviewer.mixin.render;

import dev.fire.hyperviewer.HyperViewer;
import dev.fire.hyperviewer.screen.screens.CodeScreen;
import dev.fire.hyperviewer.screen.widget.BlendableTexturedButtonWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.option.OptionsScreen;


@Mixin(OptionsScreen.class)
public class MOptionsScreen extends Screen {
    public MOptionsScreen(Text literalText) {
        super(literalText);
    }

    @Unique
    private final Identifier identifier_main = new Identifier(HyperViewer.MOD_ID + ":script_button");
    @Unique
    private final Identifier identifier_main_highlight = new Identifier(HyperViewer.MOD_ID + ":script_button_highlight");

    @Inject(method = "init()V", at = @At("RETURN"))
    private void init(CallbackInfo ci) {
        HyperViewer.LOGGER.info("testing");
        this.addDrawableChild(new BlendableTexturedButtonWidget(5, 30, 20, 20, identifier_main, identifier_main_highlight, (button) -> {
              CodeScreen screen = new CodeScreen(Text.literal("hi"), this);
              HyperViewer.MC.setScreen(screen);
        }));
    }
}