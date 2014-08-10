/*
 * Created by JFormDesigner on Sat Aug 09 21:32:28 CEST 2014
 */

package drusy.ui.panels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.*;
import aurelienribon.ui.components.Button;
import aurelienribon.ui.css.Style;
import aurelienribon.utils.SwingUtils;
import drusy.ui.MainPanel;

/**
 * @author Kevin Renella
 */
public class AboutPanel extends JPanel {
    public AboutPanel(final MainPanel parent) {
        initComponents();

        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        Style.registerCssClasses(cssEngineLabel, ".linkLabel");
        Style.registerCssClasses(aurelienRibonLabel, ".linkLabel");
        Style.registerCssClasses(gralLabel, ".linkLabel");
        Style.registerCssClasses(freeboxAPILabel, ".linkLabel");
        Style.registerCssClasses(slidingLayoutLabel, ".linkLabel");
        Style.registerCssClasses(tweenEngineLabel, ".linkLabel");

        SwingUtils.addBrowseBehavior(freeboxAPILabel, "http://dev.freebox.fr/sdk/os/");
        SwingUtils.addBrowseBehavior(aurelienRibonLabel, "http://www.aurelienribon.com");
        SwingUtils.addBrowseBehavior(cssEngineLabel, "http://code.google.com/p/java-universal-css-engine/");
        SwingUtils.addBrowseBehavior(slidingLayoutLabel, "https://github.com/AurelienRibon/sliding-layout");
        SwingUtils.addBrowseBehavior(tweenEngineLabel, "http://www.aurelienribon.com/blog/projects/universal-tween-engine/");
        SwingUtils.addBrowseBehavior(gralLabel, "http://trac.erichseifert.de/gral/");

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.hideAboutPanel();
            }
        });
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - KÃ©vin Renella
        mainPanel = new JPanel();
        vSpacer1 = new JPanel(null);
        panel4 = new JPanel();
        label1 = new JLabel();
        hSpacer4 = new JPanel(null);
        vSpacer3 = new JPanel(null);
        panel5 = new JPanel();
        label2 = new JLabel();
        hSpacer5 = new JPanel(null);
        panel7 = new JPanel();
        label3 = new JLabel();
        hSpacer9 = new JPanel(null);
        vSpacer4 = new JPanel(null);
        panel6 = new JPanel();
        label4 = new JLabel();
        hSpacer8 = new JPanel(null);
        vSpacer6 = new JPanel(null);
        panel1 = new JPanel();
        hSpacer2 = new JPanel(null);
        panel2 = new JPanel();
        cssEngineLabel = new JLabel();
        slidingLayoutLabel = new JLabel();
        gralLabel = new JLabel();
        hSpacer3 = new JPanel(null);
        panel3 = new JPanel();
        tweenEngineLabel = new JLabel();
        aurelienRibonLabel = new JLabel();
        freeboxAPILabel = new JLabel();
        hSpacer7 = new JPanel(null);
        hSpacer6 = new JPanel(null);
        hSpacer1 = new JPanel(null);
        vSpacer2 = new JPanel(null);
        panel8 = new JPanel();
        logoLabel = new JLabel();
        hSpacer10 = new JPanel(null);
        panel10 = new JPanel();
        vSpacer5 = new JPanel(null);
        backButton = new Button();

        //======== this ========
        setBackground(new Color(240, 240, 240, 0));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //======== mainPanel ========
        {
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

            //---- vSpacer1 ----
            vSpacer1.setMaximumSize(new Dimension(37000, 20));
            vSpacer1.setMinimumSize(new Dimension(37000, 20));
            vSpacer1.setPreferredSize(new Dimension(37000, 20));
            mainPanel.add(vSpacer1);

            //======== panel4 ========
            {
                panel4.setLayout(new BoxLayout(panel4, BoxLayout.X_AXIS));

                //---- label1 ----
                label1.setText("Freebox v6 Monitor allow you to have a full overview of your Freebox Revolution.");
                label1.setBackground(new Color(240, 240, 240, 0));
                label1.setHorizontalAlignment(SwingConstants.CENTER);
                panel4.add(label1);

                //---- hSpacer4 ----
                hSpacer4.setMaximumSize(new Dimension(32700, 10));
                panel4.add(hSpacer4);
            }
            mainPanel.add(panel4);

            //---- vSpacer3 ----
            vSpacer3.setMaximumSize(new Dimension(37000, 20));
            vSpacer3.setMinimumSize(new Dimension(37000, 20));
            vSpacer3.setPreferredSize(new Dimension(37000, 20));
            mainPanel.add(vSpacer3);

            //======== panel5 ========
            {
                panel5.setMinimumSize(new Dimension(176, 0));
                panel5.setLayout(new BoxLayout(panel5, BoxLayout.X_AXIS));

                //---- label2 ----
                label2.setText("Core developed by Kevin Renella.");
                label2.setBackground(new Color(240, 240, 240, 0));
                panel5.add(label2);

                //---- hSpacer5 ----
                hSpacer5.setMinimumSize(new Dimension(10, 2));
                hSpacer5.setMaximumSize(new Dimension(32767, 5));
                panel5.add(hSpacer5);
            }
            mainPanel.add(panel5);

            //======== panel7 ========
            {
                panel7.setMinimumSize(new Dimension(298, 0));
                panel7.setLayout(new BoxLayout(panel7, BoxLayout.X_AXIS));

                //---- label3 ----
                label3.setText("Swing GUI entirely base on Aurelien Ribon's CSS & libraries.");
                label3.setBackground(new Color(240, 240, 240, 0));
                panel7.add(label3);

                //---- hSpacer9 ----
                hSpacer9.setMinimumSize(new Dimension(10, 2));
                hSpacer9.setMaximumSize(new Dimension(32767, 5));
                panel7.add(hSpacer9);
            }
            mainPanel.add(panel7);

            //---- vSpacer4 ----
            vSpacer4.setMaximumSize(new Dimension(37000, 20));
            vSpacer4.setMinimumSize(new Dimension(37000, 20));
            vSpacer4.setPreferredSize(new Dimension(37000, 20));
            mainPanel.add(vSpacer4);

            //======== panel6 ========
            {
                panel6.setLayout(new BoxLayout(panel6, BoxLayout.X_AXIS));

                //---- label4 ----
                label4.setText("Libraries & API used");
                label4.setFont(new Font("Tahoma", Font.BOLD, 11));
                label4.setBackground(new Color(240, 240, 240, 0));
                panel6.add(label4);

                //---- hSpacer8 ----
                hSpacer8.setMaximumSize(new Dimension(32767, 15));
                panel6.add(hSpacer8);
            }
            mainPanel.add(panel6);

            //---- vSpacer6 ----
            vSpacer6.setMaximumSize(new Dimension(37000, 10));
            vSpacer6.setMinimumSize(new Dimension(37000, 10));
            vSpacer6.setPreferredSize(new Dimension(37000, 10));
            mainPanel.add(vSpacer6);

            //======== panel1 ========
            {
                panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));

                //---- hSpacer2 ----
                hSpacer2.setMaximumSize(new Dimension(10, 20));
                panel1.add(hSpacer2);

                //======== panel2 ========
                {
                    panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));

                    //---- cssEngineLabel ----
                    cssEngineLabel.setText(":: CSS Engine for Swing");
                    panel2.add(cssEngineLabel);

                    //---- slidingLayoutLabel ----
                    slidingLayoutLabel.setText(":: Sliding Layout for Swing");
                    panel2.add(slidingLayoutLabel);

                    //---- gralLabel ----
                    gralLabel.setText(":: GRAL Charts");
                    panel2.add(gralLabel);
                }
                panel1.add(panel2);

                //---- hSpacer3 ----
                hSpacer3.setMaximumSize(new Dimension(32767, 20));
                panel1.add(hSpacer3);

                //======== panel3 ========
                {
                    panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));

                    //---- tweenEngineLabel ----
                    tweenEngineLabel.setText(":: Universal Tween Engine");
                    panel3.add(tweenEngineLabel);

                    //---- aurelienRibonLabel ----
                    aurelienRibonLabel.setText(":: Aurelien Ribon CSS");
                    panel3.add(aurelienRibonLabel);

                    //---- freeboxAPILabel ----
                    freeboxAPILabel.setText(":: Freebox v6 API");
                    panel3.add(freeboxAPILabel);
                }
                panel1.add(panel3);

                //---- hSpacer7 ----
                hSpacer7.setMaximumSize(new Dimension(32767, 20));
                panel1.add(hSpacer7);

                //---- hSpacer6 ----
                hSpacer6.setMaximumSize(new Dimension(32767, 20));
                panel1.add(hSpacer6);

                //---- hSpacer1 ----
                hSpacer1.setMaximumSize(new Dimension(32767, 20));
                panel1.add(hSpacer1);
            }
            mainPanel.add(panel1);
            mainPanel.add(vSpacer2);

            //======== panel8 ========
            {
                panel8.setLayout(new BoxLayout(panel8, BoxLayout.X_AXIS));

                //---- logoLabel ----
                logoLabel.setIcon(new ImageIcon(getClass().getResource("/res/img/icon-monitor-128.png")));
                panel8.add(logoLabel);
                panel8.add(hSpacer10);

                //======== panel10 ========
                {
                    panel10.setLayout(new BoxLayout(panel10, BoxLayout.Y_AXIS));

                    //---- vSpacer5 ----
                    vSpacer5.setMaximumSize(new Dimension(10, 32767));
                    panel10.add(vSpacer5);

                    //---- backButton ----
                    backButton.setText("Back to monitor");
                    backButton.setPreferredSize(new Dimension(150, 35));
                    backButton.setMinimumSize(new Dimension(150, 35));
                    backButton.setMaximumSize(new Dimension(150, 35));
                    backButton.setHorizontalTextPosition(SwingConstants.CENTER);
                    panel10.add(backButton);
                }
                panel8.add(panel10);
            }
            mainPanel.add(panel8);
        }
        add(mainPanel);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - KÃ©vin Renella
    private JPanel mainPanel;
    private JPanel vSpacer1;
    private JPanel panel4;
    private JLabel label1;
    private JPanel hSpacer4;
    private JPanel vSpacer3;
    private JPanel panel5;
    private JLabel label2;
    private JPanel hSpacer5;
    private JPanel panel7;
    private JLabel label3;
    private JPanel hSpacer9;
    private JPanel vSpacer4;
    private JPanel panel6;
    private JLabel label4;
    private JPanel hSpacer8;
    private JPanel vSpacer6;
    private JPanel panel1;
    private JPanel hSpacer2;
    private JPanel panel2;
    private JLabel cssEngineLabel;
    private JLabel slidingLayoutLabel;
    private JLabel gralLabel;
    private JPanel hSpacer3;
    private JPanel panel3;
    private JLabel tweenEngineLabel;
    private JLabel aurelienRibonLabel;
    private JLabel freeboxAPILabel;
    private JPanel hSpacer7;
    private JPanel hSpacer6;
    private JPanel hSpacer1;
    private JPanel vSpacer2;
    private JPanel panel8;
    private JLabel logoLabel;
    private JPanel hSpacer10;
    private JPanel panel10;
    private JPanel vSpacer5;
    private Button backButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
