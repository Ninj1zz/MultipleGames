package main.lib.display;

import java.awt.Image;
import java.io.Serial;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class ApplicationPanel extends JPanel {

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = -1039720643153996480L;

	private JLabel applicationName;
	private final String appName;
	private final int posX;
	private final int posY;
	private final int width;
	private final int height;

	public ApplicationPanel(int posX, int posY, int width, int height, String appName, Image img) {
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.appName = appName;
		init();

		this.add(new ImagePanel(img));

		applicationName.setBounds(0, height - 20, width - 50, 10);
		applicationName.setAlignmentX(CENTER_ALIGNMENT);
		this.add(applicationName);
	}

	private void init() {
		this.setBounds(posX, posY, width, height);
		this.setLayout(null);
		this.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		applicationName = new JLabel(appName);
		applicationName.setHorizontalAlignment(JLabel.CENTER);

	}

	public String getAppName() {
		return appName;
	}
}
