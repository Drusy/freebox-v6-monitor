/*
 * Created by JFormDesigner on Fri Aug 08 20:17:09 CEST 2014
 */

package drusy.ui.panels;

import aurelienribon.ui.css.Style;
import aurelienribon.utils.Res;
import de.erichseifert.gral.data.DataSource;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.areas.AreaRenderer;
import de.erichseifert.gral.plots.areas.DefaultAreaRenderer2D;
import de.erichseifert.gral.plots.areas.LineAreaRenderer2D;
import de.erichseifert.gral.plots.axes.AxisRenderer;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.DiscreteLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.plots.lines.SmoothLineRenderer2D;
import de.erichseifert.gral.plots.points.DefaultPointRenderer2D;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.GraphicsUtils;
import de.erichseifert.gral.util.Insets2D;
import drusy.utils.Config;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;

/**
 * @author Kevin Renella
 */
public class ChartPanel extends JPanel {
    private java.util.Timer timer;
    private XYPlot plot = null;
    private volatile InteractivePanel interactivePanel = null;
    private DataTable data = new DataTable(Double.class, Double.class);
    private int maxDataCount = 100;
    private Number maxByteRate = 1000;
    private Color chartColor;
    private int offset;

    public ChartPanel(String headerLabel, Color chartColor, final int offset) {
        initComponents();

        this.chartColor = chartColor;
        this.offset = offset;

        label1.setText(headerLabel);

        Style.registerCssClasses(headerPanel, ".header");
    }

    public void addDataValue(double value, Number maxByteRate) {
        if (data.getRowCount() > maxDataCount) {
            data.remove(0);
        }

        data.add((double)(new Date().getTime()), value);
        update(maxByteRate);
    }

    private void updateInteractivePanel(DataTable data, Number maxByteRate) {
        plot = new XYPlot(data);

        // Hide points
        plot.setPointRenderer(data, null);

        // Chart spacing
        double insetsTop = 10.0,
                insetsLeft = 60.0,
                insetsBottom = 60.0,
                insetsRight = 10.0;
        plot.setInsets(new Insets2D.Double(
                insetsTop, insetsLeft, insetsBottom, insetsRight));

        // Manage axis Y
        plot.getAxisRenderer(XYPlot.AXIS_Y).setLabel("ko/s");
        plot.getAxisRenderer(XYPlot.AXIS_Y).setTicksVisible(true);
        plot.getAxisRenderer(XYPlot.AXIS_Y).setTickLabelDistance(0.5);
        plot.getAxisRenderer(XYPlot.AXIS_Y).setLabelDistance(1.25);
        plot.getAxis(XYPlot.AXIS_Y).setMin(0);

        if (maxByteRate.intValue() > 0) {
            plot.getAxis(XYPlot.AXIS_Y).setMax(maxByteRate);
        } else {
            plot.getAxis(XYPlot.AXIS_Y).setMax(this.maxByteRate);
        }

        // Manage axis X
        plot.getAxisRenderer(XYPlot.AXIS_X).setLabel("time");
        plot.getAxisRenderer(XYPlot.AXIS_X).setTicksVisible(true);
        plot.getAxisRenderer(XYPlot.AXIS_X).setTickLabelDistance(0.5);
        plot.getAxisRenderer(XYPlot.AXIS_X).setLabelDistance(0.75);
        plot.getAxisRenderer(XYPlot.AXIS_X).setTickLabelFormat(new SimpleDateFormat("HH:mm"));

        // Filled area
        formatFilledArea(plot, data, chartColor);

        // Add to panel
        mainPanel.removeAll();
        interactivePanel = new InteractivePanel(plot);
        mainPanel.add(interactivePanel);
    }

    private void formatFilledArea(XYPlot plot, DataSource data, Color color) {
        LineRenderer line = new DefaultLineRenderer2D();
        line.setColor(color);
        line.setGap(3.0);
        line.setGapRounded(true);
        plot.setLineRenderer(data, line);
        AreaRenderer area = new DefaultAreaRenderer2D();
        area.setColor(GraphicsUtils.deriveWithAlpha(color, 64));
        plot.setAreaRenderer(data, area);
    }

    private void update(Number maxByteRate) {
        this.maxByteRate = maxByteRate;

        updateInteractivePanel(data, maxByteRate);
        adaptPanelSize();
    }

    private void adaptPanelSize() {
        setSize(getWidth(), (int)getPreferredSize().getHeight() + Config.CHART_HEIGHT);
        setLocation(getX(), (getHeight() + Config.GAP) * offset + Config.GAP);
        validate();
        repaint();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Kevin Renella
        headerPanel = new JPanel();
        label1 = new JLabel();
        mainPanel = new JPanel();

        //======== this ========

        setLayout(new BorderLayout());

        //======== headerPanel ========
        {
            headerPanel.setLayout(new BorderLayout());

            //---- label1 ----
            label1.setText("This panel plot the byte rate charts");
            label1.setHorizontalAlignment(SwingConstants.CENTER);
            headerPanel.add(label1, BorderLayout.NORTH);
        }
        add(headerPanel, BorderLayout.NORTH);

        //======== mainPanel ========
        {
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        }
        add(mainPanel, BorderLayout.CENTER);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Kevin Renella
    private JPanel headerPanel;
    private JLabel label1;
    private JPanel mainPanel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
