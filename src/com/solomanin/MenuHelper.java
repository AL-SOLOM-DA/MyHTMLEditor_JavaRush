package com.solomanin;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MenuHelper {
    public static JMenuItem addMenuItem(JMenu parent, String text, ActionListener actionListener){
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(actionListener);
        parent.add(menuItem);
        return menuItem;
    }

    public static JMenuItem addMenuItem(JMenu parent, Action action){
        JMenuItem menuItem = new JMenuItem(action);
        parent.add(menuItem);
        return menuItem;
    }

    public static JMenuItem addMenuItem(JMenu parent, String text, Action action){
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(action);
        parent.add(menuItem);
        return menuItem;
    }

    void intHelpMenu(View view, JMenuBar menuBar){}
    void initFontMenu(View view, JMenuBar menuBar){}
    void initColorMenu(View view, JMenuBar menuBar){}
    void initAlignMenu(View view, JMenuBar menuBar){}
    void initStyleMenu(View view, JMenuBar menuBar){}
    void initEditMenu(View view, JMenuBar menuBar){}
    void initFileMenu(View view, JMenuBar menuBar){}



}
