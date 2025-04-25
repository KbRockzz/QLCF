package Interface.QLBan;

import Mysql.ConnectSql;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class QLBan extends JPanel {
    private final JPanel pnlLeft, pnlCenter, pnlRight;
    private JLabel lblBanInfo, lblStatus, lblTongTien;
    private JButton btnGoiMon, btnDatCho, btnThanhToan;
    private DefaultTableModel tableModel;
    private JTable tableMon;
    private Ban banDangChon;
    private Map<String, List<Mon>> danhMucMon;

    public QLBan() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);

        pnlLeft = new JPanel(new BorderLayout(5, 5));
        pnlCenter = new JPanel(new BorderLayout(5, 5));
        pnlRight = new JPanel(new BorderLayout(5, 5));
        pnlLeft.setPreferredSize(new Dimension(220, 0));
        pnlRight.setPreferredSize(new Dimension(220, 0));

        taoPanelBan();
        taoPanelThongTin();
        taoPanelMenu();

        add(pnlLeft, BorderLayout.WEST);
        add(pnlCenter, BorderLayout.CENTER);
        add(pnlRight, BorderLayout.EAST);
    }

    private void taoPanelBan() {
        JPanel pnlBan = new JPanel(new GridLayout(5, 2, 10, 10));
        Random rand = new Random();

        for (int i = 1; i <= 10; i++) {
            String[] trangThai = {"Trống", "Có khách", "Đặt trước"};
            String status = trangThai[rand.nextInt(3)];
            Ban ban = new Ban(i, "Bàn " + i, status);
            JButton btn = new JButton(ban.getTenBan());
            btn.setBackground(getMauBangTrangThai(ban.getTrangThai()));
            btn.setOpaque(true);
            btn.setContentAreaFilled(true);
            btn.setBorderPainted(false);
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 12));
            btn.setPreferredSize(new Dimension(100, 40));
            btn.addActionListener(e -> chonBan(ban));
            pnlBan.add(btn);
        }

        JPanel legend = new JPanel(new GridLayout(3, 1));
        legend.add(new JLabel("\uD83D\uDFE6 Trống"));
        legend.add(new JLabel("\uD83D\uDFE9 Có khách"));
        legend.add(new JLabel("\uD83E\uDE77 Đặt trước"));

        pnlLeft.add(pnlBan, BorderLayout.CENTER);
        pnlLeft.add(legend, BorderLayout.SOUTH);
    }

    private void taoPanelThongTin() {
        JPanel pnlTop = new JPanel(new GridLayout(4, 1, 5, 5));
        lblBanInfo = new JLabel("Chọn bàn để xem thông tin", SwingConstants.CENTER);
        lblBanInfo.setFont(new Font("Arial", Font.BOLD, 16));
        lblStatus = new JLabel("", SwingConstants.CENTER);

        btnGoiMon = new JButton("Gọi món");
        btnDatCho = new JButton("Đặt chỗ");

        pnlTop.add(lblBanInfo);
        pnlTop.add(lblStatus);
        pnlTop.add(btnGoiMon);
        pnlTop.add(btnDatCho);

        String[] columnNames = {"Tên món", "Số lượng", "Giá"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableMon = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(tableMon);
        scrollPane.setPreferredSize(new Dimension(400, 180));

        lblTongTien = new JLabel("Tổng: 0 VND", SwingConstants.CENTER);
        lblTongTien.setForeground(Color.RED);
        lblTongTien.setFont(new Font("Arial", Font.BOLD, 14));

        btnThanhToan = new JButton("Thanh toán");
        btnThanhToan.setPreferredSize(new Dimension(150, 30));
        btnThanhToan.setVisible(false);

        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        pnlBottom.add(lblTongTien);
        pnlBottom.add(btnThanhToan);

        pnlCenter.add(pnlTop, BorderLayout.NORTH);
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        pnlCenter.add(pnlBottom, BorderLayout.SOUTH);

        btnGoiMon.addActionListener(this::goiMon);
        btnDatCho.addActionListener(e -> {
            if (banDangChon != null) {
                banDangChon.setTrangThai("Đặt trước");
                lblStatus.setText("Status: Đặt trước");
            }
        });

        btnThanhToan.addActionListener(e -> {
            if (banDangChon != null) {
                List<HoaDon.ChiTietMon> dsMon = new ArrayList<>();
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String ten = tableModel.getValueAt(i, 0).toString();
                    int sl = (int) tableModel.getValueAt(i, 1);
                    int gia = (int) tableModel.getValueAt(i, 2);
                    dsMon.add(new HoaDon.ChiTietMon(ten, sl, gia));
                }

                int tong = dsMon.stream().mapToInt(HoaDon.ChiTietMon::thanhTien).sum();
                HoaDon hoaDon = new HoaDon(banDangChon, dsMon, tong);

                hienThiHoaDon(hoaDon);
                xuatFileHoaDon(hoaDon);
                luuHoaDonVaoSQL(hoaDon);

                tableModel.setRowCount(0);
                lblTongTien.setText("Tổng: 0 VND");
                banDangChon.setTrangThai("Trống");
                lblStatus.setText("Status: Trống");
                btnThanhToan.setVisible(false);
            }
        });
    }

    private void xuatFileHoaDon(HoaDon hoaDon) {
        try {
            String filename = "HoaDon_Ban" + hoaDon.getBan().getMaBan() + "_" +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

            writer.write("===== HÓA ĐƠN =====\n");
            writer.write("Bàn: " + hoaDon.getBan().getTenBan() + "\n\n");
            writer.write(String.format("%-20s %-10s %-10s %-10s\n", "Tên món", "SL", "Giá", "Thành tiền"));

            for (HoaDon.ChiTietMon ct : hoaDon.getDsMon()) {
                writer.write(String.format("%-20s %-10d %-10d %-10d\n",
                        ct.getTenMon(), ct.getSoLuong(), ct.getGia(), ct.thanhTien()));
            }

            writer.write("\nTỔNG TIỀN: " + hoaDon.getTongTien() + " VND\n");
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void luuHoaDonVaoSQL(HoaDon hoaDon) {
        try (Connection conn = ConnectSql.getConnection()) {
            String insertHD = "INSERT INTO HoaDon (MaBan, NgayTao, TongTien) VALUES (?, GETDATE(), ?)";
            PreparedStatement psHD = conn.prepareStatement(insertHD, Statement.RETURN_GENERATED_KEYS);
            psHD.setInt(1, hoaDon.getBan().getMaBan());
            psHD.setInt(2, hoaDon.getTongTien());
            psHD.executeUpdate();

            ResultSet rs = psHD.getGeneratedKeys();
            int maHoaDon = -1;
            if (rs.next()) {
                maHoaDon = rs.getInt(1);
            }

            String insertCT = "INSERT INTO ChiTietHoaDon (MaHoaDon, TenMon, SoLuong, Gia, ThanhTien) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement psCT = conn.prepareStatement(insertCT);
            for (HoaDon.ChiTietMon ct : hoaDon.getDsMon()) {
                psCT.setInt(1, maHoaDon);
                psCT.setString(2, ct.getTenMon());
                psCT.setInt(3, ct.getSoLuong());
                psCT.setInt(4, ct.getGia());
                psCT.setInt(5, ct.thanhTien());
                psCT.addBatch();
            }
            psCT.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void hienThiHoaDon(HoaDon hoaDon) {
        StringBuilder sb = new StringBuilder();
        sb.append("===== HÓA ĐƠN =====\n");
        sb.append("Bàn: ").append(hoaDon.getBan().getTenBan()).append("\n\n");
        sb.append(String.format("%-20s %-10s %-10s %-10s\n", "Tên món", "SL", "Giá", "Thành tiền"));

        for (HoaDon.ChiTietMon ct : hoaDon.getDsMon()) {
            sb.append(String.format("%-20s %-10d %-10d %-10d\n",
                    ct.getTenMon(), ct.getSoLuong(), ct.getGia(), ct.thanhTien()));
        }

        sb.append("\nTỔNG TIỀN: ").append(hoaDon.getTongTien()).append(" VND");

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Chi tiết hóa đơn", JOptionPane.INFORMATION_MESSAGE);
    }

    private void taoPanelMenu() {
        danhMucMon = new HashMap<>();
        danhMucMon.put("Cà phê", Arrays.asList(new Mon("Cafe Sữa", 50000), new Mon("Bạc xỉu", 25000), new Mon("Đen đá", 25000)));
        danhMucMon.put("Nước - Lon", Arrays.asList(new Mon("Pepsi", 20000), new Mon("7Up", 20000)));
        danhMucMon.put("Lipton - Trà", Arrays.asList(new Mon("Lipton đá", 20000), new Mon("Trà đào", 30000)));
        danhMucMon.put("Sinh tố", Arrays.asList(new Mon("Sinh tố bơ", 30000), new Mon("Sinh tố xoài", 30000)));
        danhMucMon.put("Đồ ăn nhanh", Arrays.asList(new Mon("Khoai tây chiên", 40000), new Mon("Gà rán", 50000)));

        JPanel pnlLoai = new JPanel(new GridLayout(0, 1, 5, 5));
        for (String loai : danhMucMon.keySet()) {
            JButton btn = new JButton(loai);
            btn.setBackground(new Color(255, 204, 153));
            btn.setFont(new Font("Arial", Font.PLAIN, 14));
            btn.addActionListener(e -> hienThiMonTheoLoai(loai));
            pnlLoai.add(btn);
        }

        pnlRight.add(pnlLoai, BorderLayout.NORTH);
    }

    private void hienThiMonTheoLoai(String loai) {
        BorderLayout layout = (BorderLayout) pnlRight.getLayout();
        Component centerComp = layout.getLayoutComponent(BorderLayout.CENTER);
        if (centerComp != null) pnlRight.remove(centerComp);

        JPanel pnlMon = new JPanel(new GridLayout(0, 1, 5, 5));
        pnlMon.setBackground(new Color(245, 245, 245));

        for (Mon mon : danhMucMon.get(loai)) {
            JButton btn = new JButton(mon.getTenMon() + " - " + mon.getGia() + " VND");
            btn.setBackground(new Color(230, 230, 255));
            btn.setFont(new Font("Arial", Font.PLAIN, 14));
            btn.addActionListener(e -> themMon(mon));
            pnlMon.add(btn);
        }

        JScrollPane scrollPane = new JScrollPane(pnlMon);
        pnlRight.add(scrollPane, BorderLayout.CENTER);
        pnlRight.revalidate();
        pnlRight.repaint();
    }

    private void themMon(Mon mon) {
        boolean found = false;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).equals(mon.getTenMon())) {
                int soLuong = (int) tableModel.getValueAt(i, 1);
                tableModel.setValueAt(soLuong + 1, i, 1);
                found = true;
                break;
            }
        }
        if (!found) {
            tableModel.addRow(new Object[]{mon.getTenMon(), 1, mon.getGia()});
        }
        capNhatTongTien();
        btnThanhToan.setVisible(true);
    }

    private void capNhatTongTien() {
        int tong = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            int sl = (int) tableModel.getValueAt(i, 1);
            int gia = (int) tableModel.getValueAt(i, 2);
            tong += sl * gia;
        }
        lblTongTien.setText("Tổng: " + tong + " VND");
    }

    private void chonBan(Ban ban) {
        this.banDangChon = ban;
        lblBanInfo.setText("Bàn: " + ban.getTenBan());
        lblStatus.setText("Status: " + ban.getTrangThai());
        tableModel.setRowCount(0);
        lblTongTien.setText("Tổng: 0 VND");
        btnThanhToan.setVisible(false);
    }

    private void goiMon(ActionEvent e) {
        if (banDangChon != null) {
            banDangChon.setTrangThai("Có khách");
            lblStatus.setText("Status: Có khách");
        }
    }

    private Color getMauBangTrangThai(String status) {
        return switch (status) {
            case "Trống" -> new Color(102, 153, 255);
            case "Có khách" -> new Color(144, 238, 144);
            case "Đặt trước" -> new Color(255, 105, 180);
            default -> Color.GRAY;
        };
    }

    static class Mon {
        private final String tenMon;
        private final int gia;

        public Mon(String tenMon, int gia) {
            this.tenMon = tenMon;
            this.gia = gia;
        }

        public String getTenMon() { return tenMon; }
        public int getGia() { return gia; }
    }
}
