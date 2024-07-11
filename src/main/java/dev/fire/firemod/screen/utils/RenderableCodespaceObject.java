package dev.fire.firemod.screen.utils;

import com.mojang.datafixers.kinds.IdF;
import dev.fire.firemod.screen.CodeScreen;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Function;

import static java.awt.SystemColor.text;

public class RenderableCodespaceObject extends RenderableRectangleObject {
    private TextRenderer textRenderer;
    public int x;
    public int y;
    public int width;
    public int height;
    public int scrollingX;
    public int scrollingY;
    public ArrayList<RenderableCodespaceObject> siblings;
    public ArrayList<RenderableCodespaceObject> preSiblings;
    public ArrayList<Widget> widgets;
    public int color;
    public RenderableCodespaceObject parent;
    public RenderableCodespaceObject listRect;

    public int xBinding = 0;
    public int yBinding = 0;

    public RectBorder topBorder = new RectBorder(false);
    public RectBorder rightBorder = new RectBorder(false);
    public RectBorder bottomBorder = new RectBorder(false);
    public RectBorder leftBorder = new RectBorder(false);

    public CodeScreen codeScreen;


    public RenderableCodespaceObject(TextRenderer textRenderer, int x, int y, int width, int height, int color, CodeScreen codeScreen) {
        super(x, y, width, height, color);
        this.textRenderer = textRenderer;
        this.siblings = new ArrayList<RenderableCodespaceObject>();
        this.preSiblings = new ArrayList<RenderableCodespaceObject>();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.codeScreen = codeScreen;
    }


    public void setBinding(int xb, int yb) {
        this.xBinding = xb;
        this.yBinding = yb;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, int parentx, int parenty, int parentWidth, int parentHeight) {
        int dx = x+parentx + (parentWidth*this.xBinding);
        int dy = y+parenty + (parentHeight*this.yBinding);

        preSiblings.forEach(obj -> obj.render(context, mouseX, mouseY, dx,dy,dx+width,dy+height));

        context.fill(dx, dy, dx+width, dy+height, color);

        siblings.forEach(obj -> obj.render(context, mouseX, mouseY, dx,dy,dx+width,dy+height));

        FunctionEntry function = codeScreen.functionEntryList.get(codeScreen.focusedFunctionTabIndex);

        int index = 0;
        int lineNum;
        int tx;
        int ty;
        int margin = 10;
        int lineHeight = 11;
        // 7 is text height
        for (String text : function.data) {
            tx = dx + margin;
            ty = (int) ((dy+index*(lineHeight)) + margin + codeScreen.codespaceScrollOffset*2);
            lineNum = index+1;

            MutableText linePrefix = Text.literal(String.valueOf(lineNum)).withColor(Colors.GRAY).append(Text.literal( "  | ").withColor(0x00A78));
            MutableText codeLine = linePrefix.append(Text.literal(text).withColor(0xffffff));
            context.drawText(textRenderer,codeLine,tx,ty,0xffffff, false);
            index++;
        }

        if (this.topBorder.enabled) { context.fill(dx, dy-this.topBorder.size, dx+width, dy, this.topBorder.color); }
        if (this.bottomBorder.enabled) { context.fill(dx, dy+height, dx+width, dy+height+this.bottomBorder.size, this.bottomBorder.color); }
        if (this.rightBorder.enabled) { context.fill(dx+    width, dy, dx+width+this.rightBorder.size, dy+height, this.rightBorder.color); }
        if (this.leftBorder.enabled) { context.fill(dx-this.leftBorder.size, dy, dx, dy+height, this.leftBorder.color); }
    }
}
