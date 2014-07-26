package drusy.ui.dialogs;

import aurelienribon.utils.DialogUtils;
import aurelienribon.utils.Res;
import com.sun.awt.AWTUtilities;
import drusy.ui.BackgroundPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WaitingGrantDialog extends JDialog {

    public WaitingGrantDialog(JFrame parent) {
        super(parent, true);

        setUndecorated(true);
        AWTUtilities.setWindowOpaque(this, false);

        setTitle("Freebox Grant");

        JLabel label = new JLabel("Grant needed,");
        label.setForeground(Color.WHITE);
        label.setFont(new Font(label.getFont().getFontName(), Font.PLAIN, 20));
        label.setAlignmentX(CENTER_ALIGNMENT);

        JLabel label2 = new JLabel("allow the app in the Freebox LCD display");
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font(label.getFont().getFontName(), Font.PLAIN, 20));
        label2.setAlignmentX(CENTER_ALIGNMENT);

        JLabel label3 = new JLabel("Waiting for user ...");
        label3.setForeground(Color.WHITE);
        label3.setFont(new Font(label.getFont().getFontName(), Font.PLAIN, 20));
        label3.setAlignmentX(CENTER_ALIGNMENT);

        BackgroundPanel panel = new BackgroundPanel(null, Res.getImage("img/bg.png").getImage());
        BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(layout);
        panel.add(Box.createVerticalGlue());
        panel.add(label);
        panel.add(label2);
        panel.add(Box.createVerticalGlue());
        panel.add(label3);
        panel.add(Box.createVerticalGlue());
        panel.setPreferredSize(new Dimension(400, 300));

        // the following two lines are only needed because there is no
        // focusable component in here, and the "hit espace to close" requires
        // the focus to be in the dialog. If you have a button, a textfield,
        // or any focusable stuff, you don't need these lines.
        panel.setFocusable(true);
        panel.requestFocusInWindow();

        getContentPane().add(panel);

        DialogUtils.createDialogBackPanel(this, parent.getContentPane());
        DialogUtils.addEscapeToCloseSupport(this, true);
    }
}
