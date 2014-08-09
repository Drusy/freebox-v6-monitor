package drusy.ui;

import aurelienribon.utils.Res;
import drusy.ui.panels.InternetStatePanel;
import drusy.ui.panels.SwitchStatePanel;
import drusy.ui.panels.WifiStatePanel;
import drusy.utils.Updater;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UpdateLabel extends JLabel implements Updater {
    private boolean enabled = true;
    private int counter;

    UpdateLabel(final InternetStatePanel internetStatePanel, final WifiStatePanel wifiStatePanel, final SwitchStatePanel switchStatePanel) {
        setAvailable();
        final Updater updater = this;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (enabled) {
                    setUnavailable();

                    counter = 3;

                    internetStatePanel.update(updater);
                    wifiStatePanel.update(updater);
                    switchStatePanel.update(updater);
                }
            }
        });
    }

    public void updated() {
        if (--counter <= 0) {
            setAvailable();
        }
    }

    private void setAvailable() {
        enabled = true;
        setText("Refresh information");
        setIcon(Res.getImage("img/ic_update.png"));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void setUnavailable() {
        enabled = false;
        setText("Refreshing ...");
        setIcon(Res.getImage("img/ic_loading.gif"));
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
}
