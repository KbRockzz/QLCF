/**
 * @name: NhanVien
 * @author: Nguyen Dao Hoai Thuan
 * @version: 1.0
 * @created: 4/24/2025
 */

package Models;

public class NhanVien {
    public enum ChucVu {
        NHANVIEN("NhanVien"),
        QUANLY("QuanLy");

        private final String tenHienThi;

        ChucVu(String s) {
            this.tenHienThi = s;
        }

        @Override
        public String toString() {
            return tenHienThi;
        }

        public static ChucVu fromString(String text) {
            for (ChucVu cv : ChucVu.values()) {
                if (cv.tenHienThi.equalsIgnoreCase(text)) {
                    return cv;
                }
            }
            throw new IllegalArgumentException("Không tìm thấy chức vụ tương ứng: " + text);
        }
    };

    private int maNV;
    private String username = "";
    private String password = "";
    private ChucVu chucVu = ChucVu.NHANVIEN;

    public NhanVien() {
    }

    public NhanVien(int maNV, String username, String password, ChucVu chucVu) {
        this.maNV = maNV;
        this.username = username;
        this.password = password;
        this.chucVu = chucVu;
    }

    public ChucVu getChucVu() {
        return chucVu;
    }

    public void setChucVu(ChucVu chucVu) {
        this.chucVu = chucVu;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    @Override
    public String toString() {
        return "NhanVien{" +
                "maNV=" + maNV +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", chucVu=" + chucVu +
                '}';
    }
}
