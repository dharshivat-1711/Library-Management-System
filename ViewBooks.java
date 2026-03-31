import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ViewBooks {

    public static JPanel getPanel() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(18,18,40));

        String[] cols = {"ID","Name","Author","Quantity"};
        DefaultTableModel model = new DefaultTableModel(cols,0);

        JTable table = new JTable(model);
        table.setBackground(new Color(33,37,41));
        table.setForeground(Color.WHITE);

        JScrollPane sp = new JScrollPane(table);

        // SEARCH BAR
        JPanel top = new JPanel();
        JTextField search = new JTextField(15);
        JButton btn = new JButton("Search");

        btn.setBackground(new Color(0,123,255));
        btn.setForeground(Color.WHITE);

        top.add(new JLabel("Search:"));
        top.add(search);
        top.add(btn);

        panel.add(top, BorderLayout.NORTH);
        panel.add(sp, BorderLayout.CENTER);

        // LOAD DATA
        try {
            Connection con = DBConnection.getConnection();
            ResultSet rs = con.prepareStatement("SELECT * FROM books").executeQuery();

            while(rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("author"),
                    rs.getInt("quantity")
                });
            }

        } catch(Exception e) {
            System.out.println(e);
        }

        // SEARCH ACTION
        btn.addActionListener(e -> {
            try {
                model.setRowCount(0);

                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("SELECT * FROM books WHERE name LIKE ?");
                ps.setString(1, "%" + search.getText() + "%");

                ResultSet rs = ps.executeQuery();

                while(rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("author"),
                        rs.getInt("quantity")
                    });
                }

            } catch(Exception ex) {
                System.out.println(ex);
            }
        });

        return panel;
    }
}