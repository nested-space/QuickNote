package com.edenrump.ui.data;

import java.util.Arrays;

public class CommandHistory {

    private int caretDepth = 0;
    private int numberOfCommands = 0;
    private String[] commandHistory;

    public int put(String newCommand) {
        resetCaret();

        if (newCommand.equals(getLatest())) return numberOfCommands;
        System.arraycopy(
                commandHistory, 0,
                commandHistory, 1,
                commandHistory.length - 1);
        commandHistory[0] = newCommand;
        numberOfCommands = Math.min(numberOfCommands + 1, commandHistory.length);
        return numberOfCommands;
    }

    public CommandHistory(int maxNumber) {
        commandHistory = new String[maxNumber];
    }

    public String getCommand(int depth) {
        int index = cycleIndex(depth - 1, numberOfCommands);
        if (depth < 1) return " ";
        return commandHistory[index];
    }

    public int cycleIndex(int index, int max) {
        if (index > max) {
            return cycleIndex(index - max, max);
        } else {
            return index;
        }
    }

    public String getLatest() {
        return getCommand(1);
    }

    public int rewind() {
        if (caretDepth == 0) return 0;
        numberOfCommands -= (caretDepth);
        System.arraycopy(
                commandHistory, caretDepth,
                commandHistory, 0,
                commandHistory.length - caretDepth);

        for (int i = (numberOfCommands); i < commandHistory.length; i++) {
            commandHistory[i] = null;
        }
        caretDepth = 0;
        return numberOfCommands;
    }

    public String[] getAll() {
        return commandHistory;
    }

    public String increaseCaret() {
        if (numberOfCommands == 0) return "";
        caretDepth++;
        System.out.println("+Depth: " + caretDepth + " -> " + Arrays.toString(getHistory()));
        if (caretDepth > numberOfCommands) caretDepth = 1;
        return getCommand(caretDepth);
    }

    public String decreaseCaret() {
        if (numberOfCommands == 0) return "";
        caretDepth--;
        System.out.println("-Depth: " + caretDepth + " -> " + Arrays.toString(getHistory()));
        if (caretDepth < 0) caretDepth = 0;
        return getCommand(caretDepth);
    }

    public String[] getHistory() {
        String[] history = new String[numberOfCommands];
        System.arraycopy(
                commandHistory, 0,
                history, 0,
                numberOfCommands);
        return history;
    }

    public void clear() {
        Arrays.fill(commandHistory, null);
        caretDepth = 0;
        numberOfCommands = 0;
    }

    public void resetCaret() {
        caretDepth = 0;
    }
}
