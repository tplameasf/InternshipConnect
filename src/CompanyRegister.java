import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class CompanyRegister extends JFrame {

    private JTextField nameField, emailField, phoneField, addressField, websiteField;
    private JPasswordField passwordField;

    public CompanyRegister() {
        setTitle("Company Registration");
        setSize(420, 420);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Title
        JLabel title = new JLabel("Register Company", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        panel.add(title, BorderLayout.NORTH);

        // Form
        JPanel form = new JPanel(new GridLayout(7, 2, 5, 8));

        form.add(new JLabel("Company Name:"));
        nameField = new JTextField();
        form.add(nameField);

        form.add(new JLabel("Email:"));
        emailField = new JTextField();
        form.add(emailField);

        form.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        form.add(passwordField);

        form.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        form.add(phoneField);

        form.add(new JLabel("Address:"));
        addressField = new JTextField();
        form.add(addressField);

        form.add(new JLabel("Website:"));
        websiteField = new JTextField();
        form.add(websiteField);

        panel.add(form, BorderLayout.CENTER);

        // Button
        JButton registerButton = new JButton("Register");
        registerButton.setBackground(new Color(30, 90, 160));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.addActionListener(this::registerCompany);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(registerButton);
        panel.add(bottom, BorderLayout.SOUTH);

        setContentPane(panel);
        setVisible(true);
    }

    private void registerCompany(ActionEvent e) {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String pass = new String(passwordField.getPassword()).trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();
        String website = websiteField.getText().trim();

        // Basic validation
        if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Company name, email and password are required.");
            return;
        }
        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.");
            return;
        }
        if (pass.length() < 4) {
            JOptionPane.showMessageDialog(this, "Password should be at least 4 characters.");
            return;
        }
        if (!phone.isEmpty() && !phone.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Phone must contain only digits.");
            return;
        }

        // Insert into database
        try (Connection con = DatabaseConnection.getConnection()) {
            if (con == null) {
                JOptionPane.showMessageDialog(this, "Database connection failed.");
                return;
            }

            String sql = "INSERT INTO companies (company_name, email, password, phone, address, website) " +
                         "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, pass);
            ps.setString(4, phone);
            ps.setString(5, address);
            ps.setString(6, website);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Company registered successfully!");
            dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
