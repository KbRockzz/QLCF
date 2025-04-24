package Interface.QLThucDon;

public class Mon {
    private String maMon;
    private String tenMon;
    private String loaiMon;
    private int donGia;
    private boolean trangThai;

    public Mon(){
        this.maMon="";
        this.tenMon="";
        this.loaiMon="";
        this.donGia=0;
        this.trangThai=false;
    }
    public Mon(String mamon, String ten, String maloai, int gia, boolean dvt){
        this.maMon=mamon;
        this.tenMon=ten;
        this.loaiMon=maloai;
        this.donGia=gia;
        this.trangThai=dvt;
    }

    public String getMaMon() {
        return maMon;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public String getLoaiMon() {
        return loaiMon;
    }

    public void setLoaiMon(String loaiMon) {
        this.loaiMon = loaiMon;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
}