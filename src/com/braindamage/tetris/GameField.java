package com.braindamage.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

//Game field and logic
public class GameField extends JPanel implements ActionListener {
    private final int X = 15;
    private final int Y = 23;
    private final int DOT_SIZE = 20;
    private Image dot = new ImageIcon("dot.png").getImage();
    private Image wall = new ImageIcon("win.jpg").getImage();
    private final int[][] FIGURES = {{1,3,5,7},{2,4,5,7},{3,5,4,6},{3,5,4,7},{2,3,5,7},{3,5,7,6},{2,3,4,5}};
    private int[] x = new int[4];
    private int[] y = new int[4];
    private Timer timer;
    private int[][] field = new int[X][Y];
    private boolean isLeft = true;
    private boolean isRight = true;


    public GameField() {
        nextFigure();
        timer = new Timer(600, this);
        timer.start();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void nextFigure() {
        int figure = new Random().nextInt(7);
        for (int i = 0; i < 4; i++) {
            x[i] = 6 + (FIGURES[figure][i] % 2);
            y[i] = FIGURES[figure][i] / 2;
        }
    }

    public void move() {
        for (int i = 0; i < 4; i++) {
            y[i] += 1;
        }
    }

    public void checkBottom() {
        for (int i = 0; i < 4; i++) {
            if (y[i] == 21) {
                stopFigure();
                nextFigure();
            }
        }
    }

    public void checkFiguresOnBottom() {
        for (int i = 0; i < 4; i++) {
            if (field[x[i]][y[i]+1] > 0) {
                stopFigure();
                nextFigure();
            }
        }
    }

    public void checkBorders() {
        for (int i = 0; i < 4; i++) {
            if (x[i] == 0)
                isLeft = false;
            else if (x[i] >= X - 1)
                isRight = false;
            else {
                isLeft = true;
                isRight = true;
            }
        }
    }

    public void stopFigure() {
        for (int i = 0; i < 4; i++) {
            field[x[i]][y[i]] = y[i];
        }
    }

    public void checkLines() {
        int k = Y - 1;
        for (int i = Y - 1; i > 0; i--)
        {
            int count = 0;
            for (int j = 0; j < X; j++)
            {
                if (field[j][i] > 0) count++;
                field[j][k] = field[j][i];
            }
            if (count < X) k--;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(wall,0,0,this);
        //Moving figures
        for (int i = 0; i < 4; i++) {
            g.drawImage(dot, x[i] * DOT_SIZE, y[i] * DOT_SIZE, this);
        }
        //Static figures
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                if (field[i][j] == 0) continue;
                g.drawImage(dot, i * DOT_SIZE, j * DOT_SIZE, this);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        checkBorders();
        checkFiguresOnBottom();
        checkBottom();
        checkLines();
        move();
        repaint();
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_RIGHT && isRight) {
                for (int i = 0; i < 4; i++) {
                    x[i] += 1;
                }
            }
            if (key == KeyEvent.VK_LEFT && isLeft) {
                for (int i = 0; i < 4; i++) {
                    x[i] -= 1;
                }
            }
            if (key == KeyEvent.VK_DOWN) {
                for (int i = 0; i < 4; i++) {
                    y[i] += 2;
                }
            }
            if (key == KeyEvent.VK_UP) {
                for (int i = 0; i < 4; i++) {
                    int a = y[i] - y[1];
                    int b = x[i] - x[1];
                    x[i] = x[1] - a;
                    y[i] = y[1] + b;
                }
            }
        }
    }
}