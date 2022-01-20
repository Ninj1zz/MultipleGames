package main.lib.application;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ApplicationManager {
	private List<Application> applicationList;

	public ApplicationManager() {
		File dataFolder = new File("data");
		if (!dataFolder.exists()) {
			dataFolder.mkdir();
		}
		load();
	}

	public void load() {
		applicationList = new ArrayList<>();
		File[] fileList = new File("data").listFiles();
		assert fileList != null;
		for (File f : fileList) {
			if (f.isDirectory()) {
				for(File f1 : Objects.requireNonNull(f.listFiles())){
					if(f1.getName().contains(".jar")){
						String name = f.getName().replace(".jar", "");
						applicationList.add(new Application(name, f1));
					}

				}

			}

		}
	}

	public void addApplication(Application a) {
		applicationList.add(a);
		sortApplicationList();
	}

	public List<Application> getApplicationList() {
		return applicationList;
	}

	private void sortApplicationList() {
		applicationList.sort(Comparator.comparing(Application::getAppName));
	}

}
