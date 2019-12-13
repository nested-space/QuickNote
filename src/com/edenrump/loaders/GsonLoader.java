package com.edenrump.loaders;

public abstract class GsonLoader {

    /**
     * Default error message when an issue is encountered loading a file
     */
    protected final static String FILE_LOAD_ERROR = "Error Loading File";

    abstract Object loadFromFile(String filename);
}
