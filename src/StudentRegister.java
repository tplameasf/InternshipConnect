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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));

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

        JButton registerButton = new JButton("Register");
        panel.add(registerButton);

        add(panel);

        registerButton.addActionListener(this::registerStudent);

        setVisible(true);
    }

    private void registerStudent(ActionEvent e) {
        String name = nameField.getText();
        String email = emailField.getText();
        String pass = new String(passwordField.getPassword());
        String phone = phoneField.getText();
        String college = collegeField.getText();
        String course = courseField.getText();

        if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name, email, and password are required.");
            return;
        }

        try (Connection con = DatabaseConnection.getConnection()) {
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
