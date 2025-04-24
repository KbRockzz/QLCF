package Interface.ThongKe;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThongKe extends JPanel {
    private JTable tblDoanhThu;
    private DefaultTableModel model;
    private JLabel lblTongDon, lblTongTien;
    private JTextField txtTuNgay, txtDenNgay;
    private Object[][] originalData; // Lưu trữ dữ liệu gốc để lọc

    public ThongKe() {
        setLayout(new BorderLayout());

        // Panel bộ lọc thống kê
        JPanel pnlFilter = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlFilter.setBorder(BorderFactory.createTitledBorder("Lọc thống kê"));

        // Giảm độ rộng của panel bên trái
        pnlFilter.setPreferredSize(new Dimension(200, 100));
        pnlFilter.setMaximumSize(new Dimension(200, Integer.MAX_VALUE));

        pnlFilter.add(new JLabel("Từ ngày:"));
        txtTuNgay = new JTextField("01/01/2025", 10);
        pnlFilter.add(txtTuNgay);

        pnlFilter.add(new JLabel("Đến ngày:"));
        txtDenNgay = new JTextField("31/12/2025", 10);
        pnlFilter.add(txtDenNgay);

        JButton btnThongKe = new JButton("Thống kê");
        pnlFilter.add(btnThongKe);

        add(pnlFilter, BorderLayout.WEST);

        // Bảng thống kê
        String[] cols = {"Mã đơn", "Ngày bán", "Tổng tiền", "NV phụ trách"};
        model = new DefaultTableModel(cols, 0);
        tblDoanhThu = new JTable(model);

        JScrollPane scr = new JScrollPane(tblDoanhThu);
        add(scr, BorderLayout.CENTER);

        // Panel bottom: tổng hợp doanh thu
        JPanel pnlSummary = new JPanel(new GridLayout(1, 2));
        lblTongDon = new JLabel("Tổng số đơn: 0", JLabel.LEFT);
        lblTongTien = new JLabel("Tổng doanh thu: 0 đ", JLabel.RIGHT);

        lblTongDon.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblTongTien.setFont(new Font("Tahoma", Font.BOLD, 13));

        pnlSummary.add(lblTongDon);
        pnlSummary.add(lblTongTien);
        add(pnlSummary, BorderLayout.SOUTH);

        // Dữ liệu mẫu
        originalData = new Object[][]{
                {"HD01", "05/04/2025", "150000 đ", "Nhân viên A"},
                {"HD02", "06/04/2025", "23000000 đ", "Nhân viên B"},
                {"HD03", "07/04/2025", "125000 đ", "Nhân viên C"}
        };

        // Thêm dữ liệu mẫu vào bảng
        for (Object[] row : originalData) {
            model.addRow(row);
        }

        capNhatTong();

        // Sự kiện cho nút Thống kê
        btnThongKe.addActionListener(e -> {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date tuNgay = sdf.parse(txtTuNgay.getText());
                Date denNgay = sdf.parse(txtDenNgay.getText());

                // Xóa dữ liệu hiện tại trong bảng
                model.setRowCount(0);

                // Lọc dữ liệu theo khoảng thời gian
                for (Object[] row : originalData) {
                    Date ngayBan = sdf.parse((String) row[1]); // Cột "Ngày bán"
                    if (ngayBan.compareTo(tuNgay) >= 0 && ngayBan.compareTo(denNgay) <= 0) {
                        model.addRow(row);
                    }
                }

                // Cập nhật tổng
                capNhatTong();

                // Kiểm tra nếu không có dữ liệu nào trong khoảng thời gian
                if (model.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(this, "Không có dữ liệu trong khoảng thời gian đã chọn!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ! Vui lòng nhập theo định dạng dd/MM/yyyy.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void capNhatTong() {
        int soDon = model.getRowCount();
        int tongTien = 0;

        for (int i = 0; i < soDon; i++) {
            String tien = model.getValueAt(i, 2).toString().replace(" đ", "").replace(".", "");
            tongTien += Integer.parseInt(tien);
        }

        lblTongDon.setText("Tổng số đơn: " + soDon);
        lblTongTien.setText("Tổng doanh thu: " + tongTien + " đ");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Thống Kê Doanh Thu");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 600);
            frame.add(new ThongKe());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}