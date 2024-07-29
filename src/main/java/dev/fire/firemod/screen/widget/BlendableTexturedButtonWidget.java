package dev.fire.firemod.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.util.Identifier;

// copied from authme mod
public class BlendableTexturedButtonWidget extends TexturedButtonWidget {

    public BlendableTexturedButtonWidget(int x, int y, int width, int height, Identifier unfocused, Identifier focused, PressAction pressAction) {
        super(x, y, width, height, new ButtonTextures(unfocused, focused), pressAction);
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        RenderSystem.clearColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        super.renderWidget(context, mouseX, mouseY, delta);
    }
}