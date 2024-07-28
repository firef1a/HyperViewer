package dev.fire.firemod.screen.utils;

public class Cursor {
    public int stringX;
    public int lineY;
    public Cursor(int sx, int ly) {
        this.stringX = sx;
        this.lineY = ly;
    }

    public void set(int sx, int ly) {
        this.stringX = sx;
        this.lineY = ly;
    }
    public void set(Cursor cursor) {
        this.stringX = cursor.stringX;
        this.lineY = cursor.lineY;
    }


    public boolean isEqual(Cursor cursor) {
        return cursor.stringX == this.stringX && cursor.lineY == this.lineY;
    }
}
