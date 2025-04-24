package Interface.QLThucDon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;

public class QLThucDon extends JPanel {
    private JTable tblThucDon;
    private DefaultTableModel model;

    public QLThucDon() {
        setSize(900, 600);
        setLayout(new BorderLayout());

        // Panel trái chứa các nút
        JPanel pnlLeft = new JPanel();
        pnlLeft.setLayout(new BoxLayout(pnlLeft, BoxLayout.Y_AXIS));
        pnlLeft.setPreferredSize(new Dimension(180, 0));

        JButton btnThem = new JButton("Thêm Món");
        JButton btnSua = new JButton("Sửa Món");
        JButton btnXoa = new JButton("Xóa Món");

        btnThem.setMaximumSize(new Dimension(160, 40));
        btnSua.setMaximumSize(new Dimension(160, 40));
        btnXoa.setMaximumSize(new Dimension(160, 40));

        pnlLeft.add(Box.createVerticalStrut(20));
        pnlLeft.add(btnThem);
        pnlLeft.add(Box.createVerticalStrut(20));
        pnlLeft.add(btnSua);
        pnlLeft.add(Box.createVerticalStrut(20));
        pnlLeft.add(btnXoa);

        add(pnlLeft, BorderLayout.WEST);

        // Panel trung tâm
        JPanel pnlCenter = new JPanel(new BorderLayout());

        // Thanh tìm kiếm
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlSearch.add(new JLabel("Loại món:"));
        JComboBox<String> cbLoai = new JComboBox<>(new String[]{"Tất cả", "Đồ uống", "Món ăn", "Khác"});
        pnlSearch.add(cbLoai);

        pnlSearch.add(new JLabel("Tìm món:"));
        JTextField txtSearch = new JTextField(20);
        pnlSearch.add(txtSearch);

        pnlCenter.add(pnlSearch, BorderLayout.NORTH);

        // Bảng thực đơn
        String[] columns = {"Mã", "Tên món", "Loại", "Giá", "Trạng thái"};
        model = new DefaultTableModel(columns, 0);
        tblThucDon = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tblThucDon);
        pnlCenter.add(scrollPane, BorderLayout.CENTER);

        // Label tổng số
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTongMon = new JLabel("Tổng số món: 0");
        pnlBottom.add(lblTongMon);
        pnlCenter.add(pnlBottom, BorderLayout.SOUTH);

        add(pnlCenter, BorderLayout.CENTER);

        // Dữ liệu mẫu để test UI
        themDongDemo("TD01", "Cafe sữa", "Đồ uống", 25000, "Còn bán");
        themDongDemo("TD02", "Bánh mì trứng", "Món ăn", 30000, "Còn bán");
        themDongDemo("TD03", "Trà đào", "Đồ uống", 28000, "Hết hàng");

        lblTongMon.setText("Tổng số món: " + model.getRowCount());

        // Thêm JComboBox vào cột "Trạng thái"
        TableColumn statusColumn = tblThucDon.getColumnModel().getColumn(4);
        JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Còn hàng", "Hết hàng"});
        statusColumn.setCellEditor(new DefaultCellEditor(statusComboBox));
    }

    private void themDongDemo(String ma, String ten, String loai, int gia, String trangThai) {
        model.addRow(new Object[]{ma, ten, loai, gia + " đ", trangThai});
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Quản Lý Thực Đơn");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.add(new QLThucDon());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
