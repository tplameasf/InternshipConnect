import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class CompanyDashboard extends JFrame {

    private int companyId;
    private JTable table;
    private DefaultTableModel model;

    public CompanyDashboard(int companyId, String name) {
        this.companyId = companyId;

        setTitle("Company Dashboard - " + name);
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new DefaultTableModel(new String[]{"ID", "Title", "Location", "Stipend", "Deadline", "Applications"}, 0);
        table = new JTable(model);

        JButton refreshButton = new JButton("Refresh");
        JButton postButton = new JButton("Post Internship");
        JButton viewAppsButton = new JButton("View Applications");

        refreshButton.addActionListener(e -> loadInternships());
        postButton.addActionListener(e -> openPostDialog());
        viewAppsButton.addActionListener(e -> openApplicationsDialog());

        JPanel bottom = new JPanel();
        bottom.add(refreshButton);
        bottom.add(postButton);
        bottom.add(viewAppsButton);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        loadInternships();
        setVisible(true);
    }

    private void loadInternships() {
        model.setRowCount(0);
        try (Connection con = DatabaseConnection.getConnection()) {
            String sql = "SELECT i.internship_id, i.title, i.location, i.stipend, i.deadline_date, " +
                         "COUNT(a.application_id) AS apps " +
                         "FROM internships i LEFT JOIN applications a ON i.internship_id = a.internship_id " +
                         "WHERE i.company_id = ? GROUP BY i.internship_id";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, companyId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("internship_id"),
                        rs.getString("title"),
                        rs.getString("location"),
                        rs.getDouble("stipend"),
                        rs.getDate("deadline_date"),
                        rs.getInt("apps")
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void openPostDialog() {
        JTextField titleField = new JTextField();
        JTextField locationField = new JTextField();
        JTextField durationField = new JTextField();
        JTextField stipendField = new JTextField();
        JTextField deadlineField = new JTextField();
        JTextArea descArea = new JTextArea(3, 20);
        JTextArea reqArea = new JTextArea(3, 20);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Title:")); panel.add(titleField);
        panel.add(new JLabel("Location:")); panel.add(locationField);
        panel.add(new JLabel("Duration:")); panel.add(durationField);
        panel.add(new JLabel("Stipend:")); panel.add(stipendField);
        panel.add(new JLabel("Deadline (YYYY-MM-DD):")); panel.add(deadlineField);
        panel.add(new JLabel("Description:")); panel.add(new JScrollPane(descArea));
        panel.add(new JLabel("Requirements:")); panel.add(new JScrollPane(reqArea));

        int result = JOptionPane.showConfirmDialog(this, panel, "Post Internship", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try (Connection con = DatabaseConnection.getConnection()) {
                String sql = "INSERT INTO internships (company_id, title, description, requirements, location, duration, stipend, deadline_date) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, companyId);
                ps.setString(2, titleField.getText());
                ps.setString(3, descArea.getText());
                ps.setString(4, reqArea.getText());
                ps.setString(5, locationField.getText());
                ps.setString(6, durationField.getText());
                ps.setDouble(7, Double.parseDouble(stipendField.getText()));
                ps.setString(8, deadlineField.getText());
                ps.executeUpdate();
                loadInternships();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void openApplicationsDialog() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select an internship first.");
            return;
        }
        int internshipId = (int) model.getValueAt(row, 0);

        DefaultTableModel appsModel = new DefaultTableModel(
                new String[]{"App ID", "Student", "Email", "College", "Course", "Date", "Status"}, 0);
        JTable appsTable = new JTable(appsModel);

        try (Connection con = DatabaseConnection.getConnection()) {
            String sql = "SELECT a.application_id, s.name, s.email, s.college, s.course, a.application_date, a.status " +
                         "FROM applications a JOIN students s ON a.student_id = s.student_id " +
                         "WHERE a.internship_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, internshipId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                appsModel.addRow(new Object[]{
                        rs.getInt("application_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("college"),
                        rs.getString("course"),
                        rs.getDate("application_date"),
                        rs.getString("status")
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JOptionPane.showMessageDialog(this, new JScrollPane(appsTable), "Applications", JOptionPane.PLAIN_MESSAGE);
    }
}
