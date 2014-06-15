package drusy;

import aurelienribon.ui.components.ArStyle;
import aurelienribon.ui.css.swing.SwingStyle;
import drusy.ui.MainPanel;

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
				frame.setContentPane(new MainPanel());
				frame.setSize(1100, 600);
                frame.setMinimumSize(new Dimension(1100, 600));
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}
}
