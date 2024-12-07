//Nathan Nelson

import java.awt.*;
import java.util.ArrayList;

public class PolygonItem extends Item {
    private ArrayList<Line> lines; // Lines to create the object. Degenerate line will be used.
    private boolean closed = false;

    public PolygonItem(Point startPoint) {
        lines = new ArrayList<>(); 
        Line degenerateLine = new Line(startPoint, startPoint); // Start with a degenerate line
        lines.add(degenerateLine);
    }

    public void addPoint(Point newPoint) {
        if (!closed) {
            Line lastLine = lines.get(lines.size() - 1);
            lastLine.setPoint2(newPoint);
            Line newLine = new Line(newPoint, newPoint);
            lines.add(newLine);
        }
    }

    public void close() {
        if (!closed && lines.size() > 1) {
            Line closingLine = new Line(lines.get(lines.size() - 1).getPoint2(), lines.get(0).getPoint1());
            lines.add(closingLine); // Close the polygon by connecting the first and last points \//\/\//\/YAY!
            closed = true;
        }
    }

    public boolean isClosed() {
        return closed;
    }

    @Override
    public boolean includes(Point point) {
        for (Line line : lines) {
            if (line.includes(point)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void render(UIContext uiContext) {
        for (Line line : lines) {
            line.render(uiContext);
        }
    }
}
