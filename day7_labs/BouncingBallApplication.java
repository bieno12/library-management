
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Date;

import javax.swing.*;

class BouncingBallApplication extends JFrame implements Runnable {
    JLabel label;
    Thread backgroundThread;

    double dx, dy;
    final double speed = 4;

    public BouncingBallApplication(String title) {
        super(title);
        label = new JLabel();

        Image image = new ImageIcon("ball.png").getImage().getScaledInstance(128, 128, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(image));
        setLayout(null);

        backgroundThread = new Thread(this);
        backgroundThread.start();
        label.setBounds(0, 100, label.getPreferredSize().width, label.getPreferredSize().height);
        add(label);
        System.out.println(label.getSize());
        // dx = Math.random();
        // dy = Math.random();
        dx = 0.5;
        dy = 0.5;

    }

    @Override
    public void run() {
        while (true) {
            Rectangle bounds = label.getBounds();
            bounds.x = bounds.x + (int) (speed * dx);
            bounds.y = bounds.y + (int) (speed * dy);
            if (bounds.x + bounds.width > getWidth())
                dx = Math.abs(dx) * -1;
            if (bounds.x < 0)
                dx = Math.abs(dx);
            System.out.println(getHeight());
            if (bounds.y + bounds.height + 32 > getHeight())
                dy = Math.abs(dy) * -1;

            if (bounds.y < 0)
                dy = Math.abs(dy) ;

            label.setBounds(bounds);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        BouncingBallApplication app = new BouncingBallApplication("Date And Time Application");

        app.setBounds(0, 0, 400, 400);

        app.setBackground(new Color(30, 30, 30));
        app.setVisible(true);

    }
}
