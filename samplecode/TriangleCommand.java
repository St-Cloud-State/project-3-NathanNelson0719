//Nathan Nelson. Traingle Command
import java.awt.*;

public class TriangleCommand extends Command {
    private Triangle triangle;
    private int pointCount = 0;

    public TriangleCommand(Point point) {
        this.triangle = new Triangle(point, null, null);
        pointCount = 1;
    }

    public void setNextPoint(Point point) {
        if (pointCount == 1) {
            triangle = new Triangle(triangle.getPoint1(), point, null);
            pointCount++;
        } else if (pointCount == 2) {
            triangle = new Triangle(triangle.getPoint1(), triangle.getPoint2(), point);
            pointCount++;
        }
    }

    @Override
    public void execute() {
        model.addItem(triangle);
    }

    @Override
    public boolean undo() {
        model.removeItem(triangle);
        return true;
    }

    @Override
    public boolean redo() {
        execute();
        return true;
    }

    @Override
    public boolean end() {
        return pointCount == 3;
    }
}
