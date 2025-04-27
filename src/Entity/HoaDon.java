package Entity;

import java.util.List;

public class HoaDon {
    private Ban ban;
    private List<ChiTietMon> dsMon;
    private int tongTien;

    public HoaDon(Ban ban, List<ChiTietMon> dsMon, int tongTien) {
        this.ban = ban;
        this.dsMon = dsMon;
        this.tongTien = tongTien;
    }

    public Ban getBan() {
        return ban;
    }

    public List<ChiTietMon> getDsMon() {
        return dsMon;
    }

    public int getTongTien() {
        return tongTien;
    }

    public static class ChiTietMon {
        private String tenMon;
        private int soLuong;
        private int gia;

        public ChiTietMon(String tenMon, int soLuong, int gia) {
            this.tenMon = tenMon;
            this.soLuong = soLuong;
            this.gia = gia;
        }

        public String getTenMon() {
            return tenMon;
        }

        public int getSoLuong() {
            return soLuong;
        }

        public int getGia() {
            return gia;
        }

        public int thanhTien() {
            return soLuong * gia;
        }
    }
}
