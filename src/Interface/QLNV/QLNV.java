/**
 * @name: QLNV
 * @author: Nguyen Dao Hoai Thuan
 * @version: 1.0
 * @created: 4/22/2025
 */
package Interface.QLNV;

import Models.NhanVien;
import Models.NhanVien_DAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class QLNV extends JPanel {

    private JTable tblNhanVien;
    private JLabel lblTong;

    public QLNV() {
        setLayout(new BorderLayout());

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
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddEmployeeDialog();
            }
        });
        pnlLeft.add(Box.createVerticalStrut(15));
        pnlLeft.add(btnThem);

        JButton btnSua = new JButton("Sửa Thông Tin");
        btnSua.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSua.setMaximumSize(new Dimension(160, 40));
        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tblNhanVien.getSelectedRow();
                if (selectedRow != -1) {
                    int maNV = (int) tblNhanVien.getValueAt(selectedRow, 0);  // Lấy mã nhân viên từ bảng
                    showUpdateEmployeeDialog(maNV);
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên để sửa!");
                }
            }
        });
        pnlLeft.add(Box.createVerticalStrut(15));
        pnlLeft.add(btnSua);

        JButton btnXoa = new JButton("Xóa Nhân Viên");
        btnXoa.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnXoa.setMaximumSize(new Dimension(160, 40));
        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tblNhanVien.getSelectedRow();
                if (selectedRow != -1) {
                    int maNV = Integer.parseInt(tblNhanVien.getValueAt(selectedRow, 0).toString());
                    deleteEmployee(maNV);
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên để xóa!");
                }
            }
        });
        pnlLeft.add(Box.createVerticalStrut(15));
        pnlLeft.add(btnXoa);

        add(pnlLeft, BorderLayout.WEST);

        // Panel trung tâm
        JPanel pnlCenter = new JPanel(new BorderLayout());

        // Panel tìm kiếm
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlTop.add(new JLabel("Chức vụ:"));
        JComboBox<String> cbChucVu = new JComboBox<>(new String[]{"NhanVien", "QuanLy"});
        pnlTop.add(cbChucVu);

        pnlTop.add(new JLabel("Tìm nhân viên:"));
        JTextField txtTim = new JTextField(20);
        pnlTop.add(txtTim);

        pnlCenter.add(pnlTop, BorderLayout.NORTH);

        // Bảng danh sách nhân viên
        String[] col = {"Mã NV", "Username", "Password", "Chức vụ"};
        DefaultTableModel model = new DefaultTableModel(col, 0);
        tblNhanVien = new JTable(model);
        // Chỉnh sửa trực tiếp không được
        tblNhanVien.setDefaultEditor(Object.class, null);
        JScrollPane scr = new JScrollPane(tblNhanVien);
        pnlCenter.add(scr, BorderLayout.CENTER);

        // Label tổng số
        JPanel pnlBottom = new JPanel(new BorderLayout());
        lblTong = new JLabel("Tổng số nhân viên: 0", JLabel.LEFT);
        lblTong.setFont(new Font("Tahoma", Font.BOLD, 12));
        pnlBottom.add(lblTong, BorderLayout.WEST);

        pnlCenter.add(pnlBottom, BorderLayout.SOUTH);
        add(pnlCenter, BorderLayout.CENTER);

        // Load dữ liệu từ database
        Models.NhanVien_DAO dao = new Models.NhanVien_DAO();
        ArrayList<NhanVien> nhanVienList = dao.getAlltbNhanVien();
        updateTable(tblNhanVien, nhanVienList);
    }

    private void updateTable(JTable table, ArrayList<NhanVien> nhanVienList) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (NhanVien nv : nhanVienList) {
            Object[] row = {nv.getMaNV(), nv.getUsername(), nv.getPassword(), nv.getChucVu()};
            model.addRow(row);
        }

        lblTong.setText("Tổng số nhân viên: " + nhanVienList.size());
    }

    private void showAddEmployeeDialog() {
        // Mở cửa sổ nhập liệu thêm nhân viên
        JDialog addDialog = new JDialog();
        addDialog.setTitle("Thêm Nhân Viên");
        addDialog.setSize(400, 300);
        addDialog.setResizable(false);
        addDialog.setLocationRelativeTo(null); // Để cửa sổ xuất hiện ở giữa màn hình

        addDialog.setLayout(new GridLayout(5, 2, 10, 10));

        // Các trường nhập liệu
        JTextField txtMaNV = new JTextField(20);
        JTextField txtUsername = new JTextField(20);
        JTextField txtPassword = new JTextField(20);
        JComboBox<String> cbChucVu = new JComboBox<>(new String[]{"NhanVien", "QuanLy"});

        addDialog.add(new JLabel("Mã Nhân Viên:"));
        addDialog.add(txtMaNV);

        addDialog.add(new JLabel("Username:"));
        addDialog.add(txtUsername);

        addDialog.add(new JLabel("Password:"));
        addDialog.add(txtPassword);

        addDialog.add(new JLabel("Chức Vụ:"));
        addDialog.add(cbChucVu);

        // Nút thêm
        JButton btnAdd = new JButton("Thêm");
        addDialog.add(new JLabel());
        addDialog.add(btnAdd);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NhanVien nv = new NhanVien(
                        Integer.parseInt(txtMaNV.getText()),
                        txtUsername.getText(),
                        txtPassword.getText(),
                        NhanVien.ChucVu.fromString(cbChucVu.getSelectedItem().toString())
                );

                Models.NhanVien_DAO dao = new Models.NhanVien_DAO();
                if (dao.create(nv)) {
                    JOptionPane.showMessageDialog(addDialog, "Thêm nhân viên thành công!");
                    addDialog.dispose();
                    // Cập nhật lại bảng nhân viên
                    ArrayList<NhanVien> nhanVienList = dao.getAlltbNhanVien();
                    updateTable(tblNhanVien, nhanVienList);
                } else {
                    JOptionPane.showMessageDialog(addDialog, "Thêm nhân viên thất bại!");
                }
            }
        });
        addDialog.setVisible(true);
    }

    private void showUpdateEmployeeDialog(int maNV) {
        // Lấy thông tin nhân viên hiện tại từ bảng để sửa
        Models.NhanVien_DAO dao = new Models.NhanVien_DAO();
        NhanVien nv = dao.getNhanVienById(maNV);  // Phương thức này sẽ lấy nhân viên theo ID

        if (nv == null) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy nhân viên!");
            return;
        }

        // Mở cửa sổ nhập liệu sửa nhân viên
        JDialog updateDialog = new JDialog();
        updateDialog.setTitle("Sửa Nhân Viên");
        updateDialog.setSize(400, 300);
        updateDialog.setResizable(false);
        updateDialog.setLocationRelativeTo(null);

        updateDialog.setLayout(new GridLayout(5, 2, 10, 10));

        // Các trường nhập liệu
        JTextField txtUsername = new JTextField(nv.getUsername(), 20);
        JTextField txtPassword = new JTextField(nv.getPassword(), 20);
        JComboBox<String> cbChucVu = new JComboBox<>(new String[]{"NhanVien", "QuanLy"});
        cbChucVu.setSelectedItem(nv.getChucVu().toString());

        updateDialog.add(new JLabel("Username:"));
        updateDialog.add(txtUsername);

        updateDialog.add(new JLabel("Password:"));
        updateDialog.add(txtPassword);

        updateDialog.add(new JLabel("Chức Vụ:"));
        updateDialog.add(cbChucVu);

        // Nút sửa
        JButton btnUpdate = new JButton("Cập Nhật");
        updateDialog.add(new JLabel());
        updateDialog.add(btnUpdate);

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nv.setUsername(txtUsername.getText());
                nv.setPassword(txtPassword.getText());
                nv.setChucVu(NhanVien.ChucVu.fromString(cbChucVu.getSelectedItem().toString()));

                if (dao.update(nv)) {
                    JOptionPane.showMessageDialog(updateDialog, "Cập nhật nhân viên thành công!");
                    updateDialog.dispose();
                    // Cập nhật lại bảng nhân viên
                    ArrayList<NhanVien> nhanVienList = dao.getAlltbNhanVien();
                    updateTable(tblNhanVien, nhanVienList);
                } else {
                    JOptionPane.showMessageDialog(updateDialog, "Cập nhật nhân viên thất bại!");
                }
            }
        });
        updateDialog.setVisible(true);
    }

    private void deleteEmployee(int maNV) {
        int option = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa nhân viên này?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            Models.NhanVien_DAO dao = new Models.NhanVien_DAO();
            if (dao.delete(String.valueOf(maNV))) {
                JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!");
                // Cập nhật lại bảng nhân viên
                ArrayList<NhanVien> nhanVienList = dao.getAlltbNhanVien();
                updateTable(tblNhanVien, nhanVienList);
            } else {
                JOptionPane.showMessageDialog(this, "Xóa nhân viên thất bại!");
            }
        }
    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("Quản lý nhân viên");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.add(new QLNV());
        frame.setVisible(true);
    }
}
