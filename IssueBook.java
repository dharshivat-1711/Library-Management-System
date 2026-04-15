import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import javax.swing.*;

public class IssueBook {

    public static JPanel getPanel() {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(18,18,40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);

        JLabel title = new JLabel("Issue Book");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel l1 = new JLabel("Book Name:");
        l1.setForeground(Color.WHITE);
        JTextField t1 = new JTextField(15);

        JLabel l2 = new JLabel("Student:");
        l2.setForeground(Color.WHITE);
        JTextField t2 = new JTextField(15);

        JLabel l3 = new JLabel("Date:");
        l3.setForeground(Color.WHITE);

        JTextField t3 = new JTextField(LocalDate.now().toString(), 15);
        t3.setEditable(false);

        JButton b1 = new JButton("ISSUE");
        b1.setBackground(new Color(0,123,255));
        b1.setForeground(Color.WHITE);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(l1, gbc);
        gbc.gridx = 1;
        panel.add(t1, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(l2, gbc);
        gbc.gridx = 1;
        panel.add(t2, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(l3, gbc);
        gbc.gridx = 1;
        panel.add(t3, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(b1, gbc);

        b1.addActionListener(e -> {
            Connection con = null;
            PreparedStatement checkPs = null;
            PreparedStatement insertPs = null;
            PreparedStatement updatePs = null;
            ResultSet rs = null;

            try {
                String bookName = t1.getText().trim();
                String studentName = t2.getText().trim();
                String issueDate = t3.getText().trim();

                if (bookName.isEmpty() || studentName.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Enter all details!");
                    return;
                }

                con = DBConnection.getConnection();

                // 1. Check if book exists and available
                String checkSql = "SELECT available_quantity FROM books WHERE name = ?";
                checkPs = con.prepareStatement(checkSql);
                checkPs.setString(1, bookName);
                rs = checkPs.executeQuery();

                if (!rs.next()) {
                    JOptionPane.showMessageDialog(panel, "Book not found!");
                    return;
                }

                int availableQty = rs.getInt("available_quantity");

                if (availableQty <= 0) {
                    JOptionPane.showMessageDialog(panel, "Book is not available!");
                    return;
                }

                // 2. Insert into issue table
                String insertSql = "INSERT INTO issue(book_name, student_name, date) VALUES (?, ?, ?)";
                insertPs = con.prepareStatement(insertSql);
                insertPs.setString(1, bookName);
                insertPs.setString(2, studentName);
                insertPs.setDate(3, Date.valueOf(issueDate));
                insertPs.executeUpdate();

                // 3. Reduce available quantity
                String updateSql = "UPDATE books SET available_quantity = available_quantity - 1 WHERE name = ?";
                updatePs = con.prepareStatement(updateSql);
                updatePs.setString(1, bookName);
                updatePs.executeUpdate();

                JOptionPane.showMessageDialog(panel, "Book Issued Successfully!");

                t1.setText("");
                t2.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage());
                System.out.println(ex);
            } finally {
                try { if (rs != null) rs.close(); } catch (Exception ex) {}
                try { if (checkPs != null) checkPs.close(); } catch (Exception ex) {}
                try { if (insertPs != null) insertPs.close(); } catch (Exception ex) {}
                try { if (updatePs != null) updatePs.close(); } catch (Exception ex) {}
                try { if (con != null) con.close(); } catch (Exception ex) {}
            }
        });

        return panel;
    }
}
