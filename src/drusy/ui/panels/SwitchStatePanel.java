/*
 * Created by JFormDesigner on Thu Aug 07 20:43:23 CEST 2014
 */

package drusy.ui.panels;

import aurelienribon.ui.css.Style;
import aurelienribon.utils.HttpUtils;
import aurelienribon.utils.Res;
import drusy.utils.Config;
import drusy.utils.Log;
import drusy.utils.Updater;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * @author Kevin Renella
 */
public class SwitchStatePanel extends JPanel {
    private java.util.List<JPanel> users = new ArrayList<JPanel>();
    private java.util.Timer timer;
    private WifiStatePanel wifiStatePanel;
    private Updater.FakeUpdater fakeUpdater = new Updater.FakeUpdater();

    public SwitchStatePanel(WifiStatePanel wifiStatePanel) {
        initComponents();

        setVisible(false);

        this.wifiStatePanel = wifiStatePanel;

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
        }, 1000, delay);
    }

    public void update(final Updater updater) {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        HttpUtils.DownloadGetTask task = HttpUtils.downloadGetAsync(Config.FREEBOX_API_SWITCH_STATUS, output, "Getting Switch status", false);

        task.addListener(new HttpUtils.DownloadListener() {
            @Override public void onComplete() {
                String json = output.toString();
                JSONObject obj = new JSONObject(json);
                boolean success = obj.getBoolean("success");

                clearUsers();
                if (success == true) {
                    JSONArray switchStatusArray = obj.getJSONArray("result");

                    for (int i = 0; i < switchStatusArray.length(); ++i) {
                        JSONObject switchStatus = switchStatusArray.getJSONObject(i);
                        String status = switchStatus.getString("link");
                        int id = switchStatus.getInt("id");

                        if (status.equals("up")) {
                            JSONArray switchMacArray = switchStatus.getJSONArray("mac_list");
                            JSONObject switchMac = switchMacArray.getJSONObject(i);
                            String hostname = switchMac.getString("hostname");
                            addUsersForSwitchIdAndHostname(id, hostname);
                        }
                    }
                } else {
                    String msg = obj.getString("msg");
                    Log.Debug("Freebox Switch status", msg);
                }

                if (updater != null) {
                    updater.updated();
                }
            }
        });

        task.addListener(new HttpUtils.DownloadListener() {
            @Override
            public void onError(IOException ex) {
                Log.Debug("Freebox Switch status", ex.getMessage());

                if (updater != null) {
                    updater.updated();
                }
            }
        });
    }

    public void addUsersForSwitchIdAndHostname(int id, final String hostname) {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        String statement = Config.FREEBOX_API_SWITCH_ID.replace("{id}", String.valueOf(id));
        HttpUtils.DownloadGetTask task = HttpUtils.downloadGetAsync(statement, output, "Getting Switch information", false);

        task.addListener(new HttpUtils.DownloadListener() {
            @Override public void onComplete() {
                String json = output.toString();
                JSONObject obj = new JSONObject(json);
                boolean success = obj.getBoolean("success");

                if (success == true) {
                    JSONObject result = obj.getJSONObject("result");
                    long txBytes = result.getLong("tx_bytes_rate");
                    long rxBytes = result.getLong("rx_bytes_rate");

                    addUser("", hostname, txBytes, rxBytes);
                } else {
                    String msg = obj.getString("msg");
                    Log.Debug("Freebox Switch information", msg);
                }

                setVisible(true);
            }
        });

        task.addListener(new HttpUtils.DownloadListener() {
            @Override
            public void onError(IOException ex) {
                Log.Debug("Freebox Switch information", ex.getMessage());
            }
        });
    }

    private void addUser(String hostName, String information, long txBytes, long rxBytes) {
        JPanel panel = new JPanel();
        JLabel informationLabel = new JLabel();
        ImageIcon imageIcon;

        //======== panel1 ========
        {
            panel.setLayout(new BorderLayout());
            panel.setBorder(new EmptyBorder(0, 10, 0, 10));

            //---- label2 ----
            if (hostName.equals("smartphone")) {
                imageIcon = Res.getImage("img/iphone-56.png");
            } else {
                imageIcon = Res.getImage("img/mac-56.png");
            }
            informationLabel.setIcon(imageIcon);
            informationLabel.setText("<html><b>" + information + "</b><br />Download: " + txBytes / 1000.0 + " ko/s <br />Upload: " + rxBytes / 1000.0 + " ko/s</html>");
            informationLabel.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(informationLabel, BorderLayout.CENTER);
        }
        mainPanel.add(panel);
        adaptPanelSize();

        users.add(panel);
    }

    private void adaptPanelSize() {
        setSize(getWidth(), (int) getPreferredSize().getHeight());
        setLocation(getX(), wifiStatePanel.getY() + wifiStatePanel.getHeight() + Config.GAP);
        validate();
        repaint();
    }

    private void clearUsers() {
        for (JPanel user : users) {
            mainPanel.remove(user);
        }

        users.clear();
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
            label1.setText("This panel shows you the users connected on the Switch");
            label1.setHorizontalAlignment(SwingConstants.CENTER);
            headerPanel.add(label1, BorderLayout.CENTER);
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
