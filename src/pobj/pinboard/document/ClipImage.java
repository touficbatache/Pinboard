package pobj.pinboard.document;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ClipImage extends AbstractClip implements Clip {
    private final Image image;

    public ClipImage(double left, double top, Image image) {
        super(left, top, left + image.getWidth(), top + image.getHeight(), null);

        this.image = image;
    }

    @Override
    public void draw(GraphicsContext ctx) {
        ctx.drawImage(image, getLeft(), getTop());
    }

    @Override
    public boolean isSelected(double x, double y) {
        return (x < getRight() && x > getLeft() && y > getTop() && y < getBottom());
    }

    @Override
    public Clip copy() {
        return new ClipImage(getLeft(), getTop(), image);
    }

    public Image getImage() {
        return image;
    }
}
