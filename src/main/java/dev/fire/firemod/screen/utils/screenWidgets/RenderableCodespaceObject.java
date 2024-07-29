package dev.fire.firemod.screen.utils.screenWidgets;

import dev.fire.firemod.Firemod;
import dev.fire.firemod.devutils.MathUtils;
import dev.fire.firemod.screen.screens.CodeScreen;
import dev.fire.firemod.screen.utils.FunctionEntry;
import dev.fire.firemod.screen.utils.Point;
import dev.fire.firemod.screen.utils.RectBorder;
import dev.fire.firemod.screen.utils.templates.CodeLine;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

public class RenderableCodespaceObject extends RenderableRectangleObject {
    private static TextRenderer textRenderer;
    public int x;
    public int y;
    public static int width;
    public static int height;
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

    public static CodeScreen codeScreen;
    private boolean isFocused = false;

    public static int lineHeight = 12;


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

    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        int movex = 0;
        int movey = 0;
        if (keyCode == GLFW.GLFW_KEY_LEFT) { movex = 1; }
        if (keyCode == GLFW.GLFW_KEY_RIGHT) { movex = -1; }
        if (keyCode == GLFW.GLFW_KEY_UP) { movey = 1; }
        if (keyCode == GLFW.GLFW_KEY_DOWN) { movey = -1; }

        double addX = movex*22f;
        double addY = movey*22f;

        double max_codespace_scrollY = getMaxScrollY();
        double max_codespace_scrollX = getMaxScrollX();

        if (isFocused) {
            // Y
            if (lerpcrollingY + addY >= 0) {
                lerpcrollingY = 0;
            } else if (lerpcrollingY + addY <= max_codespace_scrollY) {
                lerpcrollingY = (double) max_codespace_scrollY;
            } else {
                lerpcrollingY += addY;
            }
            // X
            if (lerpcrollingX + addX >= 0) {
                lerpcrollingX = 0;
            } else if (lerpcrollingX + addX <= max_codespace_scrollX) {
                lerpcrollingX = (double) max_codespace_scrollX;
            } else {
                lerpcrollingX += addX;
            }
        }

    }

    public static int getMaxScrollX() {
        int max_codespace_scrollX = 0;
        int margin = 300;
        if (!codeScreen.functionEntryList.isEmpty()) {
            FunctionEntry functionEntry = codeScreen.functionEntryList.get(Firemod.functionDataManager.focusedFunctionEntry);
            String getCode = functionEntry.formattedCodeList.get(functionEntry.longestline).text.getString();
            int codeLength = textRenderer.getWidth(getCode);
            if (codeLength-50 > width) {
                max_codespace_scrollX = -1 * ((codeLength-width)+margin);
            }
        }
        return max_codespace_scrollX;
    }


    public static int getMaxScrollY() {
        int codespace_size_before_scroll = 30;
        int max_codespace_scrollY = 0;
        int margin = 5;
        if (!codeScreen.functionEntryList.isEmpty() && codeScreen.functionEntryList.get(Firemod.functionDataManager.focusedFunctionEntry).formattedCodeList.size() > codespace_size_before_scroll) {
            ArrayList<CodeLine> getCode = codeScreen.functionEntryList.get(Firemod.functionDataManager.focusedFunctionEntry).formattedCodeList;
            max_codespace_scrollY = -1 * ((RenderableCodespaceObject.lineHeight * (getCode.size()+margin)) - (RenderableCodespaceObject.lineHeight*codespace_size_before_scroll));
        }
        return max_codespace_scrollY;
    }



    public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {}
    public void mouseReleased(double mouseX, double mouseY, int button) {}
    public void mouseClicked(double mouseX, double mouseY, int button) {
        Point mouse = new Point(mouseX, mouseY);
        if (this.isPointInside(mouse)) {
            this.codeScreen.searchBar.setFocused(false);
            setFocused(true);
        } else {
            setFocused(false);
        }
    }

    public void setFocused(boolean focus) {
        this.isFocused = focus;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, int parentx, int parenty, int parentWidth, int parentHeight) {
        int dx = (int) (x+parentx + (parentWidth*this.xBinding));
        int dy = (int) (y+parenty + (parentHeight*this.yBinding));
        int dx2 = dx+this.width;
        int dy2 = dy+this.width;

        context.enableScissor(dx, dy, dx2, dy2);
        context.fill(dx, dy, dx+width, dy+height, color);

        int scrollx = (int) (dx+scrollingX);
        int scrolly = (int) (dy+scrollingY);

        preSiblings.forEach(obj -> obj.render(context, mouseX, mouseY, scrollx,scrolly,scrollx+width,scrolly+height));

        //context.fill(scrollx, scrolly, scrollx+width, scrolly+height, color);

        siblings.forEach(obj -> obj.render(context, mouseX, mouseY, scrollx,scrolly,scrollx+width,scrolly+height));

        if (!codeScreen.functionEntryList.isEmpty()) {
            FunctionEntry function = codeScreen.functionEntryList.get(Firemod.functionDataManager.focusedFunctionEntry);

            int single_width = textRenderer.getWidth("x");

            int index = 0;
            int lineNum;

            int marginx = ((String.valueOf(function.formattedCodeList.size()).length() + 1) * single_width) + 2;
            int marginy = 10;
            int textHeight = 7;

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

                tx = scrollx + marginx;
                ty = (int) ((scrolly + index * (lineHeight)) + marginy);
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


            }
        } else {
            Point center = getScreenCenter();
            context.drawCenteredTextWithShadow(textRenderer, "its quite lonely in here... click the ⏻ to start using codeviewer!", center.x, center.y, 0xffffff);
        }

        if (this.topBorder.enabled)    { context.fill(scrollx, scrolly-this.topBorder.size, scrollx+width, scrolly, this.topBorder.color); }
        if (this.bottomBorder.enabled) { context.fill(scrollx, scrolly+height, scrollx+width, scrolly+height+this.bottomBorder.size, this.bottomBorder.color); }
        if (this.rightBorder.enabled)  { context.fill(scrollx+    width, scrolly, scrollx+width+this.rightBorder.size, scrolly+height, this.rightBorder.color); }
        if (this.leftBorder.enabled)   { context.fill(scrollx-this.leftBorder.size, scrolly, scrollx, scrolly+height, this.leftBorder.color); }

        this.scrollingX = MathUtils.lerp(this.scrollingX, this.lerpcrollingX, this.lerpScrollAmount);
        this.scrollingY = MathUtils.lerp(this.scrollingY, this.lerpcrollingY, this.lerpScrollAmount);

        context.disableScissor();

    }
}
