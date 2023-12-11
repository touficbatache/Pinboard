package pobj.pinboard.editor.commands;

import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;

import java.util.ArrayList;
import java.util.List;

public class CommandMove implements Command {
    private final List<Clip> clips;
    private final double x, y;

    public CommandMove(EditorInterface editor, Clip clip, double x, double y) {
        clips = new ArrayList<>();
        clips.add(clip);
        this.x = x;
        this.y = y;
    }

    public CommandMove(EditorInterface editor, List<Clip> clip, double x, double y) {
        clips = new ArrayList<>();
        clips.addAll(clip);
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute() {
        for (Clip clip : clips) {
            clip.move(x, y);
        }
    }

    @Override
    public void undo() {
        for (Clip clip : clips) {
            clip.move(-x, -y);
        }
    }
}
