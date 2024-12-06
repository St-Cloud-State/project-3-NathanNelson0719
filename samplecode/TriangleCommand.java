//Nathan Nelson
import java.awt.*;

public class TriangleCommand extends Command {
    private Triangle triangle; // The triangle being drawn
    private int pointCount = 0; // Tracks how many points have been clicked

    public TriangleCommand(Point startPoint) {
        // Start the triangle with a degenerate line (same point for start and end)
        triangle = new Triangle(startPoint, startPoint, null);
        model.addItem(triangle); // Add the triangle to the model
        model.setChanged();
        pointCount = 1;
    }

    public void setNextPoint(Point nextPoint) {
        if (pointCount == 1) {
            // Second point clicked, update the second point of the triangle
            triangle.setPoint2(nextPoint);
            model.setChanged();
            pointCount++;
        } else if (pointCount == 2) {
            // Third point clicked, complete the triangle
            triangle.setPoint3(nextPoint);
            model.setChanged(); 
            pointCount++;
        }
    }

    @Override
    public void execute() {
        // Add the complete triangle to the model
        if (triangle.isComplete()) {
            model.addItem(triangle);
            model.setChanged(); 
        }
    }

    @Override
    public boolean undo() {
        // Remove the entire triangle from the model
        model.removeItem(triangle);
        model.setChanged();
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
