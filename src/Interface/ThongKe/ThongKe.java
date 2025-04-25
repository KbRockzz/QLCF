package Interface.ThongKe;

import Mysql.ConnectSql;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThongKe extends JPanel {
    private JTable tblDoanhThu;
    private DefaultTableModel model;
    private JLabel lblTongDon, lblTongTien;
    private JTextField txtTuNgay, txtDenNgay;
    private JButton btnThongKe;

    public ThongKe() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Panel lọc bên trái
        JPanel pnlFilter = new JPanel();
        pnlFilter.setLayout(new BoxLayout(pnlFilter, BoxLayout.Y_AXIS));
        pnlFilter.setBorder(BorderFactory.createTitledBorder("📅 Lọc theo ngày"));
        pnlFilter.setPreferredSize(new Dimension(220, 150));

        txtTuNgay = new JTextField(10);
        txtDenNgay = new JTextField(10);
        txtTuNgay.setMaximumSize(new Dimension(180, 25));
        txtDenNgay.setMaximumSize(new Dimension(180, 25));
        txtTuNgay.setText("01/01/2025");
        txtDenNgay.setText("31/12/2025");

        btnThongKe = new JButton("🔍 Thống kê");

        pnlFilter.add(Box.createVerticalStrut(10));
        pnlFilter.add(new JLabel("Từ ngày (dd/MM/yyyy):"));
        pnlFilter.add(txtTuNgay);
        pnlFilter.add(Box.createVerticalStrut(15));
        pnlFilter.add(new JLabel("Đến ngày (dd/MM/yyyy):"));
        pnlFilter.add(txtDenNgay);
        pnlFilter.add(Box.createVerticalStrut(20));
        pnlFilter.add(btnThongKe);
        pnlFilter.add(Box.createVerticalGlue());

        add(pnlFilter, BorderLayout.WEST);

        // Bảng thống kê ở giữa
        String[] cols = {"Mã hóa đơn", "Mã bàn", "Ngày", "Giờ", "Tổng tiền"};
        model = new DefaultTableModel(cols, 0);
        tblDoanhThu = new JTable(model);
        JScrollPane scr = new JScrollPane(tblDoanhThu);
        add(scr, BorderLayout.CENTER);

        // Panel tổng kết phía dưới
        JPanel pnlSummary = new JPanel(new GridLayout(1, 2));
        lblTongDon = new JLabel("Tổng số đơn: 0", JLabel.LEFT);
        lblTongTien = new JLabel("Tổng doanh thu: 0 đ", JLabel.RIGHT);
        lblTongDon.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblTongTien.setFont(new Font("Tahoma", Font.BOLD, 13));
        pnlSummary.add(lblTongDon);
        pnlSummary.add(lblTongTien);
        add(pnlSummary, BorderLayout.SOUTH);

        // Sự kiện nút thống kê
        btnThongKe.addActionListener(e -> xuLyThongKe());

        // Tải dữ liệu mặc định
        xuLyThongKe();
    }

    private void xuLyThongKe() {
        if (txtTuNgay.getText().trim().isEmpty() || txtDenNgay.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ ngày!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);

            Date tuNgay = sdf.parse(txtTuNgay.getText().trim());
            Date denNgay = sdf.parse(txtDenNgay.getText().trim());

            if (tuNgay.after(denNgay)) {
                JOptionPane.showMessageDialog(this, "Từ ngày không được sau đến ngày!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            taiDuLieu(tuNgay, denNgay);

        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Ngày không đúng định dạng dd/MM/yyyy!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void taiDuLieu(Date tuNgay, Date denNgay) {
        SimpleDateFormat sdfSQL = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfNgay = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfGio = new SimpleDateFormat("HH:mm:ss");

        String query = "SELECT MaHoaDon, MaBan, NgayTao, TongTien FROM HoaDon WHERE CAST(NgayTao AS DATE) BETWEEN ? AND ?";

        try (Connection conn = ConnectSql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, sdfSQL.format(tuNgay));
            stmt.setString(2, sdfSQL.format(denNgay));

            ResultSet rs = stmt.executeQuery();
            model.setRowCount(0);  // Xóa dữ liệu cũ

            int tongTien = 0, soDon = 0;
            while (rs.next()) {
                String maHD = rs.getString("MaHoaDon");
                String maBan = rs.getString("MaBan");
                Timestamp ngayTao = rs.getTimestamp("NgayTao");
                int tien = rs.getInt("TongTien");

                model.addRow(new Object[]{
                        maHD,
                        maBan,
                        sdfNgay.format(ngayTao),
                        sdfGio.format(ngayTao),
                        tien + " đ"
                });

                tongTien += tien;
                soDon++;
            }

            lblTongDon.setText("Tổng số đơn: " + soDon);
            lblTongTien.setText("Tổng doanh thu: " + tongTien + " đ");

            if (soDon == 0) {
                JOptionPane.showMessageDialog(this, "Không có dữ liệu trong khoảng thời gian đã chọn!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("📊 Thống kê doanh thu");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(1000, 600);
            f.add(new ThongKe());
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}
