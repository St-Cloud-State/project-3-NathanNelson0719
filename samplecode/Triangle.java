//Nathan N. Triangle
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

    @Override
    public boolean includes(Point point) {
        // Check proximity to any vertex for simplicity
        return distance(point, point1) < 10.0 ||
               distance(point, point2) < 10.0 ||
               distance(point, point3) < 10.0;
    }

    @Override
    public void render(UIContext uiContext) {
        uiContext.drawTriangle(point1, point2, point3);
    }

    public Point getPoint1() { return point1; }
    public Point getPoint2() { return point2; }
    public Point getPoint3() { return point3; }
}
