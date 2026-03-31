import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ViewIssue {

    public static JPanel getPanel() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(18,18,40));

        String[] cols = {"ID","Book","Student","Date"};
        DefaultTableModel model = new DefaultTableModel(cols,0);

        JTable table = new JTable(model);
        table.setBackground(new Color(33,37,41));
        table.setForeground(Color.WHITE);

        JScrollPane sp = new JScrollPane(table);
        panel.add(sp, BorderLayout.CENTER);

        try {
            Connection con = DBConnection.getConnection();
            ResultSet rs = con.prepareStatement("SELECT * FROM issue").executeQuery();

            while(rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("book_name"),
                    rs.getString("student_name"),
                    rs.getString("date")
                });
            }

        } catch(Exception e) {
            System.out.println(e);
        }

        return panel;
    }
}