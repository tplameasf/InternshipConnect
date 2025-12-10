import javax.swing.*;
import java.awt.*;

public class InternshipConnect extends JFrame {

    public InternshipConnect() {
        setTitle("Internship Connect");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("INTERNSHIP CONNECT", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(title, BorderLayout.NORTH);

        JPanel buttons = new JPanel(new GridLayout(2, 2, 10, 10));
        JButton studentLogin = new JButton("Student Login");
        JButton studentRegister = new JButton("Student Register");
        JButton companyLogin = new JButton("Company Login");
        JButton companyRegister = new JButton("Company Register");

        buttons.add(studentLogin);
        buttons.add(studentRegister);
        buttons.add(companyLogin);
        buttons.add(companyRegister);

        panel.add(buttons, BorderLayout.CENTER);
        add(panel);

        studentLogin.addActionListener(e -> new StudentLogin());
        studentRegister.addActionListener(e -> new StudentRegister());
        companyLogin.addActionListener(e -> new CompanyLogin());
        companyRegister.addActionListener(e -> new CompanyRegister());

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InternshipConnect::new);
    }
}
