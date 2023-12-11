package pobj.pinboard.editor.commands;

import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipGroup;
import pobj.pinboard.editor.EditorInterface;

import java.util.List;

public class CommandUngroup implements Command {
    private final ClipGroup clipGroup;
    private final EditorInterface editor;

    private List<Clip> clips;

    public CommandUngroup(EditorInterface editor, ClipGroup clipGroup) {
        this.editor = editor;
        this.clipGroup = clipGroup;
    }

    @Override
    public void execute() {
        editor.getBoard().removeClip(clipGroup);
        clips = clipGroup.getClips();
        editor.getBoard().addClip(clipGroup.getClips());
    }

    @Override
    public void undo() {
        editor.getBoard().removeClip(clips);
        editor.getBoard().addClip(clipGroup);
    }
}
