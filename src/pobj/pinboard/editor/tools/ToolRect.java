package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.ClipRect;
import pobj.pinboard.editor.EditorInterface;

public class ToolRect implements Tool {
    private double startX, startY, endX, endY;
    private Color color = Color.BLUE;

    @Override
    public void press(EditorInterface i, MouseEvent e) {
        startX = e.getX();
        startY = e.getY();
    }

    @Override
    public void drag(EditorInterface i, MouseEvent e) {
        endX = e.getX();
        endY = e.getY();
    }

    @Override
    public void release(EditorInterface i, MouseEvent e) {
        if (e.getX() < startX) {
            endX = startX;
            startX = e.getX();
        }

        if (e.getY() < startY) {
            endY = startY;
            startY = e.getY();
        }

        i.getBoard().addClip(new ClipRect(startX, startY, endX, endY, color));
    }

    @Override
    public void drawFeedback(EditorInterface i, GraphicsContext gc) {
        double x = Math.min(startX, endX);
        double y = Math.min(startY, endY);
        double width = Math.max(startX, endX) - x;
        double height = Math.max(startY, endY) - y;

        i.getBoard().draw(gc);

        gc.strokeRect(x, y, width, height);
    }

    @Override
    public String getName(EditorInterface editor) {
        return "Rectangle";
    }
}
