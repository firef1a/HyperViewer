package dev.fire.firemod.screen.utils;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import javax.security.auth.callback.Callback;

public class RenderableRectButton extends RenderableRectangleObject{
    private TextRenderer textRenderer;
    public Text text;
    private Callback callback;
    private RenderableRectangleObject listRect;
    private int hightlightColor;
    private int clickColor;
    public int clickID;


    public RenderableRectButton(TextRenderer textRenderer, Text text,  int x, int y, int width, int height, int color, int hightlightColor, int clickColor, int clickID) {
        super(x, y, width, height, color);
        this.textRenderer = textRenderer;
        this.text = text;
        this.listRect = new RenderableRectangleObject(0,0,0,0);
        this.hightlightColor = hightlightColor;
        this.clickColor = clickColor;
        this.clickID = clickID;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, int parentx, int parenty, int parentWidth, int parentHeight) {
        int dx = x+parentx + (parentWidth*this.xBinding);
        int dy = y+parenty + (parentHeight*this.yBinding);
        Point center = new Point(dx+(width/2),dy+(height/2));


        preSiblings.forEach(obj -> obj.render(context, mouseX, mouseY, dx,dy,dx+width,dy+height));

        int drawColor;
        if (mouseX > dx && mouseX < dx+width && mouseY > dy && mouseY < dy+height) {
            drawColor = this.hightlightColor;
        } else {
            drawColor = this.color;
        }


        context.fill(dx, dy, dx+width, dy+height, drawColor);
        context.drawCenteredTextWithShadow(textRenderer,text,center.x,(center.y-(textRenderer.fontHeight/2))+1,0xffffff);

        siblings.forEach(obj -> obj.render(context, mouseX, mouseY, dx,dy,dx+width,dy+height));

        if (this.topBorder.enabled) { context.fill(dx, dy-this.topBorder.size, dx+width, dy, this.topBorder.color); }
        if (this.bottomBorder.enabled) { context.fill(dx, dy+height, dx+width, dy+height+this.bottomBorder.size, this.bottomBorder.color); }
        if (this.rightBorder.enabled) { context.fill(dx+width, dy, dx+width+this.rightBorder.size, dy+height, this.rightBorder.color); }
        if (this.leftBorder.enabled) { context.fill(dx-this.leftBorder.size, dy, dx, dy+height, this.leftBorder.color); }
    }
}
