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

        // Navbar dùng FlowLayout
//        JPanel navbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
//        navbar.setBounds(0, 0, 900, 60);
//        navbar.setBackground(new Color(230, 230, 230));
//        backgroundPanel.add(navbar);
//
//        Dimension btnSize = new Dimension(110, 40);
//
//        JButton btnTrangChu = new JButton("TRANG CHỦ");
//        btnTrangChu.setPreferredSize(btnSize);
//        navbar.add(btnTrangChu);
//
//        JButton btnBanHang = new JButton("BÁN HÀNG");
//        btnBanHang.setPreferredSize(btnSize);
//        navbar.add(btnBanHang);
//
//        JButton btnQuanLy = new JButton("QUẢN LÝ");
//        btnQuanLy.setPreferredSize(btnSize);
//        navbar.add(btnQuanLy);
//
//        JButton btnThongKe = new JButton("THỐNG KÊ");
//        btnThongKe.setPreferredSize(btnSize);
//        navbar.add(btnThongKe);
//
//        JButton btnDangXuat = new JButton("ĐĂNG XUẤT");
//        btnDangXuat.setPreferredSize(btnSize);
//        navbar.add(btnDangXuat);

//        JLabel lblUser = new JLabel("Nhân viên: " + username);
//        lblUser.setFont(new Font("Tahoma", Font.BOLD, 12));
//        navbar.add(lblUser);


    }
}
