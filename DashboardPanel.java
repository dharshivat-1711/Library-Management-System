import javax.swing.*;
import java.awt.*;
import java.sql.*;

import org.jfree.chart.*;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class DashboardPanel {

    public static JPanel getPanel() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(18,18,40));

        int total = 0, issued = 0;

        try {
            Connection con = DBConnection.getConnection();

            ResultSet rs1 = con.prepareStatement("SELECT COUNT(*) FROM books").executeQuery();
            if(rs1.next()) total = rs1.getInt(1);

            ResultSet rs2 = con.prepareStatement("SELECT COUNT(*) FROM issue").executeQuery();
            if(rs2.next()) issued = rs2.getInt(1);

        } catch(Exception e) {
            System.out.println(e);
        }

        int available = total - issued;

        // ===== DATA =====
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Issued", issued);
        dataset.setValue("Available", available);

        // ===== CHART =====
        JFreeChart chart = ChartFactory.createPieChart(
                "Library Status",
                dataset,
                true,
                true,
                false
        );

        // ===== DARK THEME =====
        chart.setBackgroundPaint(new Color(18,18,40));
        chart.getTitle().setPaint(Color.WHITE);
        chart.getTitle().setFont(new Font("Arial", Font.BOLD, 20));

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(new Color(18,18,40));
        plot.setOutlineVisible(false);

        // COLORS
        plot.setSectionPaint("Issued", new Color(220,53,69));   // red
        plot.setSectionPaint("Available", new Color(0,123,255)); // blue

        // 🔥 REMOVE WHITE BOXES
        plot.setLabelBackgroundPaint(new Color(18,18,40));
        plot.setLabelOutlinePaint(null);
        plot.setLabelShadowPaint(null);
        plot.setLabelPaint(Color.WHITE);

        // OPTIONAL CLEAN LOOK (uncomment if you want no labels)
        // plot.setLabelGenerator(null);

        // ===== PANEL =====
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600,400));
        chartPanel.setBackground(new Color(18,18,40));

        panel.add(chartPanel, BorderLayout.CENTER);

        return panel;
    }
}