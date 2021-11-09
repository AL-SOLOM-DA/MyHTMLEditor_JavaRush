package com.solomanin;

import com.solomanin.listeners.UndoListener;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.undo.UndoManager;
import java.io.*;

public class Controller {
    private View view;
    private HTMLDocument document;
    private File currentFile;

    public Controller(View view) {
        this.view = view;
    }

    public static void main(String[] args) {
        View view = new View();
        Controller controller = new Controller(view);
        view.setController(controller);
        view.init();
        controller.init();
    }

    public HTMLDocument getDocument() {
        return document;
    }

    public void init(){
        createNewDocument();
    }

    public void resetDocument(){
        UndoListener undoListener = view.getUndoListener();
        if (document!=null){
            document.removeUndoableEditListener(undoListener);
        }
        document = (HTMLDocument) new HTMLEditorKit().createDefaultDocument();
        document.addUndoableEditListener(undoListener);
        view.update();
    }

    public void setPlainText(String text){
        resetDocument();
        StringReader reader = new StringReader(text);
        try {
            new HTMLEditorKit().read(reader, document, 0);
        } catch (IOException | BadLocationException e) {
            ExceptionHandler.log(e);
        }
    }

    public String getPlainText(){
        StringWriter writer = new StringWriter();
        try {
            new HTMLEditorKit().write(writer, document, 0, document.getLength());
        } catch (IOException | BadLocationException e){
            ExceptionHandler.log(e);
        }
        return writer.toString();
    }

    public void exit(){
        System.exit(0);
    }



    public void createNewDocument(){
        view.selectHtmlTab();
        resetDocument();
        view.setTitle("HTML Editor");
        currentFile = null;
    }
    public void openDocument(){
        view.selectHtmlTab();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new HTMLFileFilter());
        if(fileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            resetDocument();
            view.setTitle(currentFile.getName());
            try(FileReader reader = new FileReader(currentFile)) {
                new HTMLEditorKit().read(reader, document, 0);
            } catch (IOException | BadLocationException e) {
                ExceptionHandler.log(e);
            }
        }
        view.resetUndo();
    }
    public void saveDocument(){
        if(currentFile==null) saveDocumentAs();
        view.selectHtmlTab();
        try(FileWriter writer = new FileWriter(currentFile)) {
            new HTMLEditorKit().write(writer, document, 0, document.getLength());
        } catch (IOException | BadLocationException e) {
            ExceptionHandler.log(e);
        }
    }
    public void saveDocumentAs(){
        view.selectHtmlTab();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new HTMLFileFilter());
        if(fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {


            currentFile = fileChooser.getSelectedFile();
            view.setTitle(currentFile.getName());
            try(FileWriter writer = new FileWriter(currentFile)) {
                new HTMLEditorKit().write(writer, document, 0, document.getLength());
            } catch (IOException | BadLocationException e) {
                ExceptionHandler.log(e);
            }
        }
    }
}
