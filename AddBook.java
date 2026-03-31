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

        gbc.gridx=0; gbc.gridy=0; gbc.gridwidth=2;
        panel.add(title, gbc);

        gbc.gridwidth=1;

        gbc.gridx=0; gbc.gridy=1;
        panel.add(l1, gbc);
        gbc.gridx=1;
        panel.add(t1, gbc);

        gbc.gridx=0; gbc.gridy=2;
        panel.add(l2, gbc);
        gbc.gridx=1;
        panel.add(t2, gbc);

        gbc.gridx=0; gbc.gridy=3;
        panel.add(l3, gbc);
        gbc.gridx=1;
        panel.add(t3, gbc);

        gbc.gridx=0; gbc.gridy=4; gbc.gridwidth=2;
        panel.add(b1, gbc);

        b1.addActionListener(e -> {
            try {

                // VALIDATION
                if(t1.getText().isEmpty() || t2.getText().isEmpty() || t3.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "All fields required!");
                    return;
                }

                Connection con = DBConnection.getConnection();

                String sql = "INSERT INTO books(name, author, quantity) VALUES (?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, t1.getText());
                ps.setString(2, t2.getText());
                ps.setInt(3, Integer.parseInt(t3.getText()));

                ps.executeUpdate();

                JOptionPane.showMessageDialog(panel, "Book Added!");

            } catch (Exception ex) {
                System.out.println(ex);
            }
        });

        return panel;
    }
}