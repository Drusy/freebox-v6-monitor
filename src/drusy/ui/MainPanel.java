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
import aurelienribon.utils.VersionLabel;
import drusy.ui.panels.InternetStatePanel;
import drusy.ui.panels.WifiStatePanel;
import drusy.utils.Checker;
import drusy.utils.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static aurelienribon.slidinglayout.SLSide.*;

public class MainPanel extends PaintedPanel {
	// Panels
    private final TaskPanel taskPanel = new TaskPanel();
    private JFrame parentFrame;
    private InternetStatePanel internetStatePanel = new InternetStatePanel(this);
    private WifiStatePanel wifiStatePanel = new WifiStatePanel();

	// Start panel components
	private final JLabel startLogoLabel = new JLabel(Res.getImage("img/freebox-v6-monitor-logo.png"));
	private final Button startMonitorBtn = new Button() {{setText("Monitor");}};
	private final Button startWebviewBtn = new Button() {{setText("Webview");}};

	// Misc components
    private final UpdateLabel updateLabel = new UpdateLabel();
    private final VersionLabel versionLabel = new VersionLabel();
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

        versionLabel.initAndCheck(String.valueOf(Config.CURRENT_VERSION), Config.VERSION_DISTANT_FILE,
                Config.DOWNLOAD_URL);

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

        JLabel aboutLabel = new JLabel("About this app >");
        Style.registerCssClasses(aboutLabel, ".linkLabel");
        aboutLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        versionLabel.setLayout(new BorderLayout());
        versionLabel.add(aboutLabel, BorderLayout.EAST);

        aboutLabel.addMouseListener(new MouseAdapter() {
            @Override public void mousePressed(MouseEvent e) {
                //showAboutPanel();
            }
        });
	}

	// -------------------------------------------------------------------------
	// Style
	// -------------------------------------------------------------------------

	private void initStyle() {
		Style.registerCssClasses(this, ".rootPanel");
        Style.registerCssClasses(internetStatePanel, ".groupPanel", "#internetStatePanel");
        Style.registerCssClasses(wifiStatePanel, ".groupPanel", "#wifiStatePanel");
		Style.registerCssClasses(startMonitorBtn, ".startButton");
		Style.registerCssClasses(startWebviewBtn, ".startButton");
		Style.registerCssClasses(changeModeBtn, ".bold", ".center");
        Style.registerCssClasses(versionLabel, ".versionLabel");
        Style.registerCssClasses(updateLabel, ".versionLabel");

		Component[] targets = new Component[] {
			this, taskPanel, changeModeBtn, startMonitorBtn, startWebviewBtn, versionLabel, updateLabel,
                internetStatePanel, wifiStatePanel
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
            .beginGrid(0, 0)
                .row(1f).col(1f).col(1f).col(1f)
                .beginGrid(0, 0)
                    .row(internetStatePanel.getPreferredSize().height)
                    .row(versionLabel.getPreferredSize().height)
                    .row(updateLabel.getPreferredSize().height)
                    .col(1f)
                    .place(0, 0, internetStatePanel)
                    .place(1, 0, versionLabel)
                    .place(2, 0, updateLabel)
                .endGrid()
                .beginGrid(0, 1)
                    .row(wifiStatePanel.getPreferredSize().height)
                    .col(1f)
                    .place(0, 0, wifiStatePanel)
                .endGrid()
                .beginGrid(0, 2)
                    .row(1f)
                    .col(1f)
                .endGrid()
            .endGrid()
			.beginGrid(1, 0)
				.row(1f).col(100).col(1f)
				.place(0, 0, changeModeBtn)
				.place(0, 1, taskPanel)
			.endGrid();

		webviewCfg = new SLConfig(rootPanel)
            .gap(gap, gap)
            .row(1f).row(30).col(1f)
            .beginGrid(1, 0)
                .row(1f).col(100).col(1f)
                .place(0, 0, changeModeBtn)
                .place(0, 1, taskPanel)
            .endGrid();
	}

	public void showMonitorView() {
        internetStatePanel.updatePeriodically();
        wifiStatePanel.updatePeriodically();

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
