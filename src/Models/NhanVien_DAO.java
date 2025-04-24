/**
 * @name: NhanVien_DAO
 * @author: Nguyen Dao Hoai Thuan
 * @version: 1.0
 * @created: 4/24/2025
 */

package Models;

import Mysql.ConnectSql;

import java.sql.*;
import java.util.*;

public class NhanVien_DAO {
    public ArrayList<NhanVien> getAlltbNhanVien() {
        ArrayList<NhanVien> dsNV = new ArrayList<>();
        try {
            ConnectSql.getInstance();
            Connection con = ConnectSql.getConnection();
            String sql = "SELECT * FROM NhanVien";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String maNV = rs.getString(1);
                String username = rs.getString(2);
                String password = rs.getString(3);
                String chucVu = rs.getString(4);
                NhanVien p = new NhanVien(Integer.parseInt(maNV), username, password, NhanVien.ChucVu.fromString(chucVu));
                dsNV.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsNV;
    }

    public boolean create(NhanVien nv) {
        ConnectSql.getInstance();
        Connection con = ConnectSql.getConnection();
        PreparedStatement stmt = null;
        int n = 0;
        try {
//            stmt = con.prepareStatement("INSERT INTO NhanVien (username, password, chucVu) VALUES (?, ?, ?)");
            stmt = con.prepareStatement("SET IDENTITY_INSERT NhanVien ON;" +
                    "INSERT INTO NhanVien (maNV, username, password, chucVu) " +
                    "VALUES (?, ?, ?, ?);" +
                    "SET IDENTITY_INSERT NhanVien OFF;"
            );

            stmt.setInt(1, nv.getMaNV());
            stmt.setString(2, nv.getUsername());
            stmt.setString(3, nv.getPassword());
            stmt.setString(4, nv.getChucVu().toString());
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public boolean update(NhanVien nv) {
        ConnectSql.getInstance();
        Connection con = ConnectSql.getConnection();
        PreparedStatement stmt = null;
        int n = 0;
        try {
            stmt = con.prepareStatement("UPDATE NhanVien SET Username = ?, Password = ?, ChucVu = ? WHERE maNV = ?");
            stmt.setString(1, nv.getUsername());
            stmt.setString(2, nv.getPassword());
            stmt.setString(3, nv.getChucVu().toString());
            stmt.setInt(4, nv.getMaNV());
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public boolean delete(String maNV) {
        Connection con = ConnectSql.getInstance().getConnection();
        PreparedStatement stmt = null;
        int n = 0;
        try {
            stmt = con.prepareStatement("DELETE FROM NhanVien WHERE maNV = ?");
            stmt.setString(1, maNV);
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public NhanVien getNhanVienById(int maNV) {
        NhanVien nv = null;
        try {
            ConnectSql.getInstance();
            Connection con = ConnectSql.getConnection();
            String sql = "SELECT * FROM NhanVien WHERE maNV = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, maNV);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String username = rs.getString(2);
                String password = rs.getString(3);
                String chucVu = rs.getString(4);
                nv = new NhanVien(maNV, username, password, NhanVien.ChucVu.fromString(chucVu));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nv;
    }

}
