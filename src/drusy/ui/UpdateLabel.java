package drusy.ui;

import aurelienribon.utils.Res;
import aurelienribon.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class UpdateLabel extends JLabel {
    UpdateLabel() {
        setText("Refresh information");
        setIcon(Res.getImage("img/ic_update.png"));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setIcon(Res.getImage("img/ic_loading.gif"));

                // TODO Update informations
            }
        });
    }
}
