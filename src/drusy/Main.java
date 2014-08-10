package drusy;

import aurelienribon.ui.components.ArStyle;
import aurelienribon.ui.css.swing.SwingStyle;
import aurelienribon.utils.Res;
import drusy.ui.MainPanel;
import drusy.utils.Config;

import javax.swing.*;
import java.awt.*;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException ex) {
				} catch (InstantiationException ex) {
				} catch (IllegalAccessException ex) {
				} catch (UnsupportedLookAndFeelException ex) {
				}

				SwingStyle.init();
				ArStyle.init();

				JFrame frame = new JFrame("Freebox v6 Monitor");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setContentPane(new MainPanel(frame));
				frame.setSize(Config.APP_WITH, Config.APP_HEIGHT);
                frame.setMinimumSize(new Dimension(Config.APP_WITH, Config.APP_HEIGHT));
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);

                frame.setIconImage(Res.getImage("img/icon-monitor-64.png").getImage());
			}
		});
	}
}
