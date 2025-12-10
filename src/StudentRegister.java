import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class StudentRegister extends JFrame {

    private JTextField nameField, emailField, phoneField, collegeField, courseField;
    private JPasswordField passwordField;

    public StudentRegister() {
        setTitle("Student Register");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        panel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        panel.add(phoneField);

        panel.add(new JLabel("College:"));
        collegeField = new JTextField();
        panel.add(collegeField);

        panel.add(new JLabel("Course:"));
        courseField = new JTextField();
        panel.add(courseField);

        JButton registerButton = UIUtils.primaryButton("Register");
        panel.add(new JLabel());
        panel.add(registerButton);

        add(panel);

        registerButton.addActionListener(this::registerStudent);

        setVisible(true);
    }

    private void registerStudent(ActionEvent e) {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String pass = new String(passwordField.getPassword()).trim();
        String phone = phoneField.getText().trim();
        String college = collegeField.getText().trim();
        String course = courseField.getText().trim();

        if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name, email and password are required.");
            return;
        }
        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(this, "Enter a valid email.");
            return;
        }
        if (pass.length() < 4) {
            JOptionPane.showMessageDialog(this, "Password must be at least 4 characters.");
            return;
        }
        if (!phone.isEmpty() && !phone.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Phone must contain only digits.");
            return;
        }

        try (Connection con = DatabaseConnection.getConnection()) {
            if (con == null) {
                JOptionPane.showMessageDialog(this, "Database connection failed.");
                return;
            }
            String sql = "INSERT INTO students (name, email, password, phone, college, course) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, pass);
            ps.setString(4, phone);
            ps.setString(5, college);
            ps.setString(6, course);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Student registered!");
            dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
