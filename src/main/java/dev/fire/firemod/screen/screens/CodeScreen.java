package dev.fire.firemod.screen.screens;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.fire.firemod.FileManager;
import dev.fire.firemod.Firemod;
import dev.fire.firemod.screen.chat.ChatManager;
import dev.fire.firemod.screen.utils.*;
import dev.fire.firemod.screen.utils.screenWidgets.*;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import java.io.IOException;
import java.util.*;

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

    public RenderableToggleableButton toggleButton;
    public RenderableCallbackIDButton resetButton;
    public RenderableCallbackIDButton saveButton;
    public RenderableCallbackIDButton uploadButton;


    public RenderableRectangleObject sidebar;
    public RenderableCodespaceObject codespace;


    public RenderableRectangleObject searchBarRect;
    public RenderableRectangleObject listRect;

    public ArrayList<FunctionEntry> functionEntryList;
    public ArrayList<FunctionEntry> lastFunctionEntryList;

    public TextFieldWidget searchBar;

    public int SEARCHBAR_HEIGHT = 15;
    public int SIDEBAR_WIDTH;
    public int SEARCHBAR_MARGIN = 5;

    public int renderCycle = 1;
    public String searchBarText = "";
    public String lastSearchBarText = "";

    private final List<Drawable> drawables = Lists.newArrayList();

    public CodeScreen(Text title, Screen parent) {
        super(title);
        this.parent = parent;
    }

    @Override
    protected void init() {
        UUID.randomUUID();
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

        this.sidebar = new RenderableRectangleObject(0,0, SIDEBAR_WIDTH, this.height, SIDEBAR_COLOR);
        this.sidebar.setRightBorder(true, DARK_SECTION_BORDER_COLOR, 1);
        this.sidebar.setBinding(0,1);
        this.toolbar.addPreSibling(sidebar);

        // add toolbar widgets

        int widgetSize = 15;
        int widgetX = 5;
        int widgetY = (this.toolbar.height/2)-(widgetSize/2);

        int spacing = 5+widgetSize;

        this.toggleButton = new RenderableToggleableButton(textRenderer,Text.literal("⏻"), widgetX, widgetY, widgetSize,widgetSize, setAlpha(0xed5139, 1f), setAlpha(0xe87361, 1f), setAlpha(0x62e04f, 1f), setAlpha(0x82db74, 1f), Firemod.functionDataManager.enableCodeViewer, false);
        this.toolbar.addSibling(this.toggleButton);
        widgetX += spacing;

        this.resetButton = new RenderableCallbackIDButton(textRenderer,Text.literal("☈"), widgetX, widgetY, widgetSize,widgetSize, setAlpha(0xf5714c, 1f), setAlpha(0xf2967c, 1f), true);
        this.toolbar.addSibling(this.resetButton);
        widgetX += spacing;

        this.saveButton = new RenderableCallbackIDButton(textRenderer,Text.literal("↓"), widgetX, widgetY, widgetSize,widgetSize, setAlpha(0x56baf5, 1f), setAlpha(0x8cc9ed, 1f), true);
        this.toolbar.addSibling(this.saveButton);
        widgetX += spacing;

        this.uploadButton = new RenderableCallbackIDButton(textRenderer,Text.literal("↑"), widgetX, widgetY, widgetSize,widgetSize, setAlpha(0x915bf5, 1f), setAlpha(0xb592f7, 1f), true);
        this.toolbar.addSibling(this.uploadButton);
        widgetX += spacing;



        // search bar
        this.searchBarRect = new RenderableRectangleObject(SEARCHBAR_MARGIN, SEARCHBAR_MARGIN, SIDEBAR_WIDTH-SEARCHBAR_MARGIN*2, SEARCHBAR_HEIGHT, SEARCHBAR_COLOR);
        this.searchBarRect.setBinding(0,0);
        this.searchBarRect.setBottomBorder(true, HIGHLIGHT_COLOR, 2);
        this.sidebar.addSibling(searchBarRect);


        int searchbarMarginX = this.searchBarRect.width/12;
        int searchbarMarginY = this.searchBarRect.height/10;

        RenderableRectangleObject searchBarWidgetRect = new RenderableRectangleObject(0, 0, this.searchBarRect.width-searchbarMarginX, this.searchBarRect.height-searchbarMarginY);
        //searchBarWidgetRect.setCenter(searchBarRect.x+(searchBarRect.width/2), searchBarRect.y+(searchBarRect.height/2)+this.toolbar.height+(searchBarWidgetRect.height/3));
        Point center = searchBarRect.getScreenCenter();
        searchBarWidgetRect.setCenter(center.x, (int) (center.y-1+(searchBarRect.height/3)));

        this.searchBar = new TextFieldWidget(this.textRenderer, searchBarWidgetRect.x, searchBarWidgetRect.y,searchBarWidgetRect.width, searchBarWidgetRect.height, Text.literal("test"));
        this.searchBar.setDrawsBackground(false);
       // this.searchBar.getText();

        addDrawableChild(this.searchBar);

        // function list
        this.listRect = new RenderableRectangleObject(0,0,0,0);
        this.searchBarRect.addPreSibling(listRect);

        // hide scrolling functions rect

        RenderableRectangleObject hideRect = new RenderableRectangleObject(0,-searchBarRect.y,searchBarRect.width,searchBarRect.y+searchBarRect.height+searchBarRect.bottomBorder.size+4, sidebar.color);
        this.searchBarRect.addPreSibling(hideRect);

        ArrayList<String> data = new ArrayList<String>();
        int min = 1;
        int max = 10000;
        for (int i = 0; i < 100; i++) {
            int rn = new Random().nextInt(max - min + 1) + min;
            data.add("hello world! " + String.valueOf(rn));
        }

        this.functionEntryList = Firemod.functionDataManager.functionEntryArrayList;
        this.lastFunctionEntryList = new ArrayList<>();

        generateFunctionList(this.functionEntryList);

        // codespace
        this.codespace = new RenderableCodespaceObject(textRenderer, this.sidebar.rightBorder.size,this.toolbar.bottomBorder.size, (this.width-this.sidebar.width)-this.sidebar.rightBorder.size,(this.height-this.toolbar.height)-this.toolbar.bottomBorder.size, setAlpha(CODESPACE_COLOR,1), this);
        this.codespace.setBinding(1,0);
        this.sidebar.addSibling(codespace);


        //Point codespaceScreenPos = codespace.getScreenPosition();

        //dev.fire.firemod.screen.widget.EditBoxWidget testCodeWidget = new dev.fire.firemod.screen.widget.EditBoxWidget(textRenderer, codespaceScreenPos.x, codespaceScreenPos.y, codespace.width, codespace.height, Text.empty(), Text.empty());

        //testCodeWidget.setDrawsBackground(true);

        // this.searchBar.getText();

        //addDrawableChild(testCodeWidget);


    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        Iterator var5 = drawables.iterator();

        while(var5.hasNext()) {
            Drawable drawable = (Drawable)var5.next();
            drawable.render(context, mouseX, mouseY, delta);
        }

    }



    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        //super.render(context, mouseX, mouseY, delta);
        this.renderInGameBackground(context);
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

        searchBarText = this.searchBar.getText();

        if (!Objects.equals(searchBarText, lastSearchBarText)) {
            this.listRect.scrollingY = 0;
            this.listRect.lerpcrollingY = 0;
            generateFunctionList(this.functionEntryList);
        }

        lastSearchBarText = searchBarText;

        this.functionEntryList = Firemod.functionDataManager.functionEntryArrayList;

        generateFunctionList(this.functionEntryList);

        this.lastFunctionEntryList = this.functionEntryList;


    }
    private void generateFunctionList(ArrayList<FunctionEntry> arrayList) {
        int width;
        int height = 13;
        int margin = 7;
        int heightMargin = 3;
        int defaultColor = setAlpha(0x3e3f42,1f);
        int hightlightColor = setAlpha(0x434447,1f);
        int clickColor = setAlpha(0x4b4c4f,1f);
        int x, y, cbWidth, cbHeight;
        int skipIminus = 0;
        int borderSize = margin-5;

        this.listRect.siblings.clear();
        for (int i = 0; i < arrayList.size(); i++) {
            FunctionEntry entry = arrayList.get(i);

            cbWidth = 3;
            cbHeight = height;

            x = borderSize;
            y = ((i-skipIminus)*(height+heightMargin)) + (this.searchBarRect.height+this.searchBarRect.bottomBorder.size+4) ;
            width = (this.sidebar.width-(margin*2))-cbWidth;
            RenderableEntryButton button = new RenderableEntryButton(this.textRenderer, Text.literal(entry.functionName), x, y, width+borderSize, height, defaultColor, hightlightColor, clickColor, i, false);
            button.setLeftBorder(true, setAlpha(entry.getFuncColor(entry.functionType),1f),borderSize);


            RenderableCallbackIDButton callbackButton = new RenderableCallbackIDButton(this.textRenderer, Text.empty(), 0, 0, cbWidth,cbHeight, setAlpha(0xf0573c, 1f),setAlpha(0xed806d, 1f), i, true);            callbackButton.setBinding(0,0);
            callbackButton.setBinding(1,0);
            button.addSibling(callbackButton);

            if (entry.functionName.toLowerCase().contains(this.searchBarText.toLowerCase())){
                this.listRect.addSibling(button);
            } else {
                skipIminus++;
            }
        }
    }

    private void old(ArrayList<FunctionEntry> arrayList) {
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
            RenderableEntryButton button = new RenderableEntryButton(this.textRenderer, Text.literal(entry.functionName), x, y, width+borderSize, height, defaultColor, hightlightColor, clickColor, i, false);
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
        Point mouse = new Point(mouseX, mouseY);
        double scroll_amountX = horizontalAmount;
        double scroll_amountY = verticalAmount*30f;

        int max_codespace_scrollY = RenderableCodespaceObject.getMaxScrollY();
        int max_codespace_scrollX = RenderableCodespaceObject.getMaxScrollX();

        int function_list_size_before_scroll = 24;
        int max_function_scroll = Math.min(-1*(this.listRect.siblings.size()-function_list_size_before_scroll)*16, 0);

        if (sidebar.isPointInside(mouse)) {
            if (this.functionEntryList.size() > function_list_size_before_scroll) {
                if (this.listRect.lerpcrollingY + scroll_amountY > 0) {
                    this.listRect.lerpcrollingY = 0;
                } else if (this.listRect.lerpcrollingY + scroll_amountY < max_function_scroll) {
                    this.listRect.lerpcrollingY = max_function_scroll;
                } else {
                    this.listRect.lerpcrollingY += scroll_amountY;
                }

            } else {
                this.listRect.lerpcrollingY = 0;
            }
        }

        scroll_amountX = horizontalAmount*11f;
        scroll_amountY = verticalAmount*22f;
        if (codespace.isPointInside(mouse)) {
            // Y
            if (this.codespace.lerpcrollingY + scroll_amountY >= 0) {
                this.codespace.lerpcrollingY = 0;
            } else if (this.codespace.lerpcrollingY + scroll_amountY < max_codespace_scrollY) {
                this.codespace.lerpcrollingY = (double) max_codespace_scrollY;
            } else {
                this.codespace.lerpcrollingY += scroll_amountY;
            }
            // X
            if (this.codespace.lerpcrollingX + scroll_amountX >= 0) {
                this.codespace.lerpcrollingX = 0;
            } else if (this.codespace.lerpcrollingX + scroll_amountX < max_codespace_scrollX) {
                this.codespace.lerpcrollingX = (double) max_codespace_scrollX;
            } else {
                this.codespace.lerpcrollingX += scroll_amountX;
            }
        }
        return false;
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean hasClicked = false;
        Point mouse = new Point(mouseX, mouseY);

        // toggle button code
        if (toggleButton.isPointInside(mouse)) {
            Text displayMessage;
            toggleButton.enabled = !toggleButton.enabled;
            Firemod.functionDataManager.enableCodeViewer = toggleButton.enabled;

            // why doesnt mutable text work omgistg
            if (toggleButton.enabled) {
                displayMessage = Text.literal("CodeViewer is now enabled.").withColor(0x81ff54);
            } else {
                displayMessage = Text.literal("CodeViewer is now disabled.").withColor(0xf25d55);
            }
            ChatManager.displayChatMessageToPlayer(displayMessage);
        } else if (resetButton.isPointInside(mouse)) {
            Firemod.functionDataManager.clear();
            Firemod.functionFinder.checkedBlocks.clear();
            Firemod.functionDataManager.focusedFunctionEntry = 0;
            ChatManager.displayChatMessageToPlayer(Text.literal("Cleared CodeViewer Function List.").withColor(0xf25d55));
        } else if (saveButton.isPointInside(mouse)) {
            String saveName = "codeDump.json";
            Text displayMessage;
            String codeData = "";
            int i = 0;
            for (FunctionEntry functionEntry : Firemod.functionDataManager.functionEntryArrayList) {

                codeData = codeData + functionEntry.rawJsonString;
                if (i != Firemod.functionDataManager.functionEntryArrayList.size()-1){
                    codeData = codeData + "\n";
                }
                        i++;
            }

            try {
                FileManager.writeFile(saveName, codeData);
                displayMessage = Text.literal("Wrote code to file: \"" + saveName + "\"").withColor(0x56baf5);
            } catch (IOException e) {
                displayMessage = Text.literal("Unable to write file to disk: " + e.getMessage()).withColor(0xf25d55);
            }
            ChatManager.displayChatMessageToPlayer(displayMessage);
        } else if (uploadButton.isPointInside(mouse)) {
            String saveName = "codeDump.json";
            Text displayMessage;
            if (FileManager.exists(saveName)) {
                try {
                    String fileData = FileManager.readFile(saveName);
                    String[] collection = fileData.split("\n");

                    Firemod.functionDataManager.clear();
                    for (String s : collection) {
                        Firemod.functionDataManager.addFunction(s);
                    }

                    displayMessage = Text.literal("Successfully loaded code from file \"" + saveName + "\"").withColor(0xb592f7);
                } catch (IOException e) {
                    displayMessage = Text.literal("Unable to load file to disk or invalid file: " + e.getMessage()).withColor(0xf25d55);
                }
            } else {
                displayMessage = Text.literal("File \"" + saveName + "\" does not exist, unable to load code from disk.").withColor(0xf25d55);
            }
            ChatManager.displayChatMessageToPlayer(displayMessage);
        }

        // function list code + delete func code
        for (RenderableRectangleObject rect : this.listRect.siblings) {
            for (RenderableRectangleObject sibling : rect.siblings) {
                if (sibling.isPointInside(mouse)) {
                    Firemod.functionDataManager.remove(sibling.clickID);
                    int focusedTabLength = Firemod.functionDataManager.functionEntryArrayList.size()-1;
                    if (focusedTabLength < Firemod.functionDataManager.focusedFunctionEntry) {
                        Firemod.functionDataManager.focusedFunctionEntry = focusedTabLength;
                    }
                    hasClicked = true;
                    break;
                }
            }

            if (hasClicked) { continue; }

            if (rect.isPointInside(mouse)) {
                Firemod.functionDataManager.focusedFunctionEntry = rect.clickID;

                this.codespace.lerpcrollingY = 0;
                this.codespace.scrollingY = 0;

                this.codespace.lerpcrollingX = 0;
                this.codespace.scrollingX = 0;
                break;
            }
        }
        codespace.mouseClicked(mouseX, mouseY, button);

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        codespace.mouseReleased(mouseX, mouseY, button);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        codespace.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.close();
        } else {
            codespace.keyPressed(keyCode, scanCode, modifiers);
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

    protected <T extends Element & Drawable & Selectable> T addDrawableChild(T drawableElement) {
        this.drawables.add((Drawable)drawableElement);
        return this.addSelectableChild(drawableElement);
    }

    protected <T extends Drawable> T addDrawable(T drawable) {
        this.drawables.add(drawable);
        return drawable;
    }

}