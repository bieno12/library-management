
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Date;

import javax.swing.*;
class TextBannerApplication extends JFrame implements Runnable
{
    JLabel label;
    Thread backgroundThread;
    public TextBannerApplication(String title)
    {
        super(title);
        label = new JLabel(new Date().toString());

        backgroundThread = new Thread(this);
        backgroundThread.start();
        label.setBounds(0, 100, label.getPreferredSize().width, label.getPreferredSize().height);
        add(label, BorderLayout.CENTER);
        
    }

    public static void main(String[] args) {
        TextBannerApplication app = new TextBannerApplication("Date And Time Application");

        app.setBounds(0, 0, 400, 400);

        app.setBackground(new Color(30, 30, 30));
        app.setVisible(true);

    }

    @Override
    public void run() {
        while (true) {
            label.setText(new Date().toString());
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
