/*
 * Created by JFormDesigner on Mon Aug 04 21:13:44 CEST 2014
 */

package drusy.ui.panels;

import javax.swing.border.*;
import aurelienribon.ui.css.Style;
import aurelienribon.utils.HttpUtils;
import aurelienribon.utils.Res;
import drusy.utils.Config;
import drusy.utils.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;
import javax.swing.*;

/**
 * @author Kevin Renella
 */
public class WifiStatePanel extends JPanel {
    private java.util.List<JPanel> users = new ArrayList<JPanel>();
    private java.util.Timer timer;

    public WifiStatePanel() {
        initComponents();

        Style.registerCssClasses(headerPanel, ".header");
    }

    Component[] getUsersComponents() {
        return mainPanel.getComponents();
    }

    public void updatePeriodically() {
        long delay = 1000 * 2;

        timer = new java.util.Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                update();
            }
        }, 500, delay);
    }

    public void update() {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        HttpUtils.DownloadGetTask task = HttpUtils.downloadGetAsync(Config.FREEBOX_API_WIFI_ID, output, "Getting WiFi id", false);

        task.addListener(new HttpUtils.DownloadListener() {
            @Override public void onComplete() {
                String json = output.toString();
                JSONObject obj = new JSONObject(json);
                boolean success = obj.getBoolean("success");

                if (success == true) {
                    JSONArray result = obj.getJSONArray("result");
                    JSONObject wifi = result.getJSONObject(0);
                    int id = wifi.getInt("id");

                    addUsersForWifiId(id);

                } else {
                    String msg = obj.getString("msg");
                    Log.Debug("Freebox Wi-Fi State (get id)", msg);
                }
            }
        });

        task.addListener(new HttpUtils.DownloadListener() {
            @Override
            public void onError(IOException ex) {
                Log.Debug("Freebox Wi-Fi State (get id)", ex.getMessage());
            }
        });
    }

    public void addUsersForWifiId(int id) {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        String statement = Config.FREEBOX_API_WIFI_STATIONS.replace("{id}", String.valueOf(id));
        HttpUtils.DownloadGetTask task = HttpUtils.downloadGetAsync(statement, output, "Getting WiFi id", false);

        task.addListener(new HttpUtils.DownloadListener() {
            @Override public void onComplete() {
                String json = output.toString();
                JSONObject obj = new JSONObject(json);
                boolean success = obj.getBoolean("success");

                clearUsers();
                if (success == true) {
                    final JSONArray usersList = obj.getJSONArray("result");

                    for (int i = 0; i < usersList.length(); ++i) {
                        JSONObject user = usersList.getJSONObject(i);
                        final String hostname = user.getString("hostname");
                        final long txBytes = user.getLong("tx_rate");
                        final long rxBytes = user.getLong("rx_rate");
                        JSONObject host = user.getJSONObject("host");
                        final String host_type = host.getString("host_type");

                        addUser(host_type, hostname, txBytes, rxBytes);
                    }
                } else {
                    String msg = obj.getString("msg");
                    Log.Debug("Freebox Wi-Fi State (get users)", msg);
                }
            }
        });

        task.addListener(new HttpUtils.DownloadListener() {
            @Override
            public void onError(IOException ex) {
                Log.Debug("Freebox Wi-Fi State (get users)", ex.getMessage());
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
        mainPanel.setSize(mainPanel.getWidth(), (int)mainPanel.getPreferredSize().getHeight());
        setSize(getWidth(), (int)getPreferredSize().getHeight());
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
            label1.setText("This panel shows you the users connected on the Wi-Fi");
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
