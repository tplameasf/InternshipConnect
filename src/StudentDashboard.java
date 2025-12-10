import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class StudentDashboard extends JFrame {

    private int studentId;
    private JTable table;
    private DefaultTableModel model;

    public StudentDashboard(int studentId, String name) {
        this.studentId = studentId;

        setTitle("Student Dashboard - " + name);
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new DefaultTableModel(new String[]{"ID", "Company", "Title", "Location", "Stipend", "Deadline"}, 0);
        table = new JTable(model);

        JButton refreshButton = new JButton("Refresh");
        JButton applyButton = new JButton("Apply");

        refreshButton.addActionListener(e -> loadInternships());
        applyButton.addActionListener(e -> applyForSelected());

        JPanel bottom = new JPanel();
        bottom.add(refreshButton);
        bottom.add(applyButton);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        loadInternships();
        setVisible(true);
    }

    private void loadInternships() {
        model.setRowCount(0);
        try (Connection con = DatabaseConnection.getConnection()) {
            String sql = "SELECT i.internship_id, c.company_name, i.title, i.location, i.stipend, i.deadline_date " +
                         "FROM internships i JOIN companies c ON i.company_id = c.company_id";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("internship_id"),
                        rs.getString("company_name"),
                        rs.getString("title"),
                        rs.getString("location"),
                        rs.getDouble("stipend"),
                        rs.getDate("deadline_date")
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void applyForSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select an internship first.");
            return;
        }
        int internshipId = (int) model.getValueAt(row, 0);
        try (Connection con = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO applications (student_id, internship_id, application_date) VALUES (?, ?, CURDATE())";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, studentId);
            ps.setInt(2, internshipId);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Applied!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
