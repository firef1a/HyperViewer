package dev.fire.firemod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.fire.firemod.Firemod;
import dev.fire.firemod.screen.utils.*;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Arrays;


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
    public RenderableCodespaceObject codespace;
    public RenderableRectangleObject searchBarRect;
    public RenderableRectangleObject codespaceRect;
    public RenderableRectangleObject functionsList;
    public RenderableRectangleObject listRect;

    public ArrayList<FunctionEntry> functionEntryList;

    public TextFieldWidget searchBar;

    public int BOX_HEIGHT = 20;
    public int SEARCHBAR_HEIGHT = 15;
    public int SIDEBAR_WIDTH;
    public int SEARCHBAR_MARGIN = 5;

    public int renderCycle = 1;

    public int focusedFunctionTabIndex = 0;

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


        int searchbarMarginX = this.searchBarRect.width/12;
        int searchbarMarginY = this.searchBarRect.height/10;

        RenderableRectangleObject searchBarWidgetRect = new RenderableRectangleObject(0, 0, this.searchBarRect.width-searchbarMarginX, this.searchBarRect.height-searchbarMarginY);
        //searchBarWidgetRect.setCenter(searchBarRect.x+(searchBarRect.width/2), searchBarRect.y+(searchBarRect.height/2)+this.toolbar.height+(searchBarWidgetRect.height/3));
        Point center = searchBarRect.getScreenCenter();
        searchBarWidgetRect.setCenter(center.x, (int) (center.y+(searchBarRect.height/3)));

        this.searchBar = new TextFieldWidget(this.textRenderer, searchBarWidgetRect.x, searchBarWidgetRect.y,searchBarWidgetRect.width, searchBarWidgetRect.height, Text.literal("test"));
        this.searchBar.setDrawsBackground(false);
        this.searchBar.getText();

        addDrawableChild(this.searchBar);

        // function list
        this.listRect = new RenderableRectangleObject(0,0,0,0);
        this.sidebar.addSibling(listRect);
        ArrayList<String> data = new ArrayList<String>(Arrays.asList(
                new String("hello world!"),
                new String("hello world, electric boogaloo"),
                new String("the game is loading the game is loading the game is loading the game is loading the game is loading the game is loading the game is loading the game is loading the game is loading the game is loading the")
        ));

        this.functionEntryList = new ArrayList<FunctionEntry>(Arrays.asList(
                new FunctionEntry("JOIN EVENT", "event", data),
                new FunctionEntry("RIGHT CLICK", "event"),
                new FunctionEntry("lobby settings", "func"),
                new FunctionEntry("main loop", "process"),
                new FunctionEntry("EXPLODE EVENT", "entity_event"),
                new FunctionEntry("LEAVE EVENT", "event")

        ));
        generateFunctionList(this.functionEntryList);

        // codespace
        this.codespace = new RenderableCodespaceObject(textRenderer,0,0, this.width-this.sidebar.width, this.height-this.toolbar.height, CODESPACE_COLOR, this);
        this.codespace.setBinding(1,0);
        this.sidebar.addSibling(codespace);





        //this.codespaceRect = new RenderableRectangleObject(100, -50, 100, 100, setAlpha(0xd400ff, 1f));
        //this.codespace.addSibling(codespaceRect);

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
            obj.render(context, mouseX, mouseY, 0,0,0,0);
        });
        this.renderCycle += 1;
    }

    private void generateFunctionList(ArrayList<FunctionEntry> arrayList) {
        String name = "REAL";
        int width;
        int height = 13;
        int margin = 7;
        int heightMargin = 3;
        int defaultColor = setAlpha(0x3e3f42,1f);
        int hightlightColor = setAlpha(0x434447,1f);
        int clickColor = setAlpha(0x4b4c4f,1f);
        int x;
        int y;
        int borderSize = margin-5;

        this.listRect.siblings.clear();
        for (int i = 0; i < arrayList.size(); i++) {
            FunctionEntry entry = arrayList.get(i);
            x = margin;
            y = (i*(height+heightMargin)) + (this.searchBarRect.y+this.searchBarRect.height+this.searchBarRect.bottomBorder.size+5);
            width = this.sidebar.width-(margin*2);
            RenderableRectButton button = new RenderableRectButton(this.textRenderer, Text.literal(entry.name), x, y, width+borderSize, height, defaultColor, hightlightColor, clickColor, i);
            button.setLeftBorder(true, setAlpha(entry.getFuncColor(entry.functionType),1f),borderSize);
            this.listRect.addSibling(button);

        }
    }

    private int setAlpha(int color, float alpha) {return (color+ ((int)(alpha*255)<<24));}

    public Point getCenter(int cx, int cy, int width, int height) {
        return (new Point(cx-width/2,cy-height/2));
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        this.listRect.scrollingY += (int) (verticalAmount*5f);
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (RenderableRectangleObject rect : this.listRect.siblings) {
            int dx = rect.x+rect.parent.x + (rect.parent.width*rect.xBinding);
            int dy = rect.y+rect.parent.y + (rect.parent.height*rect.yBinding);
            if (mouseX > dx && mouseX < dx+width && mouseY > dy && mouseY < dy+height) {
                this.focusedFunctionTabIndex = rect.clickID;
                break;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.close();
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
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