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

        JButton b1 = new JButton("DELETE");
        b1.setBackground(new Color(0,123,255));
        b1.setForeground(Color.WHITE);

        gbc.gridx=0; gbc.gridy=0; gbc.gridwidth=2;
        panel.add(title, gbc);

        gbc.gridwidth=1;

        gbc.gridx=0; gbc.gridy=1;
        panel.add(l1, gbc);
        gbc.gridx=1;
        panel.add(t1, gbc);

        gbc.gridx=0; gbc.gridy=2; gbc.gridwidth=2;
        panel.add(b1, gbc);

        b1.addActionListener(e -> {
            try {

                if(t1.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Enter Book ID!");
                    return;
                }

                Connection con = DBConnection.getConnection();

                String sql = "DELETE FROM books WHERE id=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(t1.getText()));

                ps.executeUpdate();

                JOptionPane.showMessageDialog(panel, "Book Deleted!");

            } catch (Exception ex) {
                System.out.println(ex);
            }
        });

        return panel;
    }
}