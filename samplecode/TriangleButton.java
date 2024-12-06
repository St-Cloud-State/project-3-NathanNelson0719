//Nathan N. Triangle button on canvas
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class TriangleButton extends JButton implements ActionListener {
    private JPanel drawingPanel;
    private View view;
    private UndoManager undoManager;
    private MouseHandler mouseHandler;
    private TriangleCommand triangleCommand;

    public TriangleButton(UndoManager undoManager, View view, JPanel drawingPanel) {
        super("Triangle");
        this.undoManager = undoManager;
        this.view = view;
        this.drawingPanel = drawingPanel;
        addActionListener(this);
        mouseHandler = new MouseHandler();
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
            if (triangleCommand == null) {
                triangleCommand = new TriangleCommand(clickPoint);
                undoManager.beginCommand(triangleCommand);
            } else {
                triangleCommand.setNextPoint(clickPoint);
                if (triangleCommand.end()) {
                    drawingPanel.removeMouseListener(this);
                    undoManager.endCommand(triangleCommand);
                    triangleCommand = null;
                    view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
            view.refresh();
        }
    }
}
