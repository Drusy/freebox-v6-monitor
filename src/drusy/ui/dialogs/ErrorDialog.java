package drusy.ui.dialogs;

import aurelienribon.ui.css.Style;
import aurelienribon.utils.DialogUtils;
import aurelienribon.utils.Res;
import aurelienribon.utils.SwingUtils;
import com.sun.awt.AWTUtilities;
import drusy.ui.BackgroundPanel;

import javax.swing.*;
import java.awt.*;

public class ErrorDialog extends JDialog {

    public ErrorDialog(JFrame parent) {
        super(parent, true);

        setUndecorated(true);
        AWTUtilities.setWindowOpaque(this, false);

        setTitle("Freebox Error");

        JLabel label = new JLabel("Freebox disconnected ...");
        label.setForeground(Color.WHITE);
        label.setFont(new Font(label.getFont().getFontName(), Font.PLAIN, 20));

        BackgroundPanel panel = new BackgroundPanel(new GridBagLayout(), Res.getImage("img/bg.png").getImage());
        panel.add(label);
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
