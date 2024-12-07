import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Enumeration;

public class MoveButton extends JButton implements ActionListener {
    private JPanel drawingPanel;
    private View view;
    private UndoManager undoManager; // Reference to the UndoManager
    private MouseHandler mouseHandler;
    private Point startPoint;

    public MoveButton(View view, JPanel drawingPanel, UndoManager undoManager) {
        super("Move");
        this.view = view;
        this.drawingPanel = drawingPanel;
        this.undoManager = undoManager; // Assign the UndoManager
        this.mouseHandler = new MouseHandler();
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        view.setCursor(new Cursor(Cursor.MOVE_CURSOR)); // Change cursor to move mode
        drawingPanel.addMouseListener(mouseHandler);
        drawingPanel.addMouseMotionListener(mouseHandler);
    }

    private class MouseHandler extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent event) {
            startPoint = View.mapPoint(event.getPoint()); // Record starting point
        }

        @Override
        public void mouseReleased(MouseEvent event) {
            if (startPoint == null) return;

            Point endPoint = View.mapPoint(event.getPoint()); // Record ending point
            int dx = endPoint.x - startPoint.x;
            int dy = endPoint.y - startPoint.y;

            // Get selected items from the model
            ArrayList<Item> selectedItems = new ArrayList<>();
            Enumeration<Item> items = Command.model.getSelectedItems();
            while (items.hasMoreElements()) {
                selectedItems.add(items.nextElement());
            }

            if (!selectedItems.isEmpty()) {
                // Create and execute a MoveCommand
                MoveCommand moveCommand = new MoveCommand(selectedItems, dx, dy);
                undoManager.beginCommand(moveCommand);
                undoManager.endCommand(moveCommand);
            }

            // Reset state
            startPoint = null;
            drawingPanel.removeMouseListener(this);
            drawingPanel.removeMouseMotionListener(this);
            view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Reset cursor
            view.refresh(); // Refresh canvas
        }
    }
}
