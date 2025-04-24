package Interface;

import Interface.QLBan.QLBan;
import Interface.QLNV.QLNV;
import Interface.ThongKe.ThongKe;
import Interface.home.Home;
import Models.NhanVien;

import javax.swing.*;
import java.awt.*;

public class frmMain extends JFrame {
    private CardLayout cardLayout;
    private JPanel containerPanel;
    private NhanVien currentUser;

    public frmMain(NhanVien currentUser) {
        this.currentUser = currentUser;

        setTitle("Quản lý hệ thống");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        containerPanel = new JPanel(cardLayout);

        // Thêm các JPanel vào container
        containerPanel.add(new Home(/*username*/), "Home");
        containerPanel.add(new QLNV(), "QLNV");
        containerPanel.add(new QLBan(), "QLBan");
        containerPanel.add(new ThongKe(), "ThongKe");

        // Thêm container vào JFrame
        add(containerPanel);

        // Tạo thanh điều hướng
        JPanel navbar = createNavbar(currentUser);
        add(navbar, BorderLayout.NORTH);

        // Hiển thị màn hình chính (Home) đầu tiên
        cardLayout.show(containerPanel, "Home");
    }

    // Tạo thanh Navbar
    private JPanel createNavbar(NhanVien currentUser) {
        JPanel navbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        navbar.setPreferredSize(new Dimension(900, 60));
        navbar.setBackground(new Color(230, 230, 230));

        Dimension btnSize = new Dimension(110, 40);

        JButton btnTrangChu = new JButton("TRANG CHỦ");
        btnTrangChu.setPreferredSize(btnSize);
        btnTrangChu.addActionListener(e -> showHome());
        navbar.add(btnTrangChu);

        JButton btnBanHang = new JButton("BÁN HÀNG");
        btnBanHang.setPreferredSize(btnSize);
        btnBanHang.addActionListener(e -> showQLBan(currentUser.getUsername()));
        navbar.add(btnBanHang);

        JButton btnQuanLy = new JButton("QUẢN LÝ");
        btnQuanLy.setPreferredSize(btnSize);
        btnQuanLy.addActionListener(e -> showQLNV());
        navbar.add(btnQuanLy);

        JButton btnThongKe = new JButton("THỐNG KÊ");
        btnThongKe.setPreferredSize(btnSize);
        btnThongKe.addActionListener(e -> showThongKe());
        navbar.add(btnThongKe);

        JButton btnDangXuat = new JButton("ĐĂNG XUẤT");
        btnDangXuat.setPreferredSize(btnSize);
        navbar.add(btnDangXuat);

        JLabel lblUser = new JLabel("Nhân viên: " + currentUser.getUsername());
        lblUser.setFont(new Font("Tahoma", Font.BOLD, 12));
        navbar.add(lblUser);

        return navbar;
    }

    // Hiển thị Home
    public void showHome() {
        cardLayout.show(containerPanel, "Home");
    }

    // Hiển thị Quản Lý Nhân Viên
    public void showQLNV() {
        // Kiểm tra chức vụ của người dùng
        if ("QuanLy".equals(currentUser.getChucVu().toString())) {
            cardLayout.show(containerPanel, "QLNV");
        } else {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền truy cập vào Quản Lý Nhân Viên!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Hiển thị Quản Lý Bàn
    public void showQLBan(String username) {
        cardLayout.show(containerPanel, "QLBan");
    }

    public void showThongKe(){
        cardLayout.show(containerPanel, "ThongKe");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
//            new frmMain("username").setVisible(true);
        });
    }
}
