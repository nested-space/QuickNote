package com.edenrump.clipboard;

import javafx.animation.AnimationTimer;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;

import java.util.ArrayList;
import java.util.List;

public class ClipboardListener {

    private Clipboard clipboard = Clipboard.getSystemClipboard();
    private ClipboardContent oldContent = new ClipboardContent();
    private List<Runnable> updateMethods = new ArrayList<>();
    final AnimationTimer timer = new AnimationTimer() {
        private long lastUpdate = 0;

        @Override
        public void handle(long time) {
            long divisor = (long) 1e6;
            long currentTime = time / divisor;
            //initialise
            if (lastUpdate == 0) {
                lastUpdate = time / divisor;
            }
            long difference = currentTime - lastUpdate;
            if (difference > 1000) {
                lastUpdate = currentTime;
                if (contentChanged()) updateContent();
            }
        }
    };

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    private void updateContent() {
        for (Runnable r : updateMethods) {
            r.run();
        }
        copyContent(clipboard, oldContent);
    }

    private boolean contentChanged() {
        DataFormat dataFormat = DataFormat.PLAIN_TEXT;
        if (clipboard.getFiles().size() > 0) {
            dataFormat = DataFormat.FILES;
        }

        try {
            if (oldContent.get(dataFormat) == null) return true;
            if (!oldContent.get(dataFormat).toString().equals(clipboard.getContent(dataFormat).toString())) return true;
        } catch (Exception e) {
            return true;
        }

        return false;
    }

    private void copyContent(Clipboard clipboard, ClipboardContent oldContent) {
        oldContent.put(DataFormat.PLAIN_TEXT, clipboard.getContent(DataFormat.PLAIN_TEXT));
        oldContent.put(DataFormat.FILES, clipboard.getContent(DataFormat.FILES));
    }

    public void addUpdateMethod(Runnable runnable) {
        updateMethods.add(runnable);
    }

    public void removeUpdateMethod(Runnable runnable) {
        updateMethods.remove(runnable);
    }

    public void setUpdateMethods(List<Runnable> runnables) {
        updateMethods = new ArrayList<>(runnables);
    }

    public void clearUpdateMethods() {
        updateMethods = new ArrayList<>();
    }

}
