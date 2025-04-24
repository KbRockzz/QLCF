/**
 * @name: LogIn
 * @author: Nguyen Dao Hoai Thuan
 * @version: 1.0
 * @created: 4/22/2025
 */

package Interface;
import Interface.home.Home;
import Models.NhanVien;
import Models.NhanVien_DAO;

import javax.swing.*;
import java.awt.*;

public class LogIn extends JFrame {
    public LogIn() {
        setTitle("Đăng nhập");
        setSize(450, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);

        JLabel lblThangHoa = new JLabel("Cà phê");
        lblThangHoa.setFont(new Font("Segoe Script", Font.BOLD, 24));
        lblThangHoa.setBounds(50, 10, 200, 30);
        add(lblThangHoa);

        JLabel lblSlogan = new JLabel("Thăng Hoa");
        lblSlogan.setFont(new Font("Segoe Script", Font.ITALIC, 14));
        lblSlogan.setBounds(50, 35, 200, 30);
        add(lblSlogan);

        JLabel lblTitle = new JLabel("Đăng nhập");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitle.setBounds(200, 20, 200, 30);
        add(lblTitle);

        // Username
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblUsername.setBounds(60, 80, 100, 25);
        add(lblUsername);

        JTextField txtUsername = new JTextField();
        txtUsername.setBounds(150, 80, 200, 25);
        add(txtUsername);

        // Password
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblPassword.setBounds(60, 115, 100, 25);
        add(lblPassword);

        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 115, 200, 25);
        add(txtPassword);

        // Buttons
        JButton btnLogin = new JButton("Đăng nhập");
        btnLogin.setBounds(150, 160, 100, 30);
        add(btnLogin);

        JButton btnExit = new JButton("Thoát");
        btnExit.setBounds(260, 160, 90, 30);
        add(btnExit);

        // Hành động nút Thoát
        btnExit.addActionListener(e -> System.exit(0));

        // Hành động nút Đăng nhập -> mở Home
        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword());

            if (username.isBlank() || password.isBlank()) {
                JOptionPane.showMessageDialog(this, "Tên đăng nhập hoặc mật khẩu không được để trống!", "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
            }

            NhanVien_DAO dao = new NhanVien_DAO();
            boolean isValid = false;
            NhanVien loggedNV = null;

            for (NhanVien nv : dao.getAlltbNhanVien()) {
                if (nv.getUsername().equals(username) && nv.getPassword().equals(password)) {
                    isValid = true;
                    loggedNV = nv;
                    break;
                }
            }

            if (isValid) {
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
                new frmMain(loggedNV).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Sai tên đăng nhập hoặc mật khẩu!", "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        setVisible(true);
    }
}

