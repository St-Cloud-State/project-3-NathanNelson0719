//Nathan Nelson

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TriangleButton extends JButton implements ActionListener {
    private JPanel drawingPanel;
    private View view;
    private UndoManager undoManager;
    private TriangleCommand triangleCommand;
    private MouseHandler mouseHandler;

    public TriangleButton(UndoManager undoManager, View view, JPanel drawingPanel) {
        super("Triangle");
        this.undoManager = undoManager;
        this.view = view;
        this.drawingPanel = drawingPanel;
        this.mouseHandler = new MouseHandler();
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        view.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR)); // Change the cursor
        drawingPanel.addMouseListener(mouseHandler); // Start listening for user click
    }

    private class MouseHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {
            Point clickPoint = View.mapPoint(event.getPoint());

            if (triangleCommand == null) {
                triangleCommand = new TriangleCommand(clickPoint); // Begin a new TriangleCommand on the first click
                undoManager.beginCommand(triangleCommand);
            } else {
                triangleCommand.setNextPoint(clickPoint);
                if (triangleCommand.end()) {
                    undoManager.endCommand(triangleCommand);
                    triangleCommand = null;
                    drawingPanel.removeMouseListener(this);
                    view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); 
                }
            }
            view.refresh(); // Update the canvas after each click
        }

        @Override
        public void mousePressed(MouseEvent event) {// This will cancel the command on right-click
            if (SwingUtilities.isRightMouseButton(event)) {
                if (triangleCommand != null) { 
                    undoManager.cancelCommand();
                    triangleCommand = null;
                    drawingPanel.removeMouseListener(this);
                    view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Reset the cursor
                    view.refresh();
                }
            }
        }
    }
}
