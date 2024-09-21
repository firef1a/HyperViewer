package dev.fire.hyperviewer.screen.utils.screenWidgets;

import dev.fire.hyperviewer.HyperViewer;
import dev.fire.hyperviewer.devutils.MathUtils;
import dev.fire.hyperviewer.screen.screens.CodeScreen;

import dev.fire.hyperviewer.screen.utils.*;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

// ignore this, this is for a future planned update where the code can be edited, might not go through with it though due to the immense technical challenges with parsing code :D

public class EditableCodespaceObject extends RenderableRectangleObject implements RenderableObject {
    private final TextRenderer textRenderer;
    public int x;
    public int y;
    public int width;
    public int height;
    public ArrayList<RenderableRectangleObject> siblings;
    public int color;
    public RenderableRectangleObject parent;

    public int xBinding = 0;
    public int yBinding = 0;

    public RectBorder topBorder = new RectBorder(false);
    public RectBorder rightBorder = new RectBorder(false);
    public RectBorder bottomBorder = new RectBorder(false);
    public RectBorder leftBorder = new RectBorder(false);

    public CodeScreen codeScreen;

    public ArrayList<String> textLines;
    public Cursor cursor;
    public boolean focused = false;

    public int cursorBliker = 0;

    public int marginX = 0;
    public int marginY = 7;
    public int lineMargin = 1;

    public boolean isSelecting = false;
    public Cursor selectionStart = new Cursor(0,0);
    public Cursor selectionEnd = new Cursor(0,0);

    public Cursor starterCursor = new Cursor(0,0);;


    public EditableCodespaceObject(TextRenderer textRenderer, int x, int y, int width, int height, int color, CodeScreen codeScreen) {
        super(x, y, width, height, color);
        this.textRenderer = textRenderer;
        this.siblings = new ArrayList<RenderableRectangleObject>();
        this.textLines = new ArrayList<>(List.of(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
                "Ut enim ad minim veniam",
                "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat",
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur",
                "Excepteur sint occaecat cupidatat non proident",
                "sunt in culpa qui officia deserunt mollit anim id est laborum"
        ));

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.codeScreen = codeScreen;
        this.cursor = new Cursor(0,0);

        this.marginX = getMarginX();
    }


    public void setBinding(int xb, int yb) {
        this.xBinding = xb;
        this.yBinding = yb;
    }

    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        boolean isSneaking = Screen.hasShiftDown();
        boolean isCtrling = Screen.hasControlDown();
        boolean isAlting = Screen.hasAltDown();
        Cursor copyCursor = new Cursor(cursor.stringX, cursor.lineY);
        if (focused) {
            codeScreen.searchBar.setFocused(false);
            String key = String.valueOf((char) keyCode).toLowerCase(Locale.ROOT);
            if (isSneaking) {
                if (KeyInputData.uppercaseSpecialCharaters.containsKey(key)) {
                    key = KeyInputData.uppercaseSpecialCharaters.get(key);
                } else {
                    key = key.toUpperCase(Locale.ROOT);
                }
            }
            if (keyCode == GLFW.GLFW_KEY_TAB) {
                key = "\t";
            }

            //Firemod.LOGGER.info("MODIFIER: " + String.valueOf(modifiers));
            int stringX = this.cursor.stringX;
            int lineY = this.cursor.lineY;
            int moveStringX = 0, moveLineY = 0, newStringX, newLineY;
            if (keyCode == GLFW.GLFW_KEY_LEFT) {
                moveStringX = -1;
            } else if (keyCode == GLFW.GLFW_KEY_RIGHT) {
                moveStringX = 1;
            } else if (keyCode == GLFW.GLFW_KEY_DOWN) {
                moveLineY = 1;
            } else if (keyCode == GLFW.GLFW_KEY_UP) {
                moveLineY = -1;
            } else if (keyCode == GLFW.GLFW_KEY_BACKSPACE || keyCode == GLFW.GLFW_KEY_DELETE) {
                if (isSelecting) {
                    isSelecting = false;
                    if (selectionStart.lineY == selectionEnd.lineY) {
                        String getline = this.textLines.get(this.selectionStart.lineY);

                        this.cursor.stringX = selectionStart.stringX;
                        this.cursor.lineY = this.selectionStart.lineY;
                        this.textLines.set(this.selectionStart.lineY, getline.substring(0, selectionStart.stringX)+getline.substring(selectionEnd.stringX));

                    } else {
                        ArrayList<String> newText = new ArrayList<>();
                        for (int i = 0; i < textLines.size(); i++) {
                            String text = this.textLines.get(i);
                            if (selectionStart.lineY == i) {
                                newText.add(text.substring(0, selectionStart.stringX));
                            } else if (selectionEnd.lineY == i && !(selectionEnd.stringX >= text.length())) {
                                newText.add(text.substring(selectionEnd.stringX));
                            } else if (i < selectionStart.lineY || selectionEnd.lineY < i) {
                                newText.add(text);
                            }
                        }
                        this.textLines = newText;

                        this.cursor.lineY = selectionStart.lineY;
                        this.cursor.stringX = selectionStart.stringX;
                    }
                } else {
                    if (cursor.stringX > 0) {

                        //Firemod.LOGGER.info(key);
                        String line = this.textLines.get(this.cursor.lineY);
                        ArrayList<String> lineList = new ArrayList<String>(Arrays.asList(line.split("")));
                        lineList.remove(stringX - 1);

                        this.cursor.stringX -= 1;
                        this.textLines.set(this.cursor.lineY, String.join("", lineList));
                    } else if (cursor.stringX == 0) {
                        if (cursor.lineY > 0) {
                            String oldLine = this.textLines.get(cursor.lineY);
                            this.textLines.remove(cursor.lineY);
                            this.cursor.lineY--;
                            this.cursor.stringX = this.textLines.get(cursor.lineY).length();
                            this.textLines.set(cursor.lineY, this.textLines.get(cursor.lineY) + oldLine);
                        }
                    }
                }
            } else if (keyCode == GLFW.GLFW_KEY_ENTER) {
                if (isSelecting) {
                    isSelecting = false;
                    if (selectionStart.lineY == selectionEnd.lineY) {
                        String getline = this.textLines.get(this.selectionStart.lineY);

                        this.cursor.stringX = 0;
                        this.cursor.lineY = this.selectionStart.lineY+1;
                        this.textLines.set(this.selectionStart.lineY+1, getline.substring(selectionEnd.stringX));
                        this.textLines.set(this.selectionStart.lineY, getline.substring(0, selectionStart.stringX));

                    } else {
                        ArrayList<String> newText = new ArrayList<>();
                        for (int i = 0; i < textLines.size(); i++) {
                            String text = this.textLines.get(i);
                            if (selectionStart.lineY == i) {
                                newText.add(text.substring(0, selectionStart.stringX));
                            } else if (selectionEnd.lineY == i && !(selectionEnd.stringX >= text.length())) {
                                newText.add(text.substring(selectionEnd.stringX));
                            } else if (i < selectionStart.lineY || selectionEnd.lineY < i) {
                                newText.add(text);
                            }
                        }
                        this.textLines = newText;

                        String line = this.textLines.get(this.selectionStart.lineY);
                        ArrayList<String> lineList = new ArrayList<String>(Arrays.asList(line.split("")));

                        this.cursor.lineY = selectionStart.lineY+1;
                        this.cursor.stringX = 0;

                        this.textLines.set(selectionStart.lineY, String.join("", lineList));


                    }
                } else {
                    String substring = this.textLines.get(cursor.lineY).substring(cursor.stringX);
                    this.textLines.set(cursor.lineY, this.textLines.get(cursor.lineY).substring(0, cursor.stringX));
                    cursor.stringX = 0;
                    cursor.lineY++;
                    this.textLines.add(cursor.lineY, substring);
                }
            } else if (keyCode == GLFW.GLFW_KEY_A && isCtrling) {
                isSelecting = true;
                selectionStart.set(0,0);
                selectionEnd.set(textLines.size()-1, textLines.get(textLines.size()-1).length());
            } else if (KeyInputData.validInputKeys.contains(keyCode)) {
                //Firemod.LOGGER.info(key);
                if (isSelecting && !selectionStart.isEqual(selectionEnd)) {
                    isSelecting = false;
                    if (selectionStart.lineY == selectionEnd.lineY) {
                        String line = this.textLines.get(this.selectionStart.lineY).substring(0, selectionStart.stringX);
                        ArrayList<String> lineList = new ArrayList<String>(Arrays.asList(line.split("")));
                        lineList.add(selectionStart.stringX, key);

                        this.cursor.stringX = selectionStart.stringX+1;
                        this.textLines.set(this.cursor.lineY, String.join("", lineList));
                    } else {
                        ArrayList<String> newText = new ArrayList<>();
                        for (int i = 0; i < textLines.size(); i++) {
                            String text = this.textLines.get(i);
                            if (selectionStart.lineY == i) {
                                newText.add(text.substring(0, selectionStart.stringX));
                            } else if (selectionEnd.lineY == i && !(selectionEnd.stringX >= text.length())) {
                                newText.add(text.substring(selectionEnd.stringX));
                            } else if (i < selectionStart.lineY || selectionEnd.lineY < i) {
                                newText.add(text);
                            }
                        }
                        this.textLines = newText;

                        String line = this.textLines.get(this.selectionStart.lineY);
                        ArrayList<String> lineList = new ArrayList<String>(Arrays.asList(line.split("")));
                        lineList.add(selectionStart.stringX, key);

                        this.cursor.lineY = selectionStart.lineY;
                        this.cursor.stringX = selectionStart.stringX+1;

                        this.textLines.set(selectionStart.lineY, String.join("", lineList));


                    }
                } else {
                    String line = this.textLines.get(this.cursor.lineY);
                    ArrayList<String> lineList = new ArrayList<String>(Arrays.asList(line.split("")));
                    if (stringX >= line.length()) {
                        lineList.add(key);
                    } else {
                        lineList.add(stringX, key);
                    }


                    this.cursor.stringX += 1;
                    this.textLines.set(this.cursor.lineY, String.join("", lineList));
                }
            }

            if (moveLineY != 0 || moveStringX != 0) {
                isSelecting = false;
                newStringX = stringX + moveStringX;
                newLineY = lineY + moveLineY;
                if (!(newLineY >= this.textLines.size()) && !(newStringX > this.textLines.get(lineY).length()) && newLineY >= 0 && newStringX >= 0) {
                    if (this.textLines.get(newLineY).length() < newStringX) {
                        newStringX = this.textLines.get(newLineY).length();
                    }
                    cursor.stringX = newStringX;
                    cursor.lineY = newLineY;
                } else if (newLineY < this.textLines.size()-1 && newStringX == this.textLines.get(lineY).length()) {
                    cursor.stringX = 0;
                    cursor.lineY++;
                }

            }

        }
        if (this.textLines.get(cursor.lineY).length() == 0) {
            cursor.stringX = 0;
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        Point mouse = new Point(mouseX, mouseY);

        if (this.isPointInside(mouse)) {
            isSelecting = false;
            codeScreen.searchBar.setFocused(false);
            this.focused = true;

            Point pos = getScreenPosition();
            int cursorx = Math.max((int) ((int) ((mouseX - pos.x) - marginX) - this.scrollingX),0);
            int cursory = Math.max((int) ((int) ((mouseY - pos.y) - marginY) - this.scrollingY),0);

            cursor.lineY = (int) Math.min(Math.floor((double) cursory / (textRenderer.fontHeight+lineMargin)), textLines.size()-1);

            String line = textLines.get(cursor.lineY);
            ArrayList<String> lineList = new ArrayList<String>(Arrays.asList(line.split("")));

            int index = 0;
            int sWidth = 0;
            boolean chosen = false;
            int currentwidth;
            for (String s : lineList) {
                currentwidth = textRenderer.getWidth(s);
                if (sWidth + currentwidth >= cursorx) {
                    if (sWidth + (currentwidth/3) >= cursorx) {
                        cursor.stringX = index;
                    } else {
                        cursor.stringX = index+1;
                    }

                    chosen = true;
                    break;
                }
                sWidth += currentwidth;
                index++;
            }
            if (!chosen){
                cursor.stringX = index;
            }
            starterCursor.set(cursor);




        } else {
            this.focused = false;
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {

    }

    public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        Point mouse = new Point(mouseX, mouseY);

        if (this.isPointInside(mouse) && this.focused) {
            Point pos = getScreenPosition();
            int cursorx = Math.max((int) ((int) ((mouseX - pos.x) - marginX) - this.scrollingX),0);
            int cursory = Math.max((int) ((int) ((mouseY - pos.y) - marginY) - this.scrollingY),0);

            cursor.lineY = (int) Math.min(Math.floor((double) cursory / (textRenderer.fontHeight+lineMargin)), textLines.size()-1);

            String line = textLines.get(cursor.lineY);
            ArrayList<String> lineList = new ArrayList<String>(Arrays.asList(line.split("")));

            int index = 0;
            int sWidth = 0;
            boolean chosen = false;
            int currentwidth;
            for (String s : lineList) {
                currentwidth = textRenderer.getWidth(s);
                if (sWidth + currentwidth >= cursorx) {
                    if (sWidth + (currentwidth/3) >= cursorx) {
                        cursor.stringX = index;
                    } else {
                        cursor.stringX = index+1;
                    }

                    chosen = true;
                    break;
                }
                sWidth += currentwidth;
                index++;
            }
            if (!chosen){
                cursor.stringX = index;
            }

            if (!starterCursor.isEqual(this.cursor)) {
                isSelecting = true;
                boolean startFirst = false;

                if (starterCursor.lineY == cursor.lineY) {
                    if (starterCursor.stringX > cursor.stringX) {
                        startFirst = false;
                    } else {
                        startFirst = true;
                    }
                } else {
                    if (starterCursor.lineY < cursor.lineY) {
                        startFirst = true;
                    } else {
                        startFirst = false;
                    }
                }

                if (startFirst) {
                    selectionStart.set(this.starterCursor);
                    selectionEnd.set(this.cursor);
                } else {
                    selectionStart.set(this.cursor);
                    selectionEnd.set(this.starterCursor);
                }

                HyperViewer.LOGGER.info("starter: {}, {}", starterCursor.stringX, starterCursor.lineY);
                HyperViewer.LOGGER.info("cursor: {}, {}", cursor.stringX, cursor.lineY);
            } else {
                isSelecting = false;
            }




        } else {
            this.focused = false;
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, int parentx, int parenty, int parentWidth, int parentHeight) {
        int dx = (int) (x+parentx + (parentWidth*this.xBinding));
        int dy = (int) (y+parenty + (parentHeight*this.yBinding));
        int dx2 = dx+this.width;
        int dy2 = dy+this.width;

        this.marginX = getMarginX();

        context.enableScissor(dx, dy, dx2, dy2);
        context.fill(dx, dy, dx+width, dy+height, color);

        int cursorx;
        if (textLines.get(cursor.lineY).length() <= cursor.stringX) {
            cursorx = marginX + dx + textRenderer.getWidth(textLines.get(cursor.lineY)) - 1;
        } else if (cursor.stringX <= 0){
            cursorx = marginX + dx - 1;
        } else {
            cursorx = marginX + dx + textRenderer.getWidth(textLines.get(cursor.lineY).substring(0, cursor.stringX)) - 1;
        }
        cursorx = (int) (cursorx + this.scrollingX);

        int cursory = (int) (marginY + dy + cursor.lineY*(textRenderer.fontHeight + lineMargin) + this.scrollingY);

        if (this.focused) {
            context.drawText(textRenderer, "|", cursorx, cursory+1, 0xffa136, false);
            context.drawText(textRenderer, "|", cursorx, cursory-1, 0xffa136, false);
        }



        siblings.forEach(obj -> obj.render(context, mouseX, mouseY, dx,dy,dx+width,dy+height));


        int scrollx = (int) (dx+scrollingX);
        int scrolly = (int) (dy+scrollingY);

        int linex, liney, index, lineNumx, charx, charint, textColor;

        index = 0;
        for (String textLine : textLines) {
            linex = marginX + scrollx;
            liney = marginY + scrolly + ((textRenderer.fontHeight+lineMargin)*index);
            lineNumx = ((scrollx + marginX) - textRenderer.getWidth(String.valueOf(index+1))) - 5;

            context.drawText(textRenderer, String.valueOf(index+1), lineNumx, liney, Formatting.GRAY.getColorValue(), false);

            ArrayList<String> lineList = new ArrayList<String>(Arrays.asList(textLine.split("")));

            charx = linex;
            charint = 0;
            for (String eachChar : lineList) {
                textColor = 0xffffff;
                if (isSelecting && !selectionStart.isEqual(selectionEnd)) {
                    if (index == selectionStart.lineY && index == selectionEnd.lineY){
                        if (charint >= selectionStart.stringX && charint < selectionEnd.stringX) {
                            textColor = 0x36b5ff;
                        }
                    } else if (betweenRange(index,selectionStart.lineY, selectionEnd.lineY)) {
                        if (index != selectionEnd.lineY && index != selectionStart.lineY) {
                            textColor = 0x36b5ff;
                        } else if (index == selectionStart.lineY) {
                            if (charint >= selectionStart.stringX) {
                                textColor = 0x36b5ff;
                            }
                        } else if (index == selectionEnd.lineY) {
                            if (charint < selectionEnd.stringX) {
                                textColor = 0x36b5ff;
                            }
                        }

                    }
                }
                context.drawText(textRenderer, eachChar, charx, liney, textColor, false);
                charx += textRenderer.getWidth(eachChar);
                charint++;
            }
            //context.drawText(textRenderer, textLine, linex, liney, 0xffffff, false);


            index++;
        }


        if (this.topBorder.enabled)    { context.fill(dx, dy-this.topBorder.size, dx+width, dy, this.topBorder.color); }
        if (this.bottomBorder.enabled) { context.fill(dx, dy+height, dx+width, dy+height+this.bottomBorder.size, this.bottomBorder.color); }
        if (this.rightBorder.enabled)  { context.fill(dx+    width, dy, dx+width+this.rightBorder.size, dy+height, this.rightBorder.color); }
        if (this.leftBorder.enabled)   { context.fill(dx-this.leftBorder.size, dy, dx, dy+height, this.leftBorder.color); }

        this.scrollingX = MathUtils.lerp(this.scrollingX, this.lerpcrollingX, this.lerpScrollAmount);
        this.scrollingY = MathUtils.lerp(this.scrollingY, this.lerpcrollingY, this.lerpScrollAmount);

        context.disableScissor();
    }

    public int getMarginX() {
        return (15+10);
    }

    public boolean betweenRange(int num, int rangeStart, int rangeEnd) {
        if (num >= rangeStart && num <= rangeEnd) {
            return true;
        } else if (num <= rangeStart && num >= rangeEnd) {
            return true;
        }
        return false;
    }

    @Override
    public Point getScreenPosition() {
        int xval = (int) (this.x);
        int yval = (int) (this.y);
        if (this.parent == null) {
            return new Point(xval, yval);
        } else {
            Point parentPoint = this.parent.getScreenPosition();
            return new Point(parentPoint.x+xval+(parent.width*this.xBinding),parentPoint.y+yval+(parent.height*this.yBinding));
        }
    }

    @Override
    public boolean isPointInside(Point point){
        Point screenpos = getScreenPosition();
        return point.x > screenpos.x && point.x < screenpos.x + width && point.y > screenpos.y && point.y < screenpos.y + height;
    }
}
