import javax.swing.*;
import java.awt.*;

public class InternshipConnect extends JFrame {

    public InternshipConnect() {
        setTitle("Internship Connect Portal");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel main = new JPanel(new BorderLayout());

        // LEFT
        JPanel left = new JPanel();
        left.setBackground(new Color(6, 22, 64));
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 40));

        JLabel w1 = new JLabel("Welcome to the");
        JLabel w2 = new JLabel("Internship Connect");
        JLabel w3 = new JLabel("Registry!");
        Font big = new Font("SansSerif", Font.BOLD, 32);
        for (JLabel l : new JLabel[]{w1, w2, w3}) {
            l.setFont(big);
            l.setForeground(Color.WHITE);
        }

        JLabel l1 = new JLabel("Connect with Opportunities.");
        JLabel l2 = new JLabel("Find Your Perfect Internship.");
        JLabel l3 = new JLabel("Make a Difference.");
        JLabel l4 = new JLabel("Explore students, companies, and internships");
        JLabel l5 = new JLabel("using the options on the right.");
        Font small = new Font("SansSerif", Font.PLAIN, 16);
        Color light = new Color(220, 220, 220);
        for (JLabel l : new JLabel[]{l1, l2, l3, l4, l5}) {
            l.setFont(small);
            l.setForeground(light);
        }

        left.add(w1); left.add(Box.createVerticalStrut(5));
        left.add(w2); left.add(Box.createVerticalStrut(5));
        left.add(w3); left.add(Box.createVerticalStrut(30));
        left.add(l1); left.add(Box.createVerticalStrut(5));
        left.add(l2); left.add(Box.createVerticalStrut(5));
        left.add(l3); left.add(Box.createVerticalStrut(25));
        left.add(l4); left.add(Box.createVerticalStrut(5));
        left.add(l5);

        // RIGHT
        JPanel rightOuter = new JPanel(new GridBagLayout());
        rightOuter.setBackground(new Color(236, 242, 248));

        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220)),
                BorderFactory.createEmptyBorder(25, 40, 25, 40)));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel getStarted = new JLabel("Get Started");
        getStarted.setAlignmentX(Component.CENTER_ALIGNMENT);
        getStarted.setFont(new Font("SansSerif", Font.BOLD, 24));
        getStarted.setForeground(new Color(6, 22, 64));
        card.add(getStarted);
        card.add(Box.createVerticalStrut(25));

        JButton studentLogin = outlinedButton("Student Login", new Color(30, 90, 160));
        JButton studentRegister = outlinedButton("Student Register", new Color(0, 128, 0));
        JButton companyLogin = outlinedButton("Company Login", new Color(204, 120, 0));
        JButton companyRegister = outlinedButton("Company Register", new Color(170, 0, 60));

        card.add(studentLogin);    card.add(Box.createVerticalStrut(15));
        card.add(studentRegister); card.add(Box.createVerticalStrut(15));
        card.add(companyLogin);    card.add(Box.createVerticalStrut(15));
        card.add(companyRegister);

        rightOuter.add(card, new GridBagConstraints());

        main.add(left, BorderLayout.CENTER);
        main.add(rightOuter, BorderLayout.EAST);

        // actions
        studentLogin.addActionListener(e -> new StudentLogin());
        studentRegister.addActionListener(e -> new StudentRegister());
        companyLogin.addActionListener(e -> new CompanyLogin());
        companyRegister.addActionListener(e -> new CompanyRegister());

        setContentPane(main);
        setVisible(true);
    }

    private JButton outlinedButton(String text, Color borderColor) {
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
