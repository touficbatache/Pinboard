package pobj.pinboard.editor;

import pobj.pinboard.editor.commands.Command;

import java.util.ArrayList;
import java.util.List;

public class CommandStack {
    private final List<Command> undoStack;
    private final List<Command> redoStack;
    private final List<CommandStackListener> listeners;

    public CommandStack() {
        undoStack = new ArrayList<>();
        redoStack = new ArrayList<>();
        listeners = new ArrayList<>();
    }

    public void addCommand(Command cmd) {
        undoStack.add(cmd);
        redoStack.clear();
        onChange();
    }

    public void undo() {
        if (isUndoEmpty()) {
            return;
        }

        Command cmd = undoStack.get(undoStack.size() - 1);
        cmd.undo();
        undoStack.remove(cmd);
        redoStack.add(cmd);
        onChange();
    }

    public void redo() {
        if (isRedoEmpty()) {
            return;
        }

        Command cmd = redoStack.get(redoStack.size() - 1);
        cmd.execute();
        redoStack.remove(cmd);
        undoStack.add(cmd);
        onChange();
    }

    public boolean isUndoEmpty() {
        return undoStack.isEmpty();
    }

    public boolean isRedoEmpty() {
        return redoStack.isEmpty();
    }

    public void addListener(CommandStackListener listener) {
        listeners.add(listener);
    }

    public void removeListener(CommandStackListener listener) {
        listeners.remove(listener);
    }

    private void onChange() {
        for (CommandStackListener listener : listeners) {
            listener.commandStackChanged();
        }
    }
}
