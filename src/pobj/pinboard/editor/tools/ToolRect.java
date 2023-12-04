package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.ClipRect;
import pobj.pinboard.editor.EditorInterface;

public class ToolRect implements Tool {
    private double startX = -1, startY = -1, endX = -1, endY = -1;
    private Color color;

    public ToolRect() {
        color = Color.BLUE;
    }

    public ToolRect(Color color) {
        this.color = color;
    }

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

        startX = -1;
        startY = -1;
        endX = -1;
        endY = -1;
    }

    @Override
    public void drawFeedback(EditorInterface i, GraphicsContext gc) {
        if (startX == -1 || startY == -1 || endX == -1 || endY == -1) {
            return;
        }

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

    @Override
    public void setColor(Color color) {
        this.color = color;
    }
}
