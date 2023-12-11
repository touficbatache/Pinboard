package pobj.pinboard.editor.commands;

import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;

import java.util.ArrayList;
import java.util.List;

public class CommandAdd implements Command {
    private final List<Clip> clips;
    private final EditorInterface editor;

    public CommandAdd(EditorInterface editor, Clip toAdd) {
        this.editor = editor;
        clips = new ArrayList<>();
        clips.add(toAdd);
    }

    public CommandAdd(EditorInterface editor, List<Clip> toAdd) {
        this.editor = editor;
        clips = new ArrayList<>();
        clips.addAll(toAdd);
    }

    @Override
    public void execute() {
        editor.getBoard().addClip(clips);
    }

    @Override
    public void undo() {
        editor.getBoard().removeClip(clips);
    }
}
