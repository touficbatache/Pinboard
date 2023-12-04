package pobj.pinboard.editor;

import pobj.pinboard.document.Clip;

import java.util.ArrayList;
import java.util.List;

public class Clipboard {
    private static Clipboard clipboard;

    public static Clipboard getInstance() {
        if (clipboard == null) {
            clipboard = new Clipboard();
        }
        return clipboard;
    }

    private final List<Clip> clips;
    private final List<ClipboardListener> listeners;

    private Clipboard() {
        clips = new ArrayList<>();
        listeners = new ArrayList<>();
    }

    public void copyToClipboard(List<Clip> clips) {
        for (Clip clip : clips) {
            this.clips.add(clip.copy());
        }
        for (ClipboardListener listener : listeners) {
            listener.clipboardChanged();
        }
    }

    public List<Clip> copyFromClipboard() {
        List<Clip> copied = new ArrayList<>();
        for (Clip clip : clips) {
            copied.add(clip.copy());
        }
        return copied;
    }

    public void clear() {
        clips.clear();
        for (ClipboardListener listener : listeners) {
            listener.clipboardChanged();
        }
    }

    public boolean isEmpty() {
        return clips.isEmpty();
    }

    public void addListener(ClipboardListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ClipboardListener listener) {
        listeners.remove(listener);
    }
}
