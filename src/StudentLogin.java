import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentLogin extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    public StudentLogin() {
        setTitle("Student Login");
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

        loginButton.addActionListener(this::loginStudent);

        setVisible(true);
    }

    private void loginStudent(ActionEvent e) {
        String email = emailField.getText();
        String pass = new String(passwordField.getPassword());

        try (Connection con = DatabaseConnection.getConnection()) {
            String sql = "SELECT student_id, name FROM students WHERE email = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int studentId = rs.getInt("student_id");
                String name = rs.getString("name");
                JOptionPane.showMessageDialog(this, "Welcome " + name);
                new StudentDashboard(studentId, name);
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
