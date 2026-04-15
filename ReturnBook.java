import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class ReturnBook {

    public static JPanel getPanel() {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(18,18,40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);

        JLabel title = new JLabel("Return Book");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel l1 = new JLabel("Book Name:");
        l1.setForeground(Color.WHITE);
        JTextField t1 = new JTextField(15);

        JButton b1 = new JButton("RETURN");
        b1.setBackground(new Color(0,123,255));
        b1.setForeground(Color.WHITE);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(l1, gbc);
        gbc.gridx = 1;
        panel.add(t1, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(b1, gbc);

        b1.addActionListener(e -> {
            Connection con = null;
            PreparedStatement checkPs = null;
            PreparedStatement deletePs = null;
            PreparedStatement updatePs = null;
            ResultSet rs = null;

            try {
                String bookName = t1.getText().trim();

                if (bookName.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Enter Book Name!");
                    return;
                }

                con = DBConnection.getConnection();

                // Check issued record exists or not
                String checkSql = "SELECT * FROM issue WHERE book_name = ? LIMIT 1";
                checkPs = con.prepareStatement(checkSql);
                checkPs.setString(1, bookName);
                rs = checkPs.executeQuery();

                if (!rs.next()) {
                    JOptionPane.showMessageDialog(panel, "This book is not issued!");
                    return;
                }

                // Delete one issued record
                String deleteSql = "DELETE FROM issue WHERE book_name = ? LIMIT 1";
                deletePs = con.prepareStatement(deleteSql);
                deletePs.setString(1, bookName);
                deletePs.executeUpdate();

                // Increase available quantity
                String updateSql = "UPDATE books SET available_quantity = available_quantity + 1 WHERE name = ?";
                updatePs = con.prepareStatement(updateSql);
                updatePs.setString(1, bookName);
                updatePs.executeUpdate();

                JOptionPane.showMessageDialog(panel, "Book Returned Successfully!");
                t1.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage());
                System.out.println(ex);
            } finally {
                try { if (rs != null) rs.close(); } catch (Exception ex) {}
                try { if (checkPs != null) checkPs.close(); } catch (Exception ex) {}
                try { if (deletePs != null) deletePs.close(); } catch (Exception ex) {}
                try { if (updatePs != null) updatePs.close(); } catch (Exception ex) {}
                try { if (con != null) con.close(); } catch (Exception ex) {}
            }
        });

        return panel;
    }
}
