package main.lib.application;

import java.io.File;
import java.io.IOException;

import main.lib.display.Frame;

public class Application extends Thread {

	private final File file;
	private final String name;
	private boolean isRunning = false;
	private Process process;

	public Application(String name, File file) {
		this.name = name;
		this.file = file;
	}

	public void run() {
		open();
		isRunning = true;
		while (isRunning) {
			if (!process.isAlive()) {
				this.destroy();
			}
		}
	}

	public void open() {
		try {
			this.setName(name);
			Runtime runTime = Runtime.getRuntime();
			process = runTime.exec("java -jar " + file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		isRunning = false;
		process.destroy();
		Frame.appMan.load();
		Frame.lblCurrentSelection.setText("Currently selected: ");
		Frame.lblCurrentStatus.setText("Status: ");
	}

	/* Getter and Setter Methods */

	public String getAppName() {
		return name;
	}

	public boolean isRunning() {
		return !isRunning;
	}
}
