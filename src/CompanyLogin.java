import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CompanyLogin extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    public CompanyLogin() {
        setTitle("Company Login");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        panel.add(loginButton);

        add(panel);

        loginButton.addActionListener(this::loginCompany);

        setVisible(true);
    }

    private void loginCompany(ActionEvent e) {
        String email = emailField.getText();
        String pass = new String(passwordField.getPassword());

        try (Connection con = DatabaseConnection.getConnection()) {
            String sql = "SELECT company_id, company_name FROM companies WHERE email = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int companyId = rs.getInt("company_id");
                String name = rs.getString("company_name");
                JOptionPane.showMessageDialog(this, "Welcome " + name);
                new CompanyDashboard(companyId, name);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid login.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
