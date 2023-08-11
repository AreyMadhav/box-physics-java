package com.mycompany.boxphysics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BoxPhysicsV3 extends JFrame implements KeyListener {

    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private static final int BOX_WIDTH = 50;
    private static final int BOX_HEIGHT = 50;
    private static final int FLOOR_HEIGHT = WINDOW_HEIGHT / 2;
    private static final int JUMP_DURATION = 300;
    private static final int MOVE_AMOUNT = 10;

    private JPanel panel;
    private int boxYPosition;
    private int boxXPosition;
    private boolean isJumping;

    public BoxPhysicsV3() {
        setTitle("BoxPhysics");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBackground(g);
                drawBox(g);
            }
        };

        panel.setFocusable(true);
        panel.addKeyListener(this);

        boxYPosition = FLOOR_HEIGHT - BOX_HEIGHT;
        boxXPosition = (WINDOW_WIDTH - BOX_WIDTH) / 2;
        isJumping = false;

        add(panel);
        setVisible(true);
    }

    private void drawBackground(Graphics g) {
        g.setColor(new Color(135, 206, 235)); // Light blue color for sky
        g.fillRect(0, 0, WINDOW_WIDTH, FLOOR_HEIGHT);

        g.setColor(new Color(34, 139, 34)); // Green color for floor
        g.fillRect(0, FLOOR_HEIGHT, WINDOW_WIDTH, FLOOR_HEIGHT);
    }

    private void drawBox(Graphics g) {
        g.setColor(Color.RED); // Red color for the box
        g.fillRect(boxXPosition, boxYPosition, BOX_WIDTH, BOX_HEIGHT);
    }

    private void moveLeft() {
        if (boxXPosition > 0) {
            boxXPosition -= MOVE_AMOUNT;
            panel.repaint();
        }
    }

    private void moveRight() {
        if (boxXPosition < WINDOW_WIDTH - BOX_WIDTH) {
            boxXPosition += MOVE_AMOUNT;
            panel.repaint();
        }
    }

    private void jump() {
        if (!isJumping) {
            isJumping = true;

            new Thread(() -> {
                long startTime = System.currentTimeMillis();

                while (System.currentTimeMillis() - startTime <= JUMP_DURATION) {
                    float t = (System.currentTimeMillis() - startTime) / (float) JUMP_DURATION;
                    float easeInValue = t * t; // Ease-in quadratic function

                    boxYPosition = FLOOR_HEIGHT - BOX_HEIGHT - (int) (easeInValue * 100);
                    repaint();

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }

                isJumping = false;
            }).start();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) {
            jump();
        } else if (key == KeyEvent.VK_A) {
            moveLeft();
        } else if (key == KeyEvent.VK_D) {
            moveRight();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BoxPhysicsV3::new);
    }
}
