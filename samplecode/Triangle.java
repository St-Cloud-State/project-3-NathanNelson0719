//Nathan Nelson

import java.awt.*;

public class Triangle extends Item {
    private Point point1;
    private Point point2;
    private Point point3;

    public Triangle(Point point1, Point point2, Point point3) {
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
    }

    public void setPoint2(Point point2) {
        this.point2 = point2;
    }

    public void setPoint3(Point point3) {
        this.point3 = point3;
    }

    public boolean isComplete() {
        return point1 != null && point2 != null && point3 != null;
    }

    @Override
    public boolean includes(Point point) { 
        return distance(point, point1) < 10.0 ||
               (point2 != null && distance(point, point2) < 10.0) ||
               (point3 != null && distance(point, point3) < 10.0);
    }

    @Override
    public void render(UIContext uiContext) {
        if (point1 != null && point2 != null) {
            uiContext.drawLine(point1, point2); 
        }
        if (point2 != null && point3 != null) {
            uiContext.drawLine(point2, point3); 
        }
        if (point3 != null && point1 != null) {
            uiContext.drawLine(point3, point1); 
        }
    }

    @Override
    public void translate(int dx, int dy) {
        // Translate each point exactly once
        if (point1 != null) {
            point1.translate(dx, dy);
        }
        if (point2 != null) {
            point2.translate(dx, dy);
        }
        if (point3 != null) {
            point3.translate(dx, dy);
        }
    }
}
