package dev.fire.hyperviewer.screen.utils;

public class Point {
    public int x;
    public int y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Point(double x, double y) {
        this.x = (int) x;
        this.y = (int) y;
    }
    public Point(float x, float y) {
        this.x = (int) x;
        this.y = (int) y;
    }
    public void mergePoints(Point p) {
        this.x += p.x;
        this.y += p.y;
    }
}
