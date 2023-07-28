import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BoxPhysicsV1 extends JFrame implements KeyListener {

    private JPanel panel;
    private int boxYPosition;

    public BoxPhysicsV1() {
        setTitle("BoxPhysicsV1");
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
        g.fillRect(getWidth() / 2 - 25, boxYPosition, 50, 50);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) {
            jump();
        }
    }

    private void jump() {
        if (boxYPosition >= getHeight() / 2 - 100) {
            new Thread(() -> {
                for (int i = 0; i < 50; i++) {
                    boxYPosition--;
                    panel.repaint();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                for (int i = 0; i < 50; i++) {
                    boxYPosition++;
                    panel.repaint();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
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
        SwingUtilities.invokeLater(() -> new BoxPhysicsV1());
    }
}
