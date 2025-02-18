package de.esg.ausbildung.honl.game;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {

	static final String FILE_NAME = "Kartenspiel.csv";
	static final String USER_HOME = System.getProperty("user.home");
	static final String DIRECTORY = USER_HOME + File.separator + "JavaJack";
	static final String FILE_PATH_SAFE = Utils.createAndGetDirectory(Paths.get(DIRECTORY)) + File.separator + FILE_NAME;
	static final Path filePath = Paths.get(DIRECTORY, Constants.FILE_NAME);
}
