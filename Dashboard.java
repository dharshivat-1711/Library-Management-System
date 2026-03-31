import java.awt.*;
import javax.swing.*;

public class Dashboard {

    static JPanel contentPanel;

    // 🔥 ICON RESIZE METHOD
    public static ImageIcon resize(String path) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    public static void main(String[] args) {

        JFrame f = new JFrame("Library System");
        f.setLayout(new BorderLayout());

        // ===== SIDEBAR =====
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(9,1,5,5));
        sidebar.setBackground(new Color(18,18,40));
        sidebar.setPreferredSize(new Dimension(180,0));

        // ===== BUTTONS WITH ICONS =====
        JButton b1 = new JButton("Add Book", resize("icons/add.png"));
        JButton b2 = new JButton("Issue Book", resize("icons/issue.png"));
        JButton b3 = new JButton("View Books", resize("icons/view.png"));
        JButton b4 = new JButton("Return Book", resize("icons/return.png"));
        JButton b5 = new JButton("Delete Book", resize("icons/delete.png"));
        JButton b6 = new JButton("View Issued", resize("icons/viewissue.png"));
        JButton b7 = new JButton("Dashboard", resize("icons/dashboard.png"));
        JButton logout = new JButton("Logout", resize("icons/logout.png"));

        JButton[] btns = {b1,b2,b3,b4,b5,b6,b7};

        // ===== STYLE BUTTONS =====
        for(JButton b : btns) {
            b.setBackground(new Color(0,102,204));
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.setFont(new Font("Arial", Font.BOLD, 14));
            b.setHorizontalAlignment(SwingConstants.LEFT);
            b.setIconTextGap(10);
            sidebar.add(b);
        }

        // ===== LOGOUT BUTTON =====
        logout.setBackground(new Color(220,53,69));
        logout.setForeground(Color.WHITE);
        logout.setFont(new Font("Arial", Font.BOLD, 14));
        logout.setFocusPainted(false);
        logout.setHorizontalAlignment(SwingConstants.LEFT);
        logout.setIconTextGap(10);

        sidebar.add(logout);

        // ===== CONTENT AREA =====
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(18,18,40));

        JLabel welcome = new JLabel("Welcome to Library System", JLabel.CENTER);
        welcome.setForeground(Color.WHITE);
        welcome.setFont(new Font("Arial", Font.BOLD, 22));

        contentPanel.add(welcome);

        // ===== BUTTON ACTIONS =====
        b1.addActionListener(e -> loadPanel(AddBook.getPanel()));
        b2.addActionListener(e -> loadPanel(IssueBook.getPanel()));
        b3.addActionListener(e -> loadPanel(ViewBooks.getPanel()));
        b4.addActionListener(e -> loadPanel(ReturnBook.getPanel()));
        b5.addActionListener(e -> loadPanel(DeleteBook.getPanel()));
        b6.addActionListener(e -> loadPanel(ViewIssue.getPanel()));
        b7.addActionListener(e -> loadPanel(DashboardPanel.getPanel()));

        logout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(f, "Do you want to logout?");
            if(confirm == 0) {
                f.dispose();
                Login.main(null);
            }
        });

        // ===== FRAME =====
        f.add(sidebar, BorderLayout.WEST);
        f.add(contentPanel, BorderLayout.CENTER);

        f.setSize(900,550);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    // ===== PANEL SWITCH =====
    static void loadPanel(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}