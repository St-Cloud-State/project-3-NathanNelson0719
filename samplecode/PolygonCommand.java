//Nathan Nelson

import java.awt.*;
import java.util.Enumeration;

public class PolygonCommand extends Command {
    private PolygonItem polygon; // Represents the entire polygon as one object that can Undo, Redo, and Move later on.
    private boolean finalized = false; 

    public PolygonCommand(Point startPoint) {
        polygon = new PolygonItem(startPoint);
        model.addItem(polygon); 
        model.setChanged(); 
    }

    public void addPoint(Point newPoint) {
        if (!finalized) {
            polygon.addPoint(newPoint); 
            model.setChanged(); 
        }
    }

    public void closePolygon() {
        if (!finalized) {
            polygon.close(); 
            model.setChanged(); 
            finalized = true;
        }
    }

    @Override
    public void execute() {
    // Ensure the polygon is added to the model
        boolean alreadyExists = false;
        Enumeration<Item> items = model.getItems();

        while (items.hasMoreElements()) {
            if (items.nextElement() == polygon) {
                alreadyExists = true;
                break;
            }
        }

        if (!alreadyExists) {
            model.addItem(polygon);
            model.setChanged(); // Refresh the canvas
        }
    }

    @Override
    public boolean undo() {
        model.removeItem(polygon); // Remove the entire polygon from the model
        model.setChanged(); // Refresh the canvas
        return true;
    }

    @Override
    public boolean redo() {
        execute(); // Re-add the polygon to the model
        return true;
    }

    @Override
    public boolean end() {
        return finalized; // Command ends only when the polygon is finalized
    }
}
