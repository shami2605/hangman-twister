// Name: Shamitha Naidu
// andrewid : shamithn
package hw3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class WordNerdModel {

	public static final String WORDS_FILE_NAME = "data\\wordsfile.txt";
	public static final String SCORE_FINAL_NAME = "data\\scores.csv";
	public static String[] wordsFromFile; // stores words read from the word file in this array
	public static String[] scoresFromFile;
	public static ObservableList<Score> scoreList = FXCollections.observableArrayList();

	public static void readWordsFile(String wordsFilename) {
		File file = new File(wordsFilename);
		StringBuilder fileData = new StringBuilder();
		String fileDataWithoutLinebreak = new String();

		// Loop through the fileDataInput & populate the fileData

		try {
			Scanner fileDataInput = new Scanner(file);
			while (fileDataInput.useDelimiter("\n").hasNext()) {
				fileData.append(fileDataInput.next() + "\n");
			}

			fileDataWithoutLinebreak = fileData.toString().replace("\n", "");
			fileDataWithoutLinebreak = fileDataWithoutLinebreak.replace("\r", "");

			wordsFromFile = fileData.toString().trim().split("\n");

			if (wordsFromFile.length == 1 && wordsFromFile[0].equalsIgnoreCase("")) {
				throw new InvalidWordSourceException("Check word source format!");
			} else if (!isAlphabestOnly(fileDataWithoutLinebreak)) {
				throw new InvalidWordSourceException("Check word source format!");
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * method checks if a string has only alphabets, if it finds any other character
	 * numeric or special, it returns false
	 */
	private static boolean isAlphabestOnly(String fileData) {
		if (fileData == null) {
			return false;
		}

		for (int i = 0; i < fileData.length(); i++) {
			char ch = fileData.charAt(i);
			if (!(ch >= 'A' && ch <= 'Z') && !(ch >= 'a' && ch <= 'z')) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Reads through the file to populate the String array & calls the fillScoreList
	 * function
	 */
	public static void readScore() {
		WordNerdModel wnm = new WordNerdModel();
		File file = new File(SCORE_FINAL_NAME);
		StringBuilder fileData = new StringBuilder();

		// Loop through the fileDataInput & populate the fileData

		try {
			Scanner fileDataInput = new Scanner(file);
			while (fileDataInput.useDelimiter("\n").hasNext()) {
				fileData.append(fileDataInput.next() + "\n");
			}

			scoresFromFile = fileData.toString().trim().split("\n");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		wnm.fillScoreList();
	}

	/**
	 * Used to fill the observable list through the string array scoresFromFile
	 */
	private void fillScoreList() {
		scoreList = FXCollections.observableArrayList();
		for (int i = 0; i < scoresFromFile.length; i++) {
			String scores[] = scoresFromFile[i].split(",");
			Score score = new Score(Integer.parseInt(scores[0].trim()), scores[1], Integer.parseInt(scores[2].trim()),
					Float.parseFloat(scores[3].trim()));

			scoreList.add(score);
		}

	}

	/**
	 * Used to write a String in the scores.csv file
	 */
	public static void writeScore(String scoreString) {

		try {

			FileWriter fw = new FileWriter(SCORE_FINAL_NAME, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(scoreString);
			bw.newLine();
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
