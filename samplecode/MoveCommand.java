import java.awt.*;
import java.util.ArrayList;

public class MoveCommand extends Command {
    private ArrayList<Item> items; // The items to be moved
    private int dx, dy; // The translation vector

    public MoveCommand(ArrayList<Item> items, int dx, int dy) {
        this.items = new ArrayList<>(items); // Copy the list of items to ensure isolation
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void execute() {
        for (Item item : items) {
            item.translate(dx, dy); // Apply the translation
        }
        model.setChanged(); // Refresh the canvas
    }

    @Override
    public boolean undo() {
        for (Item item : items) {
            item.translate(-dx, -dy); // Reverse the translation
        }
        model.setChanged(); // Refresh the canvas
        return true;
    }

    @Override
    public boolean redo() {
        execute(); // Reapply the translation
        return true;
    }
}
