package com.company.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Time;
import java.util.Random;

public class gamePanel extends JPanel implements ActionListener {

    static final int screenWidth = 600;
    static final int screenHeight = 600;
    static final int unitSize = 25;
    static final int gameUnits = (screenWidth * screenHeight) / unitSize;
    static final int delay = 100;
    final int[] x = new int[gameUnits];
    final int[] y = new int[gameUnits];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    gamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new myKeyAdapter());
        startGame();
    }
    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(delay,this);
        timer.start();
    }
    public void paintComponent(Graphics g ) {
        super.paintComponent(g);
        draw(g);
    }
    public void move () {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U':
                y[0] = y[0] - unitSize;
                break;
            case 'D':
                y[0] = y[0] + unitSize;
                break;
            case 'L':
                x[0] = x[0] - unitSize;
                break;
            case 'R':
                x[0] = x[0] + unitSize;
                break;
        }
    }
    public void checkApple() {
        if (x[0] == appleX & y[0] == appleY) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }
    public void draw(Graphics g ) {
        if (running) {

//                for (int i = 0; i < screenHeight / unitSize; i++) {
//                    g.drawLine(i * unitSize, 0, i * unitSize, screenHeight);
//                    g.drawLine(0, i * unitSize, screenWidth, i * unitSize);
//                }
                g.setColor(Color.blue);
                g.fillOval(appleX, appleY, unitSize, unitSize);

                for (int i = 0; i < bodyParts; i++) {
                    if (i == 0) {
                        g.setColor(Color.green);
                        g.fillRect(x[i], y[i], unitSize, unitSize);
                    } else {
                        g.setColor(new Color(45, 180, 0));
                        g.setColor(new Color(random.nextInt(255),random.nextInt(252),random.nextInt(200)));
                        g.fillRect(x[i], y[i], unitSize, unitSize);
                    }
                }
                // Score Text
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free" , Font.BOLD ,40));
            FontMetrics fontMetrics = getFontMetrics(g.getFont());
            g.drawString("Score😊: " + applesEaten , (screenWidth - fontMetrics.stringWidth("Score😊: " + applesEaten))/2, g.getFont().getSize());

        } else {

            gameOver(g);
        }
    }
    public void newApple() {
        appleX = random.nextInt((int)(screenWidth / unitSize)) * unitSize;
        appleY = random.nextInt((int)(screenHeight / unitSize)) * unitSize;

    }
    public void checkCollisions() {
        // check if head collides with body.
        for (int i = bodyParts; i > 0; i--) {
            if (x[0] == x[i] & y[0] == y[i]) {
                running = false;
            }
        }
        // check if head touched left border and touched right border ,touched top border ,and bottom border.
        if (x[0] < 0 | x[0] > screenWidth | y[0] < 0 | y[0] > screenHeight) {
            running = false;
        }
        if (!running) {
            timer.stop();
        }

    }
    public void gameOver(Graphics g) {
        // Score Text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free" , Font.BOLD ,40));
        FontMetrics fontMetrics1 = getFontMetrics(g.getFont());
        g.drawString("Score😊: " + applesEaten , (screenWidth - fontMetrics1.stringWidth("Score😊: " + applesEaten))/2, g.getFont().getSize());

        // game Over Text
        g.setColor(Color.RED);
        g.setFont(new Font("Serif" , Font.ITALIC ,75));
        FontMetrics fontMetrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over ☹" , (screenWidth - fontMetrics2.stringWidth("Game Over ☹"))/2, screenHeight / 2 );

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class myKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if (direction != 'R') {
                            direction = 'L';
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (direction != 'L') {
                            direction = 'R';
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if (direction != 'D') {
                            direction = 'U';
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (direction != 'U') {
                            direction = 'D';
                        }
                        break;
                }
        }
    }
}
