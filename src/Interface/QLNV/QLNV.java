/**
 * @name: QLNV
 * @author: Nguyen Dao Hoai Thuan
 * @version: 1.0
 * @created: 4/22/2025
 */
package Interface.QLNV;

import Interface.QLBan.QLBan;
import Interface.home.Home;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class QLNV extends JPanel {

    public QLNV() {
        setLayout(new BorderLayout());

        // Navbar
//        JPanel navbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
//        navbar.setPreferredSize(new Dimension(900, 60));
//        navbar.setBackground(new Color(230, 230, 230));
//        add(navbar, BorderLayout.NORTH);
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

//        JLabel lblUser = new JLabel("Nhân viên: " + "username");
//        lblUser.setFont(new Font("Tahoma", Font.BOLD, 12));
//        navbar.add(lblUser);

        // Panel trái chứa các nút điều hướng và chức năng
        JPanel pnlLeft = new JPanel();
        pnlLeft.setLayout(new BoxLayout(pnlLeft, BoxLayout.Y_AXIS));
        pnlLeft.setPreferredSize(new Dimension(180, 0));

        // Các nút điều hướng trang quản lý
        JButton btnQLNV = new JButton("Quản Lý Nhân Viên");
        btnQLNV.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnQLNV.setMaximumSize(new Dimension(160, 40));
        pnlLeft.add(Box.createVerticalStrut(15));
        pnlLeft.add(btnQLNV);

        JButton btnQLThucDon = new JButton("Quản Lý Thực Đơn");
        btnQLThucDon.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnQLThucDon.setMaximumSize(new Dimension(160, 40));
        pnlLeft.add(Box.createVerticalStrut(15));
        pnlLeft.add(btnQLThucDon);

        JButton btnQLBan = new JButton("Quản Lý Bàn");
        btnQLBan.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnQLBan.setMaximumSize(new Dimension(160, 40));
        pnlLeft.add(Box.createVerticalStrut(15));
        pnlLeft.add(btnQLBan);

        // Các nút thao tác
        JButton btnThem = new JButton("Thêm Nhân Viên");
        btnThem.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnThem.setMaximumSize(new Dimension(160, 40));
        pnlLeft.add(Box.createVerticalStrut(15));
        pnlLeft.add(btnThem);

        JButton btnSua = new JButton("Sửa Thông Tin");
        btnSua.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSua.setMaximumSize(new Dimension(160, 40));
        pnlLeft.add(Box.createVerticalStrut(15));
        pnlLeft.add(btnSua);

        JButton btnXoa = new JButton("Xóa Nhân Viên");
        btnXoa.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnXoa.setMaximumSize(new Dimension(160, 40));
        pnlLeft.add(Box.createVerticalStrut(15));
        pnlLeft.add(btnXoa);

        add(pnlLeft, BorderLayout.WEST);

        // Panel trung tâm
        JPanel pnlCenter = new JPanel(new BorderLayout());

        // Panel tìm kiếm
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlTop.add(new JLabel("Chức vụ:"));
        JComboBox<String> cbChucVu = new JComboBox<>(new String[]{"Nhân viên", "Quản lý"});
        pnlTop.add(cbChucVu);

        pnlTop.add(new JLabel("Tìm nhân viên:"));
        JTextField txtTim = new JTextField(20);
        pnlTop.add(txtTim);

        pnlCenter.add(pnlTop, BorderLayout.NORTH);

        // Bảng danh sách nhân viên
        String[] col = {"Mã NV", "Username", "Password", "Chức vụ"};
        DefaultTableModel model = new DefaultTableModel(col, 0);
        JTable tblNhanVien = new JTable(model);
        JScrollPane scr = new JScrollPane(tblNhanVien);
        pnlCenter.add(scr, BorderLayout.CENTER);

        // Label tổng số
        JPanel pnlBottom = new JPanel(new BorderLayout());
        JLabel lblTong = new JLabel("Tổng số nhân viên: 0", JLabel.LEFT);
        lblTong.setFont(new Font("Tahoma", Font.BOLD, 12));
        pnlBottom.add(lblTong, BorderLayout.WEST);

        pnlCenter.add(pnlBottom, BorderLayout.SOUTH);
        add(pnlCenter, BorderLayout.CENTER);

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Quản lý nhân viên");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.add(new QLNV());
        frame.setVisible(true);
    }
}
