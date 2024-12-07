//Nathan Nelson

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PolygonButton extends JButton implements ActionListener {
    private JPanel drawingPanel;
    private View view;
    private UndoManager undoManager;
    private PolygonCommand polygonCommand;
    private MouseHandler mouseHandler;

    public PolygonButton(UndoManager undoManager, View view, JPanel drawingPanel) {
        super("Polygon");
        this.undoManager = undoManager;
        this.view = view;
        this.drawingPanel = drawingPanel;
        this.mouseHandler = new MouseHandler();
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        view.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR)); 
        drawingPanel.addMouseListener(mouseHandler);
    }

    private class MouseHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {
            Point clickPoint = View.mapPoint(event.getPoint());

            if (SwingUtilities.isLeftMouseButton(event)) {
                if (polygonCommand == null) {
                    polygonCommand = new PolygonCommand(clickPoint);
                    undoManager.beginCommand(polygonCommand);
                } else {
                    polygonCommand.addPoint(clickPoint); // Add a point to the current PolygonCommand
                }
            } else if (SwingUtilities.isRightMouseButton(event)) {
                if (polygonCommand != null) {
                    polygonCommand.closePolygon(); // Close the polygon on a right-click
                    undoManager.endCommand(polygonCommand); 
                    polygonCommand = null; 
                    drawingPanel.removeMouseListener(this); 
                    view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
            view.refresh(); // Refresh the canvas after each action
        }
    }
}
