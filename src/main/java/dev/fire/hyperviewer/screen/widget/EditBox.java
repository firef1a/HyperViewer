package dev.fire.hyperviewer.screen.widget;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.CursorMovement;
import net.minecraft.text.Style;
import net.minecraft.util.StringHelper;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class EditBox {
    public static final int UNLIMITED_LENGTH = Integer.MAX_VALUE;
    private static final int CURSOR_WIDTH = 2;
    public final TextRenderer textRenderer;
    public final List<EditBox.Substring> lines = Lists.newArrayList();
    public String text;
    public int cursor;
    public int selectionEnd;
    public boolean selecting;
    public int maxLength = Integer.MAX_VALUE;
    public final int width;
    private Consumer<String> changeListener = (text) -> {
    };
    private Runnable cursorChangeListener = () -> {
    };

    public EditBox(TextRenderer textRenderer, int width) {
        this.textRenderer = textRenderer;
        this.width = width;
        this.setText("");
    }

    public int getMaxLength() {
        return this.maxLength;
    }

    public void setMaxLength(int maxLength) {
        if (maxLength < 0) {
            throw new IllegalArgumentException("Character limit cannot be negative");
        } else {
            this.maxLength = maxLength;
        }
    }

    public boolean hasMaxLength() {
        return this.maxLength != Integer.MAX_VALUE;
    }

    public void setChangeListener(Consumer<String> changeListener) {
        this.changeListener = changeListener;
    }

    public void setCursorChangeListener(Runnable cursorChangeListener) {
        this.cursorChangeListener = cursorChangeListener;
    }

    public void setText(String text) {
        this.text = this.truncateForReplacement(text);
        this.cursor = this.text.length();
        this.selectionEnd = this.cursor;
        this.onChange();
    }

    public String getText() {
        return this.text;
    }

    public void replaceSelection(String string) {
        if (!string.isEmpty() || this.hasSelection()) {
            String string2 = this.truncate(SharedConstants.stripInvalidChars(string, true));
            EditBox.Substring substring = this.getSelection();
            this.text = (new StringBuilder(this.text)).replace(substring.beginIndex, substring.endIndex, string2).toString();
            this.cursor = substring.beginIndex + string2.length();
            this.selectionEnd = this.cursor;
            this.onChange();
        }
    }

    public void delete(int offset) {
        if (!this.hasSelection()) {
            this.selectionEnd = MathHelper.clamp(this.cursor + offset, 0, this.text.length());
        }

        this.replaceSelection("");
    }

    public int getCursor() {
        return this.cursor;
    }

    public void setSelecting(boolean selecting) {
        this.selecting = selecting;
    }

    EditBox.Substring getSelection() {
        return new EditBox.Substring(Math.min(this.selectionEnd, this.cursor), Math.max(this.selectionEnd, this.cursor));
    }

    public int getLineCount() {
        return this.lines.size();
    }

    public int getCurrentLineIndex() {
        for(int i = 0; i < this.lines.size(); ++i) {
            EditBox.Substring substring = (EditBox.Substring)this.lines.get(i);
            if (this.cursor >= substring.beginIndex && this.cursor <= substring.endIndex) {
                return i;
            }
        }

        return -1;
    }

    public EditBox.Substring getLine(int index) {
        return (EditBox.Substring)this.lines.get(MathHelper.clamp(index, 0, this.lines.size() - 1));
    }

    public void moveCursor(CursorMovement movement, int amount) {
        switch (movement) {
            case ABSOLUTE -> this.cursor = amount;
            case RELATIVE -> this.cursor += amount;
            case END -> this.cursor = this.text.length() + amount;
        }

        this.cursor = MathHelper.clamp(this.cursor, 0, this.text.length());
        this.cursorChangeListener.run();
        if (!this.selecting) {
            this.selectionEnd = this.cursor;
        }

    }

    public void moveCursorLine(int offset) {
        if (offset != 0) {
            int i = this.textRenderer.getWidth(this.text.substring(this.getCurrentLine().beginIndex, this.cursor)) + 2;
            EditBox.Substring substring = this.getOffsetLine(offset);
            int j = this.textRenderer.trimToWidth(this.text.substring(substring.beginIndex, substring.endIndex), i).length();
            this.moveCursor(CursorMovement.ABSOLUTE, substring.beginIndex + j);
        }
    }

    public void moveCursor(double x, double y) {
        int i = MathHelper.floor(x);
        Objects.requireNonNull(this.textRenderer);
        int j = MathHelper.floor(y / 9.0);
        EditBox.Substring substring = (EditBox.Substring)this.lines.get(MathHelper.clamp(j, 0, this.lines.size() - 1));
        int k = this.textRenderer.trimToWidth(this.text.substring(substring.beginIndex, substring.endIndex), i).length();
        this.moveCursor(CursorMovement.ABSOLUTE, substring.beginIndex + k);
    }

    public boolean handleSpecialKey(int keyCode) {
        this.selecting = Screen.hasShiftDown();
        if (Screen.isSelectAll(keyCode)) {
            this.cursor = this.text.length();
            this.selectionEnd = 0;
            return true;
        } else if (Screen.isCopy(keyCode)) {
            MinecraftClient.getInstance().keyboard.setClipboard(this.getSelectedText());
            return true;
        } else if (Screen.isPaste(keyCode)) {
            this.replaceSelection(MinecraftClient.getInstance().keyboard.getClipboard());
            return true;
        } else if (Screen.isCut(keyCode)) {
            MinecraftClient.getInstance().keyboard.setClipboard(this.getSelectedText());
            this.replaceSelection("");
            return true;
        } else {
            EditBox.Substring substring;
            switch (keyCode) {
                case 257:
                case 335:
                    this.replaceSelection("\n");
                    return true;
                case 259:
                    if (Screen.hasControlDown()) {
                        substring = this.getPreviousWordAtCursor();
                        this.delete(substring.beginIndex - this.cursor);
                    } else {
                        this.delete(-1);
                    }

                    return true;
                case 261:
                    if (Screen.hasControlDown()) {
                        substring = this.getNextWordAtCursor();
                        this.delete(substring.beginIndex - this.cursor);
                    } else {
                        this.delete(1);
                    }

                    return true;
                case 262:
                    if (Screen.hasControlDown()) {
                        substring = this.getNextWordAtCursor();
                        this.moveCursor(CursorMovement.ABSOLUTE, substring.beginIndex);
                    } else {
                        this.moveCursor(CursorMovement.RELATIVE, 1);
                    }

                    return true;
                case 263:
                    if (Screen.hasControlDown()) {
                        substring = this.getPreviousWordAtCursor();
                        this.moveCursor(CursorMovement.ABSOLUTE, substring.beginIndex);
                    } else {
                        this.moveCursor(CursorMovement.RELATIVE, -1);
                    }

                    return true;
                case 264:
                    if (!Screen.hasControlDown()) {
                        this.moveCursorLine(1);
                    }

                    return true;
                case 265:
                    if (!Screen.hasControlDown()) {
                        this.moveCursorLine(-1);
                    }

                    return true;
                case 266:
                    this.moveCursor(CursorMovement.ABSOLUTE, 0);
                    return true;
                case 267:
                    this.moveCursor(CursorMovement.END, 0);
                    return true;
                case 268:
                    if (Screen.hasControlDown()) {
                        this.moveCursor(CursorMovement.ABSOLUTE, 0);
                    } else {
                        this.moveCursor(CursorMovement.ABSOLUTE, this.getCurrentLine().beginIndex);
                    }

                    return true;
                case 269:
                    if (Screen.hasControlDown()) {
                        this.moveCursor(CursorMovement.END, 0);
                    } else {
                        this.moveCursor(CursorMovement.ABSOLUTE, this.getCurrentLine().endIndex);
                    }

                    return true;
                default:
                    return false;
            }
        }
    }

    public Iterable<EditBox.Substring> getLines() {
        return this.lines;
    }

    public boolean hasSelection() {
        return this.selectionEnd != this.cursor;
    }

    @VisibleForTesting
    public String getSelectedText() {
        EditBox.Substring substring = this.getSelection();
        return this.text.substring(substring.beginIndex, substring.endIndex);
    }

    private EditBox.Substring getCurrentLine() {
        return this.getOffsetLine(0);
    }

    private EditBox.Substring getOffsetLine(int offsetFromCurrent) {
        int i = this.getCurrentLineIndex();
        if (i < 0) {
            int var10002 = this.cursor;
            throw new IllegalStateException("Cursor is not within text (cursor = " + var10002 + ", length = " + this.text.length() + ")");
        } else {
            return (EditBox.Substring)this.lines.get(MathHelper.clamp(i + offsetFromCurrent, 0, this.lines.size() - 1));
        }
    }

    @VisibleForTesting
    public EditBox.Substring getPreviousWordAtCursor() {
        if (this.text.isEmpty()) {
            return EditBox.Substring.EMPTY;
        } else {
            int i;
            for(i = MathHelper.clamp(this.cursor, 0, this.text.length() - 1); i > 0 && Character.isWhitespace(this.text.charAt(i - 1)); --i) {
            }

            while(i > 0 && !Character.isWhitespace(this.text.charAt(i - 1))) {
                --i;
            }

            return new EditBox.Substring(i, this.getWordEndIndex(i));
        }
    }

    @VisibleForTesting
    public EditBox.Substring getNextWordAtCursor() {
        if (this.text.isEmpty()) {
            return EditBox.Substring.EMPTY;
        } else {
            int i;
            for(i = MathHelper.clamp(this.cursor, 0, this.text.length() - 1); i < this.text.length() && !Character.isWhitespace(this.text.charAt(i)); ++i) {
            }

            while(i < this.text.length() && Character.isWhitespace(this.text.charAt(i))) {
                ++i;
            }

            return new EditBox.Substring(i, this.getWordEndIndex(i));
        }
    }

    private int getWordEndIndex(int startIndex) {
        int i;
        for(i = startIndex; i < this.text.length() && !Character.isWhitespace(this.text.charAt(i)); ++i) {
        }

        return i;
    }

    private void onChange() {
        this.rewrap();
        this.changeListener.accept(this.text);
        this.cursorChangeListener.run();
    }

    private void rewrap() {
        this.lines.clear();
        if (this.text.isEmpty()) {
            this.lines.add(EditBox.Substring.EMPTY);
        } else {
            this.textRenderer.getTextHandler().wrapLines(this.text, this.width, Style.EMPTY, false, (style, start, end) -> {
                this.lines.add(new EditBox.Substring(start, end));
            });
            if (this.text.charAt(this.text.length() - 1) == '\n') {
                this.lines.add(new EditBox.Substring(this.text.length(), this.text.length()));
            }

        }
    }

    private String truncateForReplacement(String value) {
        return this.hasMaxLength() ? StringHelper.truncate(value, this.maxLength, false) : value;
    }

    private String truncate(String value) {
        if (this.hasMaxLength()) {
            int i = this.maxLength - this.text.length();
            return StringHelper.truncate(value, i, false);
        } else {
            return value;
        }
    }

    

    @Environment(EnvType.CLIENT)
    protected static record Substring(int beginIndex, int endIndex) {
        static final Substring EMPTY = new Substring(0, 0);

        protected Substring(int beginIndex, int endIndex) {
            this.beginIndex = beginIndex;
            this.endIndex = endIndex;
        }

        public int beginIndex() {
            return this.beginIndex;
        }

        public int endIndex() {
            return this.endIndex;
        }
    }
}
