package com.edenrump.clipboard;

import javafx.animation.AnimationTimer;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a timer that periodically tests for changes to the system clipboard and
 * <p>
 * this method copies PLAIN_TEXT and FILES DataFormats, because other data formats such as RTF and HTML compare
 * based on hashes rather than content making false-positive comparissons all but certain.
 * <p>
 * This class currently operates on an executable list model rather than Observer/Observable with inversion of control.
 * Future implementations may give the user a Property which changes when clipboard has changed
 */
public class ClipboardListener {

    /**
     * System clipboard to which the listener listens
     */
    private final Clipboard clipboard = Clipboard.getSystemClipboard();

    /**
     * Storage for old clipboard content to determine whether a change in content has been made
     */
    private final ClipboardContent oldContent = new ClipboardContent();

    @Deprecated
    /**
     * Methods which the listener should execute on a change of clipboard content
     */
    private List<Runnable> executeOnChange = new ArrayList<>();

    /**
     * Animation timer responsible for periodically checking for changes to the clipboard
     */
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
                if (isContentChanged()) executeAllAndUpdateHistory();
            }
        }
    };

    /**
     * Method to start the ClipboardListener listening for changes
     */
    public void start() {
        timer.start();
    }

    /**
     * Method to stop the ClipboardListener listening for changes
     */
    public void stop() {
        timer.stop();
    }

    /**
     * Utility method to retrieve the system clipboard
     *
     * @return the system clipboard
     */
    public Clipboard getClipboard() {
        return clipboard;
    }

    /**
     * Method to execute all registered methods and then copy the current clipboard content into the oldContent history
     */
    private void executeAllAndUpdateHistory() {
        for (Runnable r : executeOnChange) {
            r.run();
        }
        copyContent(clipboard, oldContent);
    }

    /**
     * Method to determine whether the current clipboard content is different from the stored historical clipboard
     * content
     * currently defaults to comparing files if files are present, or plain text as text because comparing other
     * individual DataFormats such as RTF compare the bitwise hashes, which are not equal, even in cases of identical
     * content
     *
     * @return whether the clipboard has changed since the clipboard was last recorded
     */
    private boolean isContentChanged() {
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

    /**
     * Method to copy the content of the clipboard content into a record for later determining whether changes to the
     * clipboard have been made
     * <p>
     * Again, in line with isContentChanged(), this method copies PLAIN_TEXT and FILES DataFormats, because other data
     * formats such as RTF and HTML compare based on hashes rather than content making false-positive comparissons
     * all but certain.
     *
     * @param clipboard  the current system clipboard
     * @param oldContent the record of the system clipboard content kept by this class
     */
    private void copyContent(Clipboard clipboard, ClipboardContent oldContent) {
        oldContent.put(DataFormat.PLAIN_TEXT, clipboard.getContent(DataFormat.PLAIN_TEXT));
        oldContent.put(DataFormat.FILES, clipboard.getContent(DataFormat.FILES));
    }

    @Deprecated
    /**
     * Method to register a runnable method which this class should execute when the clipboard is changed
     *
     * Currently operates with inversion of control, so this class is responsible for responsibly running this method
     * when changes are made. Possible future updates may be for this class to give a Property to the caller that changes
     * when an update is made so that the calling function can determine what to do.
     * @param runnable the runnable to be executed when a change to the clipboard is made
     */
    public void registerChangeRunnable(Runnable runnable) {
        executeOnChange.add(runnable);
    }

    @Deprecated
    /**
     * A method to remove a method from the execution list
     * @param runnable the method to be removed
     */
    public void removeChangeRunnable(Runnable runnable) {
        executeOnChange.remove(runnable);
    }

    @Deprecated
    /**
     * A method to clear all methods that are to be executed when the clipboard changes
     */
    public void clearChangeRunnables() {
        executeOnChange = new ArrayList<>();
    }

}
