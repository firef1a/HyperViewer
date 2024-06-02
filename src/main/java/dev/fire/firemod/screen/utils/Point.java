package dev.fire.firemod.screen.utils;

public class Point {
    public int x;
    public int y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void mergePoints(Point p) {
        this.x += p.x;
        this.y += p.y;
    }
}
