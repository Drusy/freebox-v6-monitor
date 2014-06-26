package drusy.ui;

import aurelienribon.slidinglayout.SLAnimator;
import aurelienribon.slidinglayout.SLConfig;
import aurelienribon.slidinglayout.SLKeyframe;
import aurelienribon.slidinglayout.SLPanel;
import aurelienribon.ui.TaskPanel;
import aurelienribon.ui.components.Button;
import aurelienribon.ui.components.PaintedPanel;
import aurelienribon.ui.css.Style;
import aurelienribon.utils.Res;
import aurelienribon.utils.SwingUtils;
import drusy.utils.Checker;
import drusy.utils.Config;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import static aurelienribon.slidinglayout.SLSide.*;

public class MainPanel extends PaintedPanel {
	// Panels
    private final TaskPanel taskPanel = new TaskPanel();
    JFrame parentFrame;

    // WebView
    private JFXPanel webViewPanel = new JFXPanel();
    private WebView webView;

	// Start panel components
	private final JLabel startLogoLabel = new JLabel(Res.getImage("img/freebox-v6-monitor-logo.png"));
	private final Button startMonitorBtn = new Button() {{setText("Monitor");}};
	private final Button startWebviewBtn = new Button() {{setText("Webview");}};

	// Misc components
	private final Button changeModeBtn = new Button() {{setText("Back");}};

	// SlidingLayout
	private final SLPanel rootPanel = new SLPanel();
	private final float transitionDuration = 0.5f;
	private final int gap = 10;

	public MainPanel(JFrame frame) {
		SwingUtils.importFont(Res.getStream("fonts/SquareFont.ttf"));
		setLayout(new BorderLayout());
		add(rootPanel, BorderLayout.CENTER);
        parentFrame = frame;

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                webViewPanel = new JFXPanel();
                BorderPane borderPane = new BorderPane();
                webView = new WebView();
                borderPane.setCenter(webView);
                Scene scene = new Scene(borderPane,450,450);
                webViewPanel.setScene(scene);
                webView.getEngine().load(Config.FREEBOX_URL);
            }
        });
        Platform.setImplicitExit(false);

		initUI();
		initStyle();
		initConfigurations();
		rootPanel.initialize(initCfg);

		SLAnimator.start();
		rootPanel.setTweenManager(SLAnimator.createTweenManager());
		taskPanel.setTweenManager(SLAnimator.createTweenManager());

        SwingUtils.addWindowListener(this, new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
                Checker.CheckVersion();
                Checker.CheckFreeboxUp(parentFrame);
            }

			@Override
			public void windowClosing(WindowEvent e) {
			}
		});
	}

	private void initUI() {
		startMonitorBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMonitorView();
            }
        });

		startWebviewBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showWebView();
            }
        });

		changeModeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showInitView();
            }
        });
	}

	// -------------------------------------------------------------------------
	// Style
	// -------------------------------------------------------------------------

	private void initStyle() {
		Style.registerCssClasses(this, ".rootPanel");
		Style.registerCssClasses(startMonitorBtn, ".startButton");
		Style.registerCssClasses(startWebviewBtn, ".startButton");
		Style.registerCssClasses(changeModeBtn, ".bold", ".center");

		Component[] targets = new Component[] {
			this, taskPanel, changeModeBtn, startMonitorBtn, startWebviewBtn
		};

		Style style = new Style(Res.getUrl("css/style.css"));
		for (Component target : targets)
            Style.apply(target, style);
	}

	// -------------------------------------------------------------------------
	// Configurations
	// -------------------------------------------------------------------------
	private SLConfig initCfg, monitorCfg, webviewCfg;

	private void initConfigurations() {
		initCfg = new SLConfig(rootPanel)
			.gap(gap, gap)
			.row(1f).row(30).col(1f)
			.beginGrid(0, 0)
                .row(30)
				.row(startLogoLabel.getPreferredSize().height)
				.row(1f)
				.col(1f)
				.place(1, 0, startLogoLabel)
				.beginGrid(2, 0)
					.row(1f).row(1f).row(1f)
					.col(1f).col(2.5f).col(1f)
					.beginGrid(1, 1)
						.row(1f).col(1f).col(10).col(1f)
						.place(0, 0, startMonitorBtn)
						.place(0, 2, startWebviewBtn)
					.endGrid()
				.endGrid()
			.endGrid()
			.place(1, 0, taskPanel);

		monitorCfg = new SLConfig(rootPanel)
			.gap(gap, gap)
			.row(1f).row(30).col(1f)
			.beginGrid(1, 0)
				.row(1f).col(100).col(1f)
				.place(0, 0, changeModeBtn)
				.place(0, 1, taskPanel)
			.endGrid();

		webviewCfg = new SLConfig(rootPanel)
            .gap(gap, gap)
            .row(1f).row(30).col(1f)
            .place(0, 0, webViewPanel)
            .beginGrid(1, 0)
                .row(1f).col(100).col(1f)
                .place(0, 0, changeModeBtn)
                .place(0, 1, taskPanel)
            .endGrid();
	}

	public void showMonitorView() {
		rootPanel.createTransition()
			.push(new SLKeyframe(monitorCfg, transitionDuration)
				.setStartSideForNewCmps(RIGHT)
				.setStartSide(LEFT, changeModeBtn)
				.setEndSideForOldCmps(LEFT))
			.play();
	}

	public void showWebView() {
		rootPanel.createTransition()
			.push(new SLKeyframe(webviewCfg, transitionDuration)
				.setStartSideForNewCmps(RIGHT)
				.setStartSide(LEFT, changeModeBtn)
				.setEndSideForOldCmps(LEFT))
			.play();
	}

	public void showInitView() {
		rootPanel.createTransition()
			.push(new SLKeyframe(initCfg, transitionDuration)
				.setStartSideForNewCmps(LEFT)
				.setEndSideForOldCmps(RIGHT)
				.setEndSide(LEFT, changeModeBtn))
			.play();
	}
}
