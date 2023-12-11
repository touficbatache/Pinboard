package pobj.pinboard.document;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class ClipGroup extends AbstractClip implements Composite {
    private final List<Clip> clips;

    public ClipGroup() {
        super(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, null);

        clips = new ArrayList<>();
    }

    @Override
    public void draw(GraphicsContext ctx) {
        for (Clip clip : clips) {
            clip.draw(ctx);
        }
    }

    @Override
    public void move(double x, double y) {
        super.move(x, y);

        for (Clip clip : clips) {
            clip.move(x, y);
        }
    }

    @Override
    public boolean isSelected(double x, double y) {
        for (Clip clip : clips) {
            if (clip.isSelected(x, y))
                return true;
        }
        return false;
    }

    @Override
    public Clip copy() {
        ClipGroup clipGroup = new ClipGroup();
        for (Clip clip : clips) {
            clipGroup.addClip(clip.copy());
        }
        return clipGroup;
    }

    @Override
    public List<Clip> getClips() {
        return clips;
    }

    @Override
    public void addClip(Clip toAdd) {
        clips.add(toAdd);
        updateGeometry();
    }

    @Override
    public void removeClip(Clip toRemove) {
        clips.remove(toRemove);
        updateGeometry();
    }

    private void updateGeometry() {
        setGeometry(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);

        for (Clip clip : clips) {
            setGeometry(
                    Math.min(getLeft(), clip.getLeft()),
                    Math.min(getTop(), clip.getTop()),
                    Math.max(getRight(), clip.getRight()),
                    Math.max(getBottom(), clip.getBottom())
            );
        }
    }
}
