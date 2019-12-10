package com.edenrump.controllers;

import com.edenrump.clipboard.ClipboardListener;
import javafx.animation.*;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    public TextArea contentType;
    public TextArea clipboardBox;
    public ListView<DataFormat> listView;

    private Clipboard clipboard = Clipboard.getSystemClipboard();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ClipboardListener clipboardListener = new ClipboardListener();
        clipboardListener.addUpdateMethod(new Runnable() {
            @Override
            public void run() {
                listView.getItems().setAll(clipboard.getContentTypes());
            }
        });
        clipboardListener.start();
    }

}
