package dev.fire.firemod.screen.utils;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import javax.security.auth.callback.Callback;

public class RenderableRectButton extends RenderableRectangleObject{
    public Text text;
    private Callback callback;
    public RenderableRectButton(Text text, Callback callback, int x, int y, int width, int height, int color) {
        super(x, y, width, height, color);
        this.text = text;
        this.callback = callback;
    }

    @Override
    public void render(DrawContext context, int parentx, int parenty, int parentWidth, int parentHeight) {
        int dx = x+parentx + (parentWidth*this.xBinding);
        int dy = y+parenty + (parentHeight*this.yBinding);

        preSiblings.forEach(obj -> {
            obj.render(context,dx,dy,width,height);
        });

        context.fill(dx, dy, dx+width, dy+height, color);

        siblings.forEach(obj -> {
            obj.render(context,dx,dy,width,height);
        });

        if (this.topBorder.enabled) { context.fill(dx, dy-this.topBorder.size, dx+width, dy, this.topBorder.color); }
        if (this.bottomBorder.enabled) { context.fill(dx, dy+height, dx+width, dy+height+this.bottomBorder.size, this.bottomBorder.color); }
        if (this.rightBorder.enabled) { context.fill(dx+width, dy, dx+width+this.rightBorder.size, dy+height, this.rightBorder.color); }
    }
}
