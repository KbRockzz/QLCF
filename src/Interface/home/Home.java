package Interface.home;

import Interface.QLNV.QLNV;

import javax.swing.*;
import java.awt.*;

public class Home extends JPanel {

    public class BackgroundPanel extends JPanel {
        private final Image backgroundImage;

        public BackgroundPanel(ImageIcon icon) {
            this.backgroundImage = icon.getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public Home(/*username*/) {
        setLayout(null);

        // Load ảnh nền
        ImageIcon bgIcon = new ImageIcon(getClass().getResource("/Image/coffee_background.jpg"));
        BackgroundPanel backgroundPanel = new BackgroundPanel(bgIcon);
        backgroundPanel.setLayout(null);
        backgroundPanel.setBounds(0, 0, 900, 600);
        add(backgroundPanel);

    }
}
