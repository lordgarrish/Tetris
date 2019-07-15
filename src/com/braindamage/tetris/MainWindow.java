package com.braindamage.tetris;

import javax.swing.*;
import java.awt.*;

//Game window (frame)
public class MainWindow extends JFrame {

    public MainWindow() {
        EventQueue.invokeLater(() -> {
            JMenuBar menuBar = new JMenuBar();
            JMenu fileMenu = new JMenu("Menu");
            JMenuItem aboutMenuItem = new JMenuItem("About");
            aboutMenuItem.addActionListener(actionEvent -> JOptionPane.showMessageDialog(this,
                    "Tetris by lord_garrish, (c) 2019"));
            fileMenu.add(aboutMenuItem);
            menuBar.add(fileMenu);
            setJMenuBar(menuBar);
            setTitle("Tetris");
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(315,500);
            setLocation(400,400);
            add(new GameField());
            setVisible(true);
        });
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}