/*
 * Created by JFormDesigner on Mon Aug 04 21:13:44 CEST 2014
 */

package drusy.ui.panels;

import java.awt.*;
import javax.swing.*;

/**
 * @author Kevin Renella
 */
public class WifiStatePanel extends JPanel {
    public WifiStatePanel() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Kevin Renella
        headPanel = new JPanel();
        label1 = new JLabel();

        //======== this ========

        setLayout(new BorderLayout());

        //======== headPanel ========
        {
            headPanel.setLayout(new BorderLayout());

            //---- label1 ----
            label1.setText("This panel shows you the Wi-Fi state");
            label1.setHorizontalAlignment(SwingConstants.CENTER);
            headPanel.add(label1, BorderLayout.CENTER);
        }
        add(headPanel, BorderLayout.NORTH);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Kevin Renella
    private JPanel headPanel;
    private JLabel label1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
