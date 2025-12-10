import javax.swing.*;
import java.awt.*;

public class InternshipConnect extends JFrame {

    public InternshipConnect() {
        setTitle("Internship Connect Portal");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Main split: left info panel + right actions panel
        JPanel main = new JPanel(new BorderLayout());

        // ===== LEFT SIDE =====
        JPanel left = new JPanel();
        left.setBackground(new Color(6, 22, 64)); // dark blue
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 40));

        JLabel welcome1 = new JLabel("Welcome to the");
        JLabel welcome2 = new JLabel("Internship Connect");
        JLabel welcome3 = new JLabel("Registry!");

        welcome1.setFont(new Font("SansSerif", Font.BOLD, 32));
        welcome2.setFont(new Font("SansSerif", Font.BOLD, 32));
        welcome3.setFont(new Font("SansSerif", Font.BOLD, 32));

        Color white = Color.WHITE;
        welcome1.setForeground(white);
        welcome2.setForeground(white);
        welcome3.setForeground(white);

        JLabel line1 = new JLabel("Connect with Opportunities.");
        JLabel line2 = new JLabel("Find Your Perfect Internship.");
        JLabel line3 = new JLabel("Make a Difference.");
        JLabel line4 = new JLabel("Explore students, companies, and internships");
        JLabel line5 = new JLabel("using the options on the right.");

        Font small = new Font("SansSerif", Font.PLAIN, 16);
        for (JLabel l : new JLabel[]{line1, line2, line3, line4, line5}) {
            l.setFont(small);
            l.setForeground(new Color(220, 220, 220));
        }

        left.add(welcome1);
        left.add(Box.createVerticalStrut(5));
        left.add(welcome2);
        left.add(Box.createVerticalStrut(5));
        left.add(welcome3);
        left.add(Box.createVerticalStrut(30));
        left.add(line1);
        left.add(Box.createVerticalStrut(5));
        left.add(line2);
        left.add(Box.createVerticalStrut(5));
        left.add(line3);
        left.add(Box.createVerticalStrut(25));
        left.add(line4);
        left.add(Box.createVerticalStrut(5));
        left.add(line5);

        // ===== RIGHT SIDE =====
        JPanel rightOuter = new JPanel(new GridBagLayout());
        rightOuter.setBackground(new Color(236, 242, 248));

        JPanel rightCard = new JPanel();
        rightCard.setBackground(Color.WHITE);
        rightCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220)),
                BorderFactory.createEmptyBorder(25, 40, 25, 40)
        ));
        rightCard.setLayout(new BoxLayout(rightCard, BoxLayout.Y_AXIS));

        JLabel getStarted = new JLabel("Get Started");
        getStarted.setAlignmentX(Component.CENTER_ALIGNMENT);
        getStarted.setFont(new Font("SansSerif", Font.BOLD, 24));
        getStarted.setForeground(new Color(6, 22, 64));

        rightCard.add(getStarted);
        rightCard.add(Box.createVerticalStrut(25));

        JButton studentLogin = createOutlinedButton("Student Login", new Color(30, 90, 160));
        JButton studentRegister = createOutlinedButton("Student Register", new Color(0, 128, 0));
        JButton companyLogin = createOutlinedButton("Company Login", new Color(204, 120, 0));
        JButton companyRegister = createOutlinedButton("Company Register", new Color(170, 0, 60));

        rightCard.add(studentLogin);
        rightCard.add(Box.createVerticalStrut(15));
        rightCard.add(studentRegister);
        rightCard.add(Box.createVerticalStrut(15));
        rightCard.add(companyLogin);
        rightCard.add(Box.createVerticalStrut(15));
        rightCard.add(companyRegister);

        rightOuter.add(rightCard, new GridBagConstraints());

        main.add(left, BorderLayout.CENTER);
        main.add(rightOuter, BorderLayout.EAST);

        // Button actions (same as before)
        studentLogin.addActionListener(e -> new StudentLogin());
        studentRegister.addActionListener(e -> new StudentRegister());
        companyLogin.addActionListener(e -> new CompanyLogin());
        companyRegister.addActionListener(e -> new CompanyRegister());

        setContentPane(main);
        setVisible(true);
    }

    private JButton createOutlinedButton(String text, Color borderColor) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setBackground(Color.WHITE);
        btn.setForeground(Color.BLACK);
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn.setPreferredSize(new Dimension(260, 55));
        btn.setMaximumSize(new Dimension(260, 55));
        btn.setBorder(BorderFactory.createLineBorder(borderColor, 3));
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InternshipConnect::new);
    }
}

