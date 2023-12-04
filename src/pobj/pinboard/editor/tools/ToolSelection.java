package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;

public class ToolSelection implements Tool {
    private double x, y;

    @Override
    public void press(EditorInterface i, MouseEvent e) {
        x = e.getX();
        y = e.getY();

        if (!e.isShiftDown()) {
            i.getSelection().select(i.getBoard(), e.getX(), e.getY());
        } else {
            i.getSelection().toggleSelect(i.getBoard(), e.getX(), e.getY());
        }
    }

    @Override
    public void drag(EditorInterface i, MouseEvent e) {
        for (Clip content : i.getSelection().getContents()) {
            content.move(e.getX() - x, e.getY() - y);
        }

        x = e.getX();
        y = e.getY();
    }

    @Override
    public void release(EditorInterface i, MouseEvent e) {

    }

    @Override
    public void drawFeedback(EditorInterface i, GraphicsContext gc) {
        i.getBoard().draw(gc);
        i.getSelection().drawFeedback(gc);
    }

    @Override
    public String getName(EditorInterface editor) {
        return "Selection";
    }

    @Override
    public void setColor(Color color) {

    }
}
