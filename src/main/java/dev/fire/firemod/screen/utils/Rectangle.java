package dev.fire.firemod.screen.utils;

import net.minecraft.client.gui.DrawContext;

public class Rectangle {
    public int x;
    public int y;
    public int width;
    public int height;
    private int cx;
    private int cy;

    public Rectangle(int x1, int y1, int width, int height) {
        this.x = x1;
        this.y = y1;
        this.width = width;
        this.height = height;
    }
}
