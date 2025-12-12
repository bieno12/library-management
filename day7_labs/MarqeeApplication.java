
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Date;

import javax.swing.*;
class MarqeeApplication extends JFrame implements Runnable
{
    JLabel label;
    Thread backgroundThread;

    public MarqeeApplication(String title)
    {
        super(title);
        label = new JLabel();

        Image image = new ImageIcon("ball.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        label.setText("Hello Java");
        // label.setSize(64, 64);
        setLayout(null);

        backgroundThread = new Thread(this);
        backgroundThread.start();
        label.setBounds(0, 100, label.getPreferredSize().width, label.getPreferredSize().height);
        add(label);
        
    }

    @Override
    public void run() {
        while (true) {
            Rectangle bounds = label.getBounds();
            bounds.x = bounds.x + 1;
            if(bounds.x - bounds.width > getWidth())
                bounds.x = -bounds.width;
            label.setBounds(bounds);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MarqeeApplication app = new MarqeeApplication("Date And Time Application");

        app.setBounds(0, 0, 400, 400);

        app.setBackground(new Color(30, 30, 30));
        app.setVisible(true);

    }
}
