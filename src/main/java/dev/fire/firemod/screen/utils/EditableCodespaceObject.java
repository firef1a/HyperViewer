package dev.fire.firemod.screen.utils;

import dev.fire.firemod.devutils.MathUtils;
import dev.fire.firemod.screen.CodeScreen;
import dev.fire.firemod.screen.utils.templateUtils.CodeLine;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;

public class EditableCodespaceObject  extends RenderableRectangleObject {
    private TextRenderer textRenderer;
    public int x;
    public int y;
    public int width;
    public int height;
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


    public EditableCodespaceObject(TextRenderer textRenderer, int x, int y, int width, int height, int color, CodeScreen codeScreen) {
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
        int dx = (int) (x+parentx + (parentWidth*this.xBinding) + this.scrollingX);
        int dy = (int) (y+parenty + (parentHeight*this.yBinding) + this.scrollingY);

        preSiblings.forEach(obj -> obj.render(context, mouseX, mouseY, dx,dy,dx+width,dy+height));

        context.fill(dx, dy, dx+width, dy+height, color);

        siblings.forEach(obj -> obj.render(context, mouseX, mouseY, dx,dy,dx+width,dy+height));

        if (!codeScreen.functionEntryList.isEmpty()) {
            FunctionEntry function = codeScreen.functionEntryList.get(codeScreen.focusedFunctionTabIndex);

            int single_width = textRenderer.getWidth("x");

            int index = 0;
            int lineNum;

            int marginx = ((String.valueOf(function.formattedCodeList.size()).length() + 1) * single_width) + 2;
            int marginy = 10;
            int textHeight = 7;
            //int lineHeight = 11;
            int lineHeight = 12;

            int tx, ty, clx, lpx, llx;
            String indentAddString = "    ";

            // 7 is text height
            for (CodeLine line : function.formattedCodeList) {
                MutableText text = (MutableText) line.text;
                int indent = line.indent;

                MutableText indentText = Text.empty();
                for (int level = 0; level < indent; level++) {
                    indentText.append(indentAddString);
                }
                text = indentText.append(text);

                tx = dx + marginx;
                ty = (int) ((dy + index * (lineHeight)) + marginy);
                lineNum = index + 1;

                lpx = tx - (String.valueOf(lineNum).length() * single_width);
                MutableText linePrefix = Text.literal(String.valueOf(lineNum)).withColor(Formatting.GRAY.getColorValue());
                context.drawText(textRenderer, linePrefix, lpx, ty, 0xffffff, false);

                //llx = tx + (single_width/2);
                //context.drawText(textRenderer,Text.literal("|"),llx,ty,0xffffff, false);

                clx = tx + single_width;
                MutableText codeLine = text.copy().withColor(0xffffff);
                context.drawText(textRenderer, codeLine, clx, ty, 0xffffff, false);
                index++;

                if (indent > 1 && false) {
                    int indentx;
                    for (int i = 0; i < (indent - 1); i++) {
                        indentx = clx + ((i + 1) * textRenderer.getWidth(indentAddString));
                        context.fill(0, 0, 1000, 1000, 0x246dff);
                        context.fill(indentx, ty, indentx + 2, ty + lineHeight, 0x3a3c40);
                        context.drawText(textRenderer, Text.literal("|"), indentx, ty, 0x3a3c40, false);
                    }
                }
            }
        }

        if (this.topBorder.enabled)    { context.fill(dx, dy-this.topBorder.size, dx+width, dy, this.topBorder.color); }
        if (this.bottomBorder.enabled) { context.fill(dx, dy+height, dx+width, dy+height+this.bottomBorder.size, this.bottomBorder.color); }
        if (this.rightBorder.enabled)  { context.fill(dx+    width, dy, dx+width+this.rightBorder.size, dy+height, this.rightBorder.color); }
        if (this.leftBorder.enabled)   { context.fill(dx-this.leftBorder.size, dy, dx, dy+height, this.leftBorder.color); }

        this.scrollingX = MathUtils.lerp(this.scrollingX, this.lerpcrollingX, this.lerpScrollAmount);
        this.scrollingY = MathUtils.lerp(this.scrollingY, this.lerpcrollingY, this.lerpScrollAmount);
    }
}
