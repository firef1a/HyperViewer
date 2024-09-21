package dev.fire.hyperviewer.screen.utils;

public class RectBorder {
    public boolean enabled;
    public int color;
    public int size;
    public RectBorder(boolean enabled) {
        this.enabled = enabled;
    }
    public RectBorder(boolean enabled, int color, int size) {
        this.enabled = enabled;
        this.color = color;
        this.size = size;
    }
}
