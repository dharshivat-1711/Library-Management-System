import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class DeleteBook {

    public static JPanel getPanel() {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(18,18,40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);

        JLabel title = new JLabel("Delete Book");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel l1 = new JLabel("Book ID:");
        l1.setForeground(Color.WHITE);
        JTextField t1 = new JTextField(15);

        JLabel l2 = new JLabel("Quantity to Delete:");
        l2.setForeground(Color.WHITE);
        JTextField t2 = new JTextField(15);

        JButton b1 = new JButton("DELETE");
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

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(b1, gbc);

        b1.addActionListener(e -> {
            Connection con = null;
            PreparedStatement checkPs = null;
            PreparedStatement updatePs = null;
            PreparedStatement deletePs = null;
            ResultSet rs = null;

            try {
                String idText = t1.getText().trim();
                String qtyText = t2.getText().trim();

                if (idText.isEmpty() || qtyText.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Enter Book ID and Quantity!");
                    return;
                }

                int bookId = Integer.parseInt(idText);
                int deleteQty = Integer.parseInt(qtyText);

                if (deleteQty <= 0) {
                    JOptionPane.showMessageDialog(panel, "Quantity must be greater than 0!");
                    return;
                }

                con = DBConnection.getConnection();

                String checkSql = "SELECT quantity, available_quantity FROM books WHERE id = ?";
                checkPs = con.prepareStatement(checkSql);
                checkPs.setInt(1, bookId);
                rs = checkPs.executeQuery();

                if (!rs.next()) {
                    JOptionPane.showMessageDialog(panel, "Book ID not found!");
                    return;
                }

                int totalQty = rs.getInt("quantity");
                int availableQty = rs.getInt("available_quantity");

                if (deleteQty > availableQty) {
                    JOptionPane.showMessageDialog(panel, "Cannot delete more than available quantity!");
                    return;
                }

                if (deleteQty == totalQty) {
                    String deleteSql = "DELETE FROM books WHERE id = ?";
                    deletePs = con.prepareStatement(deleteSql);
                    deletePs.setInt(1, bookId);
                    deletePs.executeUpdate();

                    JOptionPane.showMessageDialog(panel, "Book deleted completely!");
                } else {
                    String updateSql = "UPDATE books SET quantity = quantity - ?, available_quantity = available_quantity - ? WHERE id = ?";
                    updatePs = con.prepareStatement(updateSql);
                    updatePs.setInt(1, deleteQty);
                    updatePs.setInt(2, deleteQty);
                    updatePs.setInt(3, bookId);
                    updatePs.executeUpdate();

                    JOptionPane.showMessageDialog(panel, "Book quantity deleted successfully!");
                }

                t1.setText("");
                t2.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Enter valid numeric values!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage());
                System.out.println(ex);
            } finally {
                try { if (rs != null) rs.close(); } catch (Exception ex) {}
                try { if (checkPs != null) checkPs.close(); } catch (Exception ex) {}
                try { if (updatePs != null) updatePs.close(); } catch (Exception ex) {}
                try { if (deletePs != null) deletePs.close(); } catch (Exception ex) {}
                try { if (con != null) con.close(); } catch (Exception ex) {}
            }
        });

        return panel;
    }
}
