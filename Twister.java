// Name: Shamitha Naidu
// andrewid : shamithn
package hw3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Twister extends Game {

	public static final int SOLUTION_LIST_COUNT = 5;
	public static final int TWISTER_GAME_TIME = 120;
	public static final int TWISTER_MAX_WORD_LENGTH = 7;
	public static final int TWISTER_MIN_WORD_LENGTH = 3;
	public static final int NEW_WORD_BUTTON_INDEX = 0;
	public static final int TWIST_BUTTON_INDEX = 1;
	public static final int CLEAR_BUTTON_INDEX = 1;
	public static final int SUBMIT_BUTTON_INDEX = 1;
	public static final int CLUE_BUTTON_INDEX = 75;
	public static final int MIN_SOLUTION_WORDCOUNT = 10;

	TwisterRound twisterRound;

	/**
	 * setupRound() returns a new TwisterRound instance with puzzleWord initialized
	 * randomly drawn from wordsFromFile. The puzzleWord must be a word between
	 * TWISTER_MIN_WORD_LENGTH and TWISTER_MAX_WORD_LENGTH. It should also have at
	 * minimum MIN_SOLUTION_WORDCOUNT words twisted out of it. Other properties of
	 * TwisterRound are also initialized here.
	 */
	TwisterRound setupRound() {
		String[] wordsFromFile = WordNerdModel.wordsFromFile;
		String puzzleWord = null;
		twisterRound = new TwisterRound();

		while (true) {
			puzzleWord = wordsFromFile[ThreadLocalRandom.current().nextInt(0, wordsFromFile.length)];
			if (TWISTER_MIN_WORD_LENGTH <= puzzleWord.replaceAll("\r", "").length()
					&& puzzleWord.replaceAll("\r", "").length() <= TWISTER_MAX_WORD_LENGTH) {
				puzzleWord = puzzleWord.replaceAll("\r", "");

				List<String> twists = totalTwists(puzzleWord, wordsFromFile, twisterRound);
				if (twists != null) {
					twisterRound.setSolutionWordsList(twists);
					break;
				}
			}

		}
		createLists(twisterRound);
		twisterRound.setPuzzleWord(puzzleWord);
		twisterRound.setClueWord(makeAClue(puzzleWord));
		return twisterRound;

	}

	/**
	 * createLists is a private method which iterates through all words in
	 * solutionWordsList and adds it in the List of lists variable
	 * solutionListsByWordLength
	 */
	private void createLists(TwisterRound twisterRound) {

		for (String word : twisterRound.getSolutionWordsList()) {
			twisterRound.setSolutionListsByWordLength(word);
		}

	}

	/**
	 * Takes puzzleWord as an input and returns the shuffled version of the same
	 * word as a clue
	 */
	String makeAClue(String puzzleWord) {
		List<String> letters = Arrays.asList(puzzleWord.split(""));
		Collections.shuffle(letters);
		String shuffledWord = "";
		for (String letter : letters) {
			shuffledWord += letter;
		}
		return shuffledWord;
	}

	/**
	 * nextTry() takes next guess and updates the submittedListByWordLength variable
	 * in twisterRound if its a right guess. If the guess is not in the solution
	 * list it accordingly returns appropriate index. It returns INDEX for one of
	 * the images defined in GameView SMILEY_INDEX, THUMBS_UP_INDEX...etc.
	 */
	int nextTry(String guess) {
		int submittedIndexMaybe = guess.length() - TWISTER_MIN_WORD_LENGTH;
		boolean isInSolutionList = false;
		boolean isInSubmittedList = false;
		for (String solution : twisterRound.getSolutionWordsList()) {
			if (solution.equalsIgnoreCase(guess)) {
				isInSolutionList = true;
				break;
			}

		}

		if (!isInSolutionList) {
			return GameView.THUMBS_DOWN_INDEX;
		}

		for (String submitted : twisterRound.getSubmittedListsByWordLength(submittedIndexMaybe)) {
			if (submitted.equalsIgnoreCase(guess)) {
				isInSubmittedList = true;
			}
		}

		if (isInSolutionList && isInSubmittedList) {
			return GameView.REPEAT_INDEX;
		} else if (isInSolutionList && !isInSubmittedList) {
			twisterRound.setSubmittedListsByWordLength(guess);
			int totalSolutionWords = twisterRound.getSolutionWordsList().size();
			int submittedWords = 0;
			for (List<String> s : twisterRound.getSubmittedListsByWordLength()) {
				submittedWords = submittedWords + s.size();
			}
			if (totalSolutionWords == submittedWords) {
				twisterRound.setIsRoundComplete(true);
				return GameView.SMILEY_INDEX;
			}
		}
		return GameView.THUMBS_UP_INDEX;
	}

	/**
	 * getScoreString() returns the string to be displayed right at the top of the
	 * twister game. After every user guess, it updates the string by finding out
	 * latest number of words which still have not been guessed & appending it to
	 * the String.
	 */
	String getScoreString() {
		if (twisterRound.getSubmittedListsByWordLength().isEmpty()) {
			return "Twist to find" + twisterRound.getSolutionWordsList().size() + " words";
		} else {
			int submittedWords = 0;
			int solutionWords = twisterRound.getSolutionWordsList().size();
			for (int i = 0; i < twisterRound.getSubmittedListsByWordLength().size(); i++) {
				submittedWords = submittedWords + twisterRound.getSubmittedListsByWordLength(i).size();
			}
			return "Twist to find " + (solutionWords - submittedWords) + " of " + solutionWords + " words";
		}

	}

	/**
	 * totalTwists() is a private method which takes a puzzleword as an input &
	 * finds all the solution words for the word, if the number of solution words
	 * are more than MIN_SOLUTION_WORDCOUNT it returns the solution list, else it
	 * returns null
	 */
	private List<String> totalTwists(String puzzleWord, String[] wordsFromFile, TwisterRound twisterRound) {

		int totalTwists = 0;
		List<String> tempSolutions = null;
		tempSolutions = new ArrayList<String>();
		for (String s : wordsFromFile) {
			s = s.replace("\r", "");
			if (s.length() >= TWISTER_MIN_WORD_LENGTH & s.length() <= puzzleWord.length()) {
				int count = 0;
				StringBuilder copyOfPuzzlleword = new StringBuilder(puzzleWord);
				for (char c : s.toCharArray()) {
					if (copyOfPuzzlleword.toString().contains(c + "")) {
						count++;
						for (int i = 0; i < copyOfPuzzlleword.length(); i++) {
							if ((copyOfPuzzlleword.charAt(i) + "").equalsIgnoreCase(c + "")) {
								int removeAt = i;
								copyOfPuzzlleword.deleteCharAt(i);
								break;
							}
						}
					} else {
						break;
					}
				}
				if (count == s.length()) {
					totalTwists++;
					tempSolutions.add(s);
				}
			}
		}
		if (totalTwists < MIN_SOLUTION_WORDCOUNT) {
			return null;
		}
		return tempSolutions;

	}

}
