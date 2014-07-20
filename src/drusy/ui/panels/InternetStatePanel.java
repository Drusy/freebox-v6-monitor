/*
 * Created by JFormDesigner on Sun Jul 20 21:01:18 CEST 2014
 */

package drusy.ui.panels;

import aurelienribon.ui.css.Style;
import aurelienribon.utils.DialogUtils;
import aurelienribon.utils.HttpUtils;
import drusy.ui.ErrorDialog;
import drusy.ui.MainPanel;
import drusy.utils.Config;
import drusy.utils.Log;
import org.json.JSONObject;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.swing.*;

/**
 * @author unknown
 */
public class InternetStatePanel extends JPanel {
    private MainPanel parentPanel;

    public InternetStatePanel(final MainPanel parentPanel) {
        initComponents();

        this.parentPanel = parentPanel;

        Style.registerCssClasses(headerPanel, ".header");
    }

    public void update() {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        HttpUtils.DownloadTask task = HttpUtils.downloadAsync(Config.FREEBOX_API_CONNECTION, output, "Fetching connection state");

        task.addListener(new HttpUtils.DownloadListener() {
            @Override public void onComplete() {
                String json = output.toString();
                JSONObject obj = new JSONObject(json);
                boolean success = obj.getBoolean("success");

                if (success == true) {
                    JSONObject result = obj.getJSONObject("result");
                    int rateDown = result.getInt("rate_down");
                    int rateUp = result.getInt("rate_up");
                    String ipv4 = result.getString("ipv4");

                    ipContentLabel.setText(ipv4);
                    downloadContentLabel.setText(String.valueOf(rateDown));
                    uploadContentLabel.setText(String.valueOf(rateUp));
                } else {
                    String msg = obj.getString("msg");
                    Log.Debug("Freebox Connection State", msg);
                }
            }
        });

        task.addListener(new HttpUtils.DownloadListener() {
            @Override
            public void onError(IOException ex) {
                Log.Debug("Freebox Connection State", ex.getMessage());
            }
        });
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Kevin Renella
        headerPanel = new JPanel();
        label1 = new JLabel();
        mainPanel = new JPanel();
        ipTitleLabel = new JLabel();
        ipContentLabel = new JTextField();
        uploadTitleLabel = new JLabel();
        uploadContentLabel = new JTextField();
        downloadContentLabel = new JTextField();
        downloadTitleLabel = new JLabel();

        //======== this ========
        setMinimumSize(new Dimension(100, 71));

        setLayout(new BorderLayout());

        //======== headerPanel ========
        {
            headerPanel.setLayout(new BorderLayout());

            //---- label1 ----
            label1.setText("This panel shows you an overview of the internet connection");
            label1.setHorizontalAlignment(SwingConstants.CENTER);
            headerPanel.add(label1, BorderLayout.CENTER);
        }
        add(headerPanel, BorderLayout.NORTH);

        //======== mainPanel ========
        {
            mainPanel.setOpaque(false);
            mainPanel.setMinimumSize(new Dimension(100, 100));

            //---- ipTitleLabel ----
            ipTitleLabel.setText("IP v4 :");
            ipTitleLabel.setHorizontalAlignment(SwingConstants.RIGHT);

            //---- ipContentLabel ----
            ipContentLabel.setEditable(false);
            ipContentLabel.setText("192.168.0.1");
            ipContentLabel.setHorizontalAlignment(SwingConstants.CENTER);

            //---- uploadTitleLabel ----
            uploadTitleLabel.setText("Upload Bandwidth :");
            uploadTitleLabel.setHorizontalAlignment(SwingConstants.RIGHT);

            //---- uploadContentLabel ----
            uploadContentLabel.setEditable(false);
            uploadContentLabel.setText("250 ko/s");
            uploadContentLabel.setHorizontalAlignment(SwingConstants.CENTER);

            //---- downloadContentLabel ----
            downloadContentLabel.setEditable(false);
            downloadContentLabel.setText("1.2 mo/s");
            downloadContentLabel.setHorizontalAlignment(SwingConstants.CENTER);

            //---- downloadTitleLabel ----
            downloadTitleLabel.setText("Download Bandwidth :");
            downloadTitleLabel.setHorizontalAlignment(SwingConstants.RIGHT);

            GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
            mainPanel.setLayout(mainPanelLayout);
            mainPanelLayout.setHorizontalGroup(
                mainPanelLayout.createParallelGroup()
                    .addGroup(mainPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(uploadTitleLabel)
                                    .addComponent(downloadTitleLabel)
                                    .addComponent(ipTitleLabel))
                            .addGap(10, 10, 10)
                            .addGroup(mainPanelLayout.createParallelGroup()
                                    .addComponent(ipContentLabel, GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                                    .addComponent(uploadContentLabel, GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                                    .addComponent(downloadContentLabel, GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE))
                            .addContainerGap())
            );
            mainPanelLayout.setVerticalGroup(
                    mainPanelLayout.createParallelGroup()
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                    .addGap(20, 20, 20)
                                    .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(ipTitleLabel, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(ipContentLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(uploadTitleLabel, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(uploadContentLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(downloadTitleLabel, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(downloadContentLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                    .addContainerGap())
            );
        }
        add(mainPanel, BorderLayout.CENTER);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Kevin Renella
    private JPanel headerPanel;
    private JLabel label1;
    private JPanel mainPanel;
    private JLabel ipTitleLabel;
    private JTextField ipContentLabel;
    private JLabel uploadTitleLabel;
    private JTextField uploadContentLabel;
    private JTextField downloadContentLabel;
    private JLabel downloadTitleLabel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
