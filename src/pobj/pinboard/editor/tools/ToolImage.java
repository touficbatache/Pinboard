package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import pobj.pinboard.document.ClipImage;
import pobj.pinboard.editor.EditorInterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ToolImage implements Tool {
    private final Image image;

    private double x, y;

    public ToolImage(File file) throws FileNotFoundException {
        image = new Image(new FileInputStream(file.getAbsolutePath()));
    }

    @Override
    public void press(EditorInterface i, MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void drag(EditorInterface i, MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void release(EditorInterface i, MouseEvent e) {
        i.getBoard().addClip(new ClipImage(x, y, image));
    }

    @Override
    public void drawFeedback(EditorInterface i, GraphicsContext gc) {
        i.getBoard().draw(gc);
        gc.drawImage(image, x, y);
    }

    @Override
    public String getName(EditorInterface editor) {
        return "Image";
    }
}
