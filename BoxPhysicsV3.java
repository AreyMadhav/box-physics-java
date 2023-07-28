import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BoxPhysicsV3 extends JFrame implements KeyListener {

    private JPanel panel;
    private int boxYPosition;
    private int boxXPosition;
    private boolean isJumping;

    public BoxPhysicsV3() {
        setTitle("BoxPhysics");
        setSize(800, 600);
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

        boxYPosition = getHeight() / 2 - 50;
        boxXPosition = getWidth() / 2 - 25;
        isJumping = false;

        add(panel);
        setVisible(true);
    }

    private void drawBackground(Graphics g) {
        g.setColor(new Color(135, 206, 235)); // Light blue color for sky
        g.fillRect(0, 0, getWidth(), getHeight() / 2);

        g.setColor(new Color(34, 139, 34)); // Green color for floor
        g.fillRect(0, getHeight() / 2, getWidth(), getHeight() / 2);
    }

    private void drawBox(Graphics g) {
        g.setColor(Color.RED); // Red color for the box
        g.fillRect(boxXPosition, boxYPosition, 50, 50);
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

    private void moveLeft() {
        if (boxXPosition > 0) {
            boxXPosition -= 10; // Adjust the movement speed to your preference
            panel.repaint();
        }
    }

    private void moveRight() {
        if (boxXPosition < getWidth() - 50) {
            boxXPosition += 10; // Adjust the movement speed to your preference
            panel.repaint();
        }
    }

    private void jump() {
        if (!isJumping) {
            isJumping = true;

            new Thread(() -> {
                final int jumpDuration = 500; // Duration of the jump in milliseconds
                final long startTime = System.currentTimeMillis();

                for (long currentTime = startTime; currentTime - startTime <= jumpDuration; currentTime = System.currentTimeMillis()) {
                    float t = (currentTime - startTime) / (float) jumpDuration;
                    float easeInValue = t * t; // Ease-in quadratic function

                    // Calculate the new Y position with the ease-in value
                    boxYPosition = getHeight() / 2 - 100 - (int) (easeInValue * 100);

                    panel.repaint();
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
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BoxPhysicsV3::new);
    }
}
