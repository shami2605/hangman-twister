// Name: Shamitha Naidu
// andrewid : shamithn
package hw3;

import java.util.Formatter;
import java.util.concurrent.ThreadLocalRandom;

public class Hangman extends Game {
	static final int MIN_WORD_LENGTH = 5; // minimum length of puzzle word
	static final int MAX_WORD_LENGTH = 10; // maximum length of puzzle word
	static final int HANGMAN_TRIALS = 10; // max number of trials in a game
	static final int HANGMAN_GAME_TIME = 30; // max time in seconds for one round of game
	static int TRIALS = 0; // keeps the count of the trial number
	static int numberOfHits = 0;
	static int numberOfMisses = 0;
	HangmanRound hangmanRound;

	/**
	 * setupRound() is a replacement of findPuzzleWord() in HW1. It returns a new
	 * HangmanRound instance with puzzleWord initialized randomly drawn from
	 * wordsFromFile. The puzzleWord must be a word between HANGMAN_MIN_WORD_LENGTH
	 * and HANGMAN_MAX_WORD_LEGTH. Other properties of Hangmanround are also
	 * initialized here.
	 */
	@Override
	HangmanRound setupRound() {

		String[] wordsFromFile = WordNerdModel.wordsFromFile;
		String puzzleWord = null;
		numberOfHits = 0;
		numberOfMisses = 0;

		while (true) {
			puzzleWord = wordsFromFile[ThreadLocalRandom.current().nextInt(0, wordsFromFile.length)];
			if (MIN_WORD_LENGTH <= puzzleWord.replaceAll("\r", "").length()
					&& puzzleWord.replaceAll("\r", "").length() <= MAX_WORD_LENGTH) {
				puzzleWord = puzzleWord.replaceAll("\r", "");
				break;
			}

		}

		hangmanRound = new HangmanRound();
		hangmanRound.setPuzzleWord(puzzleWord);
		hangmanRound.setClueWord(makeAClue(puzzleWord));
		hangmanRound.setMissCount(0);
		hangmanRound.setHitCount(0);
		TRIALS = 0;
		hangmanRound.setIsRoundComplete(false);

		return hangmanRound;

	}

	/**
	 * Returns a clue that has at least half the number of letters in puzzleWord
	 * replaced with dashes. The replacement should stop as soon as number of dashes
	 * equals or exceeds 50% of total word length. Note that repeating letters will
	 * need to be replaced together. For example, in 'apple', if replacing p, then
	 * both 'p's need to be replaced to make it a--le
	 */
	@Override
	String makeAClue(String puzzleWord) {
		int dashes;

		// The number of dashes required is computed according to the length of the
		// puzzleword
		if (puzzleWord.length() % 2 == 0) {
			dashes = puzzleWord.length() / 2 - 1;
		} else {
			dashes = puzzleWord.length() / 2;
		}

		StringBuilder sbreplaced = new StringBuilder(puzzleWord);
		StringBuilder randomNumberused = new StringBuilder();
		int dash = 0;

		// Word is looped through until letters are replaced by at least above computed
		// amount of dashes
		while (dash <= dashes) {
			int random = ThreadLocalRandom.current().nextInt(0, puzzleWord.length());
			while (randomNumberused.toString().contains(String.valueOf(random))) {
				random = ThreadLocalRandom.current().nextInt(0, puzzleWord.length());
			}
			StringBuilder repeatedLetterIndex = new StringBuilder(puzzleWord.length());
			repeatedLetterIndex.append(random);
			for (int i = 0; i < puzzleWord.length(); i++) {
				if (puzzleWord.charAt(i) == puzzleWord.charAt(random) && i != random) {
					repeatedLetterIndex.append(i);
				}
			}

			randomNumberused.append(repeatedLetterIndex);

			for (char c : repeatedLetterIndex.toString().toCharArray()) {
				int a = Integer.parseInt(c + "");
				sbreplaced.setCharAt(a, '_');
				dash++;
			}

		}

		return sbreplaced.toString();

	}

	/** countDashes() returns the number of dashes in a clue String */
	int countDashes(String word) {
		int count = 0;
		for (char c : word.toCharArray()) {

			if (c == '_') {
				count++;
			}

		}
		return count;
	}

	/**
	 * getScoreString() returns a formatted String with calculated score to be
	 * displayed after each trial in Hangman. See the handout and the video clips
	 * for specific format of the string.
	 */
	@Override
	String getScoreString() {
		double hits = hangmanRound.getHitCount();
		double misses = hangmanRound.getMissCount();
		double score = hits;
		if (misses == 0) {
			score = hits;
		} else {
			score = hits / misses;

		}
		Formatter formatter = new Formatter();
		String output = "Hit: " + hangmanRound.getHitCount() + " " + "Miss: " + hangmanRound.getMissCount()
				+ ". Score: " + formatter.format("%.2f", score);
		return output;
	}

	/**
	 * nextTry() takes next guess and updates hitCount, missCount, and clueWord in
	 * hangmanRound. Returns INDEX for one of the images defined in GameView
	 * (SMILEY_INDEX, THUMBS_UP_INDEX...etc. The key change from HW1 is that because
	 * the keyboardButtons will be disabled after the player clicks on them, there
	 * is no need to track the previous guesses made in userInputs
	 */
	@Override
	int nextTry(String guess) {
		TRIALS++;

		// If the puzzleword doesn't contain the guessed character we return
		// THUMBS_DOWN_INDEX
		// If the puzzleword doesn't contain the guessed character & this is the last
		// guess, then we return SADLY_INDEX
		// In the else if loop we check if puzzleword contains the guessed character &
		// the complete word is guessed, we return SMILEY_INDEX
		// As a default we return THUMBS_UP_INDEX, if any of the cases above are not
		// satisfied

		if (!hangmanRound.getPuzzleWord().contains(guess + "")) {

			int miss = hangmanRound.getMissCount();
			hangmanRound.setMissCount(miss + 1);
			numberOfMisses++;
			if (TRIALS == HANGMAN_TRIALS) {
				return GameView.SADLY_INDEX;
			}
			return GameView.THUMBS_DOWN_INDEX;
		} else if (hangmanRound.getPuzzleWord().contains(guess + "")) {

			int hit = hangmanRound.getHitCount();
			hangmanRound.setHitCount(hit + 1);
			numberOfHits++;
			StringBuilder cluewordupdated = new StringBuilder(hangmanRound.getClueWord());
			for (int i = 0; i < hangmanRound.getPuzzleWord().length(); i++) {
				if ((hangmanRound.getPuzzleWord().charAt(i) + "").equalsIgnoreCase(guess)) {
					cluewordupdated.setCharAt(i, guess.charAt(0));
				}
			}

			hangmanRound.setClueWord(cluewordupdated.toString());
			if (!cluewordupdated.toString().contains("_")) {
				hangmanRound.setIsRoundComplete(true);
				return GameView.SMILEY_INDEX;
			}

		}

		if (TRIALS == HANGMAN_TRIALS) {
			return GameView.SADLY_INDEX;
		}
		return GameView.THUMBS_UP_INDEX;

	}
}
