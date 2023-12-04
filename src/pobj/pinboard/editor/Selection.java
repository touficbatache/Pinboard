package pobj.pinboard.editor;

import javafx.scene.canvas.GraphicsContext;
import pobj.pinboard.document.Board;
import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipEllipse;

import java.util.ArrayList;
import java.util.List;

public class Selection {
    private final List<Clip> clips;

    public Selection() {
        clips = new ArrayList<>();
    }

    public void select(Board board, double x, double y) {
        clear();
        for (int i = board.getContents().size() - 1; i >= 0; i--) {
            Clip content = board.getContents().get(i);
            if (content.isSelected(x, y)) {
                clips.add(content);
                return;
            }
        }
    }

    public void toggleSelect(Board board, double x, double y) {
        for (int i = board.getContents().size() - 1; i >= 0; i--) {
            Clip content = board.getContents().get(i);
            if (content.isSelected(x, y)) {
                if (clips.contains(content)) {
                    clips.remove(content);
                } else {
                    clips.add(content);
                }
                return;
            }
        }
    }

    public void clear() {
        clips.clear();
    }

    public List<Clip> getContents() {
        return clips;
    }

    public void drawFeedback(GraphicsContext gc) {
        int gap = 4;

        double left = -1;
        double top = -1;
        double right = -1;
        double bottom = -1;

        for (Clip clip : clips) {
            if (left == -1) {
                left = clip.getLeft();
            } else {
                left = Math.min(left, clip.getLeft());
            }

            if (top == -1) {
                top = clip.getTop();
            } else {
                top = Math.min(top, clip.getTop());
            }

            if (right == -1) {
                right = clip.getRight();
            } else {
                right = Math.max(right, clip.getRight());
            }

            if (bottom == -1) {
                bottom = clip.getBottom();
            } else {
                bottom = Math.max(bottom, clip.getBottom());
            }
        }

        if (left == -1 || top == -1 || right == -1 || bottom == -1) {
            left = -1;
            top = -1;
            right = -1;
            bottom = -1;
            return;
        }

        left -= gap;
        top -= gap;
        right += gap;
        bottom += gap;

        if (left == -1 || top == -1 || right == -1 || bottom == -1) {
            return;
        }

        double originalLw = gc.getLineWidth();
        gc.setLineWidth(2);
        if (clips.size() == 1) {
            if (clips.get(0) instanceof ClipEllipse) {
                gc.strokeOval(left, top, right - left, bottom - top);
            } else {
                gc.strokeRect(left, top, right - left, bottom - top);
            }
        } else {
            for (Clip clip : clips) {
                if (clip instanceof ClipEllipse) {
                    gc.strokeOval(clip.getLeft(), clip.getTop(), clip.getRight() - clip.getLeft(), clip.getBottom() - clip.getTop());
                } else {
                    gc.strokeRect(clip.getLeft(), clip.getTop(), clip.getRight() - clip.getLeft(), clip.getBottom() - clip.getTop());
                }
            }
            gc.strokeRect(left, top, right - left, bottom - top);
        }
        gc.setLineWidth(originalLw);
    }
}
