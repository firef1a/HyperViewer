package dev.fire.firemod.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.fire.firemod.Firemod;
import dev.fire.firemod.screen.utils.Point;
import dev.fire.firemod.screen.utils.Rectangle;
import dev.fire.firemod.screen.utils.RenderableRectangleObject;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;


public class CodeScreen extends Screen {
    protected Text footer = Text.literal("hello");
    private boolean usingMouseToSelect = false;
    private Integer lastMouseX;
    private Integer lastMouseY;
    private final Screen parent;

    public int CODESPACE_COLOR;
    public int TITLE_COLOR;
    public int SIDEBAR_COLOR;
    public int HIGHLIGHT_COLOR;
    public int SEARCHBAR_COLOR;
    public int DARK_SECTION_BORDER_COLOR;

    public RenderableRectangleObject background;
    public RenderableRectangleObject toolbar;
    public RenderableRectangleObject sidebar;
    public RenderableRectangleObject codespace;
    public RenderableRectangleObject searchBarRect;
    public RenderableRectangleObject codespaceRect;

    public TextFieldWidget searchBar;

    public int BOX_HEIGHT = 20;
    public int SEARCHBAR_HEIGHT = 15;
    public int SIDEBAR_WIDTH;
    public int SEARCHBAR_MARGIN = 5;

    public int renderCycle = 1;

    public CodeScreen(Text title, Screen parent) {
        super(title);
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();
        this.renderCycle = 1;

        //this.BACKGROUND_COLOR = setAlpha(0x000000, 0.6F);
        this.TITLE_COLOR = setAlpha(0x2b2d30, 1F);
        this.CODESPACE_COLOR = setAlpha(0x1e1f22, 1F);
        this.SIDEBAR_COLOR = setAlpha(0x313236, 1f);
        this.SEARCHBAR_COLOR = setAlpha(0x1c1c1f, 1f);
        this.HIGHLIGHT_COLOR = setAlpha(0x58585c, 1f);
        this.DARK_SECTION_BORDER_COLOR = setAlpha(0x222224, 1f);

        this.SIDEBAR_WIDTH = this.width/5;

        // tool bar and background parent object
        this.background = new RenderableRectangleObject(0,0,this.width, this.height, setAlpha(0xd400ff, 1f));
        this.toolbar = new RenderableRectangleObject(0,0, width,height/25, TITLE_COLOR);
        this.toolbar.setBottomBorder(true, DARK_SECTION_BORDER_COLOR, 1);
        this.background.addSibling(toolbar);

        this.sidebar = new RenderableRectangleObject(0,this.toolbar.y, SIDEBAR_WIDTH, this.height, SIDEBAR_COLOR);
        this.sidebar.setRightBorder(true, DARK_SECTION_BORDER_COLOR, 1);
        this.sidebar.setBinding(0,1);
        this.toolbar.addPreSibling(sidebar);

        // search bar
        this.searchBarRect = new RenderableRectangleObject(SEARCHBAR_MARGIN, this.toolbar.y+SEARCHBAR_MARGIN, SIDEBAR_WIDTH-SEARCHBAR_MARGIN*2, SEARCHBAR_HEIGHT, SEARCHBAR_COLOR);
        this.searchBarRect.setBinding(0,1);
        this.searchBarRect.setBottomBorder(true, HIGHLIGHT_COLOR, 2);
        this.toolbar.addPreSibling(searchBarRect);


        int searchbarMarginX = this.searchBarRect.width/15;
        int searchbarMarginY = this.searchBarRect.height/10;

        RenderableRectangleObject searchBarWidgetRect = new RenderableRectangleObject(0, 0, this.searchBarRect.width-searchbarMarginX, this.searchBarRect.height-searchbarMarginY);
        //searchBarWidgetRect.setCenter(searchBarRect.x+(searchBarRect.width/2), searchBarRect.y+(searchBarRect.height/2)+this.toolbar.height+(searchBarWidgetRect.height/3));
        Point center = searchBarRect.getScreenCenter();
        searchBarWidgetRect.setCenter(center.x,center.y+(searchBarRect.height/3));

        this.searchBar = new TextFieldWidget(this.textRenderer, searchBarWidgetRect.x, searchBarWidgetRect.y,searchBarWidgetRect.width, searchBarWidgetRect.height, Text.literal("test"));
        this.searchBar.setDrawsBackground(false);

        addDrawableChild(this.searchBar);

        // codespace
        this.codespace = new RenderableRectangleObject(0,0, this.width-this.sidebar.width, this.height-this.toolbar.height, CODESPACE_COLOR);
        this.codespace.setBinding(1,0);
        this.sidebar.addSibling(codespace);


        this.codespaceRect = new RenderableRectangleObject(100, -50, 100, 100, setAlpha(0xd400ff, 1f));
        this.codespace.addSibling(codespaceRect);


    }
/*
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

    }

 */

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        //super.render(context, mouseX, mouseY, delta);
        RenderSystem.enableBlend();
        int centerX = this.width / 2 - 62;
        int centerY = this.height / 2 - 31 - 27;

        // set last mouse position
        if (lastMouseX == null) lastMouseX = mouseX;
        if (lastMouseY == null) lastMouseY = mouseY;
        if (!usingMouseToSelect) {
            if (this.lastMouseX != mouseX || this.lastMouseY != mouseY) this.usingMouseToSelect = true;
            lastMouseX = mouseX;
            lastMouseY = mouseY;
        }

        int width = this.width;
        int height = this.height;

        //int margin = width/25;
        //int x1 = (margin)*5;
        //int y1 = (margin)*5;
        //int x2 = (width-margin)*5;
        //int y2 = (height-margin)*5;
        //RenderSystem.viewport(x1,y1,x2-x1,y2-y1);
        ArrayList<RenderableRectangleObject> renderObjectList = new ArrayList<RenderableRectangleObject>();

        renderObjectList.add(background);

        //searchBar.setDimensions(this.sidebar.width, BOX_HEIGHT);

        // Render

        renderObjectList.forEach(obj -> {
            obj.render(context,0,0,0,0);
        });
        this.renderCycle += 1;
    }


    private int setAlpha(int color, float alpha) {return (color+ ((int)(alpha*255)<<24));}

    public Point getCenter(int cx, int cy, int width, int height) {
        return (new Point(cx-width/2,cy-height/2));
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double vertical, double horizontal) {
        return false;
    }



    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.close();
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button);
    }
    @Override
    public boolean shouldPause() {
        return false;
    }
    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
    @Override
    public void close() {
        client.setScreen(parent);
    }
}