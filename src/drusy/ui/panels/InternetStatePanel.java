/*
 * Created by JFormDesigner on Sun Jul 20 21:01:18 CEST 2014
 */

package drusy.ui.panels;

import aurelienribon.ui.css.Style;
import aurelienribon.utils.HttpUtils;
import drusy.ui.MainPanel;
import drusy.utils.Config;
import drusy.utils.Log;
import drusy.utils.Updater;
import org.json.JSONObject;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.TimerTask;
import javax.swing.*;

/**
 * @author unknown
 */
public class InternetStatePanel extends JPanel {
    private MainPanel parentPanel;
    private ChartPanel downloadChartPanel;
    private ChartPanel uploadChartPanel;
    private java.util.Timer timer;
    private Updater.FakeUpdater fakeUpdater = new Updater.FakeUpdater();

    public InternetStatePanel(final MainPanel parentPanel, final ChartPanel downloadChartPanel, final ChartPanel uploadChartPanel) {
        initComponents();

        this.parentPanel = parentPanel;
        this.uploadChartPanel = uploadChartPanel;
        this.downloadChartPanel = downloadChartPanel;

        Style.registerCssClasses(headerPanel, ".header");
    }

    public void updatePeriodically() {
        long delay = 1000 * 2;

        timer = new java.util.Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                update(fakeUpdater);
            }
        }, 0, delay);
    }

    public void update(final Updater updater) {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        HttpUtils.DownloadGetTask task = HttpUtils.downloadGetAsync(Config.FREEBOX_API_CONNECTION, output, "Fetching connection state", false);

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
                    String ipv6 = result.getString("ipv6");
                    String state = result.getString("state");
                    int bandwidth_up = result.getInt("bandwidth_up");
                    int bandwidth_down = result.getInt("bandwidth_down");

                    downloadChartPanel.addDataValue(rateDown / 1000.0, bandwidth_down / 8000.0);
                    uploadChartPanel.addDataValue(rateUp / 1000.0, bandwidth_up / 8000.0);

                    ipv4ContentLabel.setText(ipv4);
                    ipv6ContentLabel.setText(ipv6);
                    connectionStateContentLabel.setText(state);
                    downloadContentLabel.setText(String.valueOf(rateDown / 1000.0) + " ko/s");
                    maxDownloadContentLabel.setText(String.valueOf(bandwidth_down / 8000.0) + " ko/s");
                    uploadContentLabel.setText(String.valueOf(rateUp / 1000.0) + " ko/s");
                    maxUploadContentLabel.setText(String.valueOf(bandwidth_up / 8000.0) + " ko/s");
                } else {
                    String msg = obj.getString("msg");
                    Log.Debug("Freebox Connection State", msg);
                }

                if (updater != null) {
                    updater.updated();
                }
            }
        });

        task.addListener(new HttpUtils.DownloadListener() {
            @Override
            public void onError(IOException ex) {
                Log.Debug("Freebox Connection State", ex.getMessage());

                if (updater != null) {
                    updater.updated();
                }
            }
        });
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Kevin Renella
        headerPanel = new JPanel();
        label1 = new JLabel();
        mainPanel = new JPanel();
        ipv4TitleLabel = new JLabel();
        ipv4ContentLabel = new JTextField();
        connectionStateTitleLabel = new JLabel();
        connectionStateContentLabel = new JTextField();
        downloadContentLabel = new JTextField();
        downloadTitleLabel = new JLabel();
        maxUploadTitleLabel = new JLabel();
        maxUploadContentLabel = new JTextField();
        maxDownloadTitleLabel = new JLabel();
        maxDownloadContentLabel = new JTextField();
        uploadTitleLabel = new JLabel();
        uploadContentLabel = new JTextField();
        ipv6ContentLabel = new JTextField();
        ipv6TitleLabel = new JLabel();

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

            //---- ipv4TitleLabel ----
            ipv4TitleLabel.setText("IP v4 :");
            ipv4TitleLabel.setHorizontalAlignment(SwingConstants.RIGHT);

            //---- ipv4ContentLabel ----
            ipv4ContentLabel.setEditable(false);
            ipv4ContentLabel.setText("192.168.0.1");
            ipv4ContentLabel.setHorizontalAlignment(SwingConstants.CENTER);

            //---- connectionStateTitleLabel ----
            connectionStateTitleLabel.setText("Connection State :");
            connectionStateTitleLabel.setHorizontalAlignment(SwingConstants.RIGHT);

            //---- connectionStateContentLabel ----
            connectionStateContentLabel.setEditable(false);
            connectionStateContentLabel.setText("up");
            connectionStateContentLabel.setHorizontalAlignment(SwingConstants.CENTER);

            //---- downloadContentLabel ----
            downloadContentLabel.setEditable(false);
            downloadContentLabel.setText("- ko/s");
            downloadContentLabel.setHorizontalAlignment(SwingConstants.CENTER);

            //---- downloadTitleLabel ----
            downloadTitleLabel.setText("Download Bandwidth :");
            downloadTitleLabel.setHorizontalAlignment(SwingConstants.RIGHT);

            //---- maxUploadTitleLabel ----
            maxUploadTitleLabel.setText("Max Upload Bandwidth :");
            maxUploadTitleLabel.setHorizontalAlignment(SwingConstants.RIGHT);

            //---- maxUploadContentLabel ----
            maxUploadContentLabel.setEditable(false);
            maxUploadContentLabel.setText("- ko/s");
            maxUploadContentLabel.setHorizontalAlignment(SwingConstants.CENTER);

            //---- maxDownloadTitleLabel ----
            maxDownloadTitleLabel.setText("Max Download Bandwidth :");
            maxDownloadTitleLabel.setHorizontalAlignment(SwingConstants.RIGHT);

            //---- maxDownloadContentLabel ----
            maxDownloadContentLabel.setEditable(false);
            maxDownloadContentLabel.setText("- ko/s");
            maxDownloadContentLabel.setHorizontalAlignment(SwingConstants.CENTER);

            //---- uploadTitleLabel ----
            uploadTitleLabel.setText("Upload Bandwidth :");
            uploadTitleLabel.setHorizontalAlignment(SwingConstants.RIGHT);

            //---- uploadContentLabel ----
            uploadContentLabel.setEditable(false);
            uploadContentLabel.setText("- ko/s");
            uploadContentLabel.setHorizontalAlignment(SwingConstants.CENTER);

            //---- ipv6ContentLabel ----
            ipv6ContentLabel.setEditable(false);
            ipv6ContentLabel.setText("::1:");
            ipv6ContentLabel.setHorizontalAlignment(SwingConstants.CENTER);

            //---- ipv6TitleLabel ----
            ipv6TitleLabel.setText("IP v6 :");
            ipv6TitleLabel.setHorizontalAlignment(SwingConstants.RIGHT);

            GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
            mainPanel.setLayout(mainPanelLayout);
            mainPanelLayout.setHorizontalGroup(
                mainPanelLayout.createParallelGroup()
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(connectionStateTitleLabel)
                            .addComponent(downloadTitleLabel)
                            .addComponent(ipv4TitleLabel)
                            .addComponent(maxUploadTitleLabel)
                            .addComponent(maxDownloadTitleLabel)
                            .addComponent(uploadTitleLabel)
                            .addComponent(ipv6TitleLabel))
                        .addGap(10, 10, 10)
                        .addGroup(mainPanelLayout.createParallelGroup()
                            .addComponent(ipv4ContentLabel, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                            .addComponent(connectionStateContentLabel, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                            .addComponent(downloadContentLabel, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                            .addComponent(maxUploadContentLabel, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                            .addComponent(maxDownloadContentLabel, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                            .addComponent(uploadContentLabel, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                            .addComponent(ipv6ContentLabel, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE))
                        .addContainerGap())
            );
            mainPanelLayout.setVerticalGroup(
                mainPanelLayout.createParallelGroup()
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(ipv4TitleLabel, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                            .addComponent(ipv4ContentLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(ipv6TitleLabel, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                            .addComponent(ipv6ContentLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(connectionStateTitleLabel, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                            .addComponent(connectionStateContentLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(uploadTitleLabel, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                            .addComponent(uploadContentLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(maxUploadTitleLabel, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                            .addComponent(maxUploadContentLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(downloadTitleLabel, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                            .addComponent(downloadContentLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(maxDownloadTitleLabel, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                            .addComponent(maxDownloadContentLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
    private JLabel ipv4TitleLabel;
    private JTextField ipv4ContentLabel;
    private JLabel connectionStateTitleLabel;
    private JTextField connectionStateContentLabel;
    private JTextField downloadContentLabel;
    private JLabel downloadTitleLabel;
    private JLabel maxUploadTitleLabel;
    private JTextField maxUploadContentLabel;
    private JLabel maxDownloadTitleLabel;
    private JTextField maxDownloadContentLabel;
    private JLabel uploadTitleLabel;
    private JTextField uploadContentLabel;
    private JTextField ipv6ContentLabel;
    private JLabel ipv6TitleLabel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
