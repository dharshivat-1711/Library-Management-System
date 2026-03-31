import java.awt.*;
import javax.swing.*;

public class Login {
    public static void main(String[] args) {

        JFrame f = new JFrame("Login");

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(18,18,40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);

        JLabel title = new JLabel("Library Login");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.WHITE);

        JLabel l1 = new JLabel("Username:");
        l1.setForeground(Color.WHITE);
        JTextField t1 = new JTextField(15);

        JLabel l2 = new JLabel("Password:");
        l2.setForeground(Color.WHITE);
        JPasswordField t2 = new JPasswordField(15);

        JButton b1 = new JButton("Login");
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

        gbc.gridx=0; gbc.gridy=3; gbc.gridwidth=2;
        panel.add(b1, gbc);

        b1.addActionListener(e -> {
            String user = t1.getText();
            String pass = new String(t2.getPassword());

            if(user.equals("admin") && pass.equals("admin")) {
                f.dispose();
                Dashboard.main(null);
            } else {
                JOptionPane.showMessageDialog(f, "Invalid Login");
            }
        });

        f.add(panel);
        f.setSize(350,250);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}