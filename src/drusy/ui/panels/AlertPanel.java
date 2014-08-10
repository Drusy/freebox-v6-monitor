/*
 * Created by JFormDesigner on Sun Aug 10 15:35:49 CEST 2014
 */

package drusy.ui.panels;

import aurelienribon.ui.css.Style;

import java.awt.*;
import javax.swing.*;

/**
 * @author KÃ©vin Renella
 */
public class AlertPanel extends JPanel {
    public AlertPanel() {
        initComponents();

        Style.registerCssClasses(headerPanel, ".header");
    }

    public void anyNotif() {
        iconLabel.setIcon(new ImageIcon(getClass().getResource("/res/img/ic_ok.png")));
        label2.setText("Any notification");
    }

    public void downloadNotif() {
        iconLabel.setIcon(new ImageIcon(getClass().getResource("/res/img/ic_error.png")));
        label2.setText("Download rate saturated");
    }

    public void uploadNotif() {
        iconLabel.setIcon(new ImageIcon(getClass().getResource("/res/img/ic_error.png")));
        label2.setText("Upload rate saturated");
    }

    public void desynchronizedNotif() {
        iconLabel.setIcon(new ImageIcon(getClass().getResource("/res/img/ic_error.png")));
        label2.setText("Freebox desynchronized");
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - KÃ©vin Renella
        headerPanel = new JPanel();
        label1 = new JLabel();
        mainPanel = new JPanel();
        vSpacer2 = new JPanel(null);
        panel1 = new JPanel();
        hSpacer1 = new JPanel(null);
        iconLabel = new JLabel();
        hSpacer3 = new JPanel(null);
        label2 = new JLabel();
        hSpacer2 = new JPanel(null);
        vSpacer1 = new JPanel(null);

        //======== this ========

        setLayout(new BorderLayout());

        //======== headerPanel ========
        {
            headerPanel.setLayout(new BorderLayout());

            //---- label1 ----
            label1.setText("This panel notifies you for important alerts");
            label1.setHorizontalAlignment(SwingConstants.CENTER);
            headerPanel.add(label1, BorderLayout.CENTER);
        }
        add(headerPanel, BorderLayout.NORTH);

        //======== mainPanel ========
        {
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

            //---- vSpacer2 ----
            vSpacer2.setMaximumSize(new Dimension(10, 15));
            vSpacer2.setMinimumSize(new Dimension(10, 15));
            vSpacer2.setPreferredSize(new Dimension(10, 15));
            mainPanel.add(vSpacer2);

            //======== panel1 ========
            {
                panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
                panel1.add(hSpacer1);

                //---- iconLabel ----
                iconLabel.setIcon(new ImageIcon(getClass().getResource("/res/img/ic_ok.png")));
                panel1.add(iconLabel);

                //---- hSpacer3 ----
                hSpacer3.setMaximumSize(new Dimension(10, 10));
                hSpacer3.setMinimumSize(new Dimension(10, 10));
                panel1.add(hSpacer3);

                //---- label2 ----
                label2.setText("Any notification");
                panel1.add(label2);
                panel1.add(hSpacer2);
            }
            mainPanel.add(panel1);

            //---- vSpacer1 ----
            vSpacer1.setMinimumSize(new Dimension(10, 15));
            vSpacer1.setMaximumSize(new Dimension(10, 15));
            vSpacer1.setPreferredSize(new Dimension(10, 15));
            mainPanel.add(vSpacer1);
        }
        add(mainPanel, BorderLayout.CENTER);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - KÃ©vin Renella
    private JPanel headerPanel;
    private JLabel label1;
    private JPanel mainPanel;
    private JPanel vSpacer2;
    private JPanel panel1;
    private JPanel hSpacer1;
    private JLabel iconLabel;
    private JPanel hSpacer3;
    private JLabel label2;
    private JPanel hSpacer2;
    private JPanel vSpacer1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
