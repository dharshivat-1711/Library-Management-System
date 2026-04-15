import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class AddBook {

    public static JPanel getPanel() {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(18,18,40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);

        JLabel title = new JLabel("Add Book");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel l1 = new JLabel("Book Name:");
        l1.setForeground(Color.WHITE);
        JTextField t1 = new JTextField(15);

        JLabel l2 = new JLabel("Author:");
        l2.setForeground(Color.WHITE);
        JTextField t2 = new JTextField(15);

        JLabel l3 = new JLabel("Quantity:");
        l3.setForeground(Color.WHITE);
        JTextField t3 = new JTextField(15);

        JButton b1 = new JButton("ADD");
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
            PreparedStatement ps = null;

            try {
                String bookName = t1.getText().trim();
                String author = t2.getText().trim();
                String qtyText = t3.getText().trim();

                if (bookName.isEmpty() || author.isEmpty() || qtyText.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "All fields required!");
                    return;
                }

                int qty = Integer.parseInt(qtyText);

                if (qty <= 0) {
                    JOptionPane.showMessageDialog(panel, "Quantity must be greater than 0!");
                    return;
                }

                con = DBConnection.getConnection();

                String sql = "INSERT INTO books(name, author, quantity, available_quantity) VALUES (?, ?, ?, ?)";
                ps = con.prepareStatement(sql);

                ps.setString(1, bookName);
                ps.setString(2, author);
                ps.setInt(3, qty);
                ps.setInt(4, qty);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(panel, "Book Added Successfully!");

                t1.setText("");
                t2.setText("");
                t3.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Quantity must be a number!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage());
                System.out.println(ex);
            } finally {
                try { if (ps != null) ps.close(); } catch (Exception ex) {}
                try { if (con != null) con.close(); } catch (Exception ex) {}
            }
        });

        return panel;
    }
}
