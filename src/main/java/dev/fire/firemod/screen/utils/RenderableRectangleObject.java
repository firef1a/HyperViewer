package dev.fire.firemod.screen.utils;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.collection.Pool;

import java.util.ArrayList;

public class RenderableRectangleObject {
    public int x;
    public int y;
    public int width;
    public int height;

    public double scrollingY = 0;
    public double lerpcrollingY = 0;
    public double lerpScrollAmount = 0.25;

    public ArrayList<RenderableRectangleObject> siblings;
    public ArrayList<RenderableRectangleObject> preSiblings;
    public ArrayList<Widget> widgets;
    public int color;
    public RenderableRectangleObject parent;
    public RenderableRectangleObject listRect;

    public int xBinding = 0;
    public int yBinding = 0;

    public int clickID;

    public RectBorder topBorder = new RectBorder(false);
    public RectBorder rightBorder = new RectBorder(false);
    public RectBorder bottomBorder = new RectBorder(false);
    public RectBorder leftBorder = new RectBorder(false);

    public RenderableRectangleObject(int x, int y, int width, int height, int color) {
        this.siblings = new ArrayList<RenderableRectangleObject>();
        this.preSiblings = new ArrayList<RenderableRectangleObject>();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }
    public RenderableRectangleObject(int x, int y, int width, int height) {
        this.siblings = new ArrayList<RenderableRectangleObject>();
        this.preSiblings = new ArrayList<RenderableRectangleObject>();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(DrawContext context, int mouseX, int mouseY, int parentx, int parenty, int parentWidth, int parentHeight) {
        int dx = x+parentx + (parentWidth*this.xBinding);
        int dy = (int) (y+parenty + (parentHeight*this.yBinding) + scrollingY);

        preSiblings.forEach(obj -> obj.render(context, mouseX, mouseY, dx,dy,dx+width,dy+height));

        context.fill(dx, dy, dx+width, dy+height, color);

        siblings.forEach(obj -> obj.render(context, mouseX, mouseY, dx,dy,dx+width,dy+height));

        if (this.topBorder.enabled) { context.fill(dx, dy-this.topBorder.size, dx+width, dy, this.topBorder.color); }
        if (this.bottomBorder.enabled) { context.fill(dx, dy+height, dx+width, dy+height+this.bottomBorder.size, this.bottomBorder.color); }
        if (this.rightBorder.enabled) { context.fill(dx+    width, dy, dx+width+this.rightBorder.size, dy+height, this.rightBorder.color); }
        if (this.leftBorder.enabled) { context.fill(dx-this.leftBorder.size, dy, dx, dy+height, this.leftBorder.color); }

        this.scrollingY = MathUtils.lerp(this.scrollingY, this.lerpcrollingY, this.lerpScrollAmount);
    }

    public Point getScreenPosition() {
        int yval = (int) (this.y+scrollingY);
        if (this.parent == null) {
            return new Point(this.x, yval);
        } else {
            Point parentPoint = this.parent.getScreenPosition();
            return new Point(parentPoint.x+yval+(parent.width*this.xBinding),parentPoint.y+yval+(parent.height*this.yBinding)+parent.scrollingY);
        }
    }
    public boolean isPointInside(Point point){
        Point screenpos = getScreenPosition();
        return point.x > screenpos.x && point.x < screenpos.x + width && point.y > screenpos.y && point.y < screenpos.y + height;
    }

    public int setAlpha(int color, float alpha) {return (color+ ((int)(alpha*255)<<24));}

    public void addSibling(RenderableRectangleObject object) {
        object.parent = this;
        siblings.add(object);
    }

    public void addPreSibling(RenderableRectangleObject object) {
        object.parent = this;
        preSiblings.add(object);
    }

    public void setBinding(int xb, int yb) {
        this.xBinding = xb;
        this.yBinding = yb;
    }

    public void resetBinding() {
        xBinding = 0;
        yBinding = 0;
    }

    public void setCenter(int cx, int cy) {
        this.x = cx-this.width/2;
        this.y = cy-this.height/2;
    }
    public void setCenter(Point p) {
        this.x = p.x-this.width/2;
        this.y = p.y-this.height/2;
    }

    public Point getCenter() {
        return new Point(this.x+this.width/2,this.y+this.height/2);
    }

    public Point getScreenCenter() {
        Point center = getScreenPosition();
        return new Point(center.x+this.width/2,center.y+this.height/2);
    }

    public void setTopBorder(boolean enabled, int color, int size) {
        this.topBorder.enabled = enabled;
        this.topBorder.color = color;
        this.topBorder.size = size;
    }
    public void setLeftBorder(boolean enabled, int color, int size) {
        this.leftBorder.enabled = enabled;
        this.leftBorder.color = color;
        this.leftBorder.size = size;
    }
    public void setRightBorder(boolean enabled, int color, int size) {
        this.rightBorder.enabled = enabled;
        this.rightBorder.color = color;
        this.rightBorder.size = size;
    }
    public void setBottomBorder(boolean enabled, int color, int size) {
        this.bottomBorder.enabled = enabled;
        this.bottomBorder.color = color;
        this.bottomBorder.size = size;
    }
}
