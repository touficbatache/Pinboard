package pobj.pinboard.editor.commands;

import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipGroup;
import pobj.pinboard.editor.EditorInterface;

import java.util.List;

public class CommandGroup implements Command {
    private final List<Clip> clips;
    private final EditorInterface editor;

    private ClipGroup clipGroup;

    public CommandGroup(EditorInterface editor, List<Clip> clip) {
        this.editor = editor;
        clips = clip;
    }

    @Override
    public void execute() {
        editor.getBoard().removeClip(clips);
        if (clipGroup == null) {
            clipGroup = new ClipGroup();
            for (Clip clip : clips) {
                clipGroup.addClip(clip);
            }
        }
        editor.getBoard().addClip(clipGroup);
    }

    @Override
    public void undo() {
        editor.getBoard().removeClip(clipGroup);
        editor.getBoard().addClip(clipGroup.getClips());
    }
}
