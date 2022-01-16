// Name: Shamitha Naidu
// andrewid : shamithn
package hw3;

import java.util.Formatter;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

public class Score {

	IntegerProperty gameId = new SimpleIntegerProperty();
	StringProperty puzzleWord = new SimpleStringProperty();
	IntegerProperty timeStamp = new SimpleIntegerProperty();
	FloatProperty score = new SimpleFloatProperty();
	StringProperty game = new SimpleStringProperty();
	StringProperty scoreFormatted = new SimpleStringProperty();

	public String getScoreFormatted() {
		return scoreFormatted.get();
	}

	public void setScoreFormatted(String scoreFormatted) {
		this.scoreFormatted.set(scoreFormatted);
	}

	public String getGame() {
		return game.get();
	}

	public void setGame(String game) {
		this.game.set(game);
	}

	public Score(int gameId, String puzzleWord, int timeStamp, float score) {
		super();
		this.gameId.set(gameId);
		this.puzzleWord.set(puzzleWord);
		this.timeStamp.set(timeStamp);
		this.score.set(score);
	}

	public Score() {
		super();
	}

	public int getGameId() {
		return gameId.get();
	}

	public void setGameId(int gameId) {
		this.gameId.set(gameId);
	}

	public String getPuzzleWord() {
		return puzzleWord.get();
	}

	public void setPuzzleWord(String puzzleWord) {
		this.puzzleWord.set(puzzleWord);
	}

	public int getTimeStamp() {
		return timeStamp.get();
	}

	public void setTimeStamp(int timeStamp) {
		this.timeStamp.set(timeStamp);
	}

	public float getScore() {
		return score.get();
	}

	public void setScore(float score) {
		this.score.set(score);
	}

	/**
	 * gameNameProperty function is called to get the suitable game name by checking
	 * the game id
	 */
	public ObservableValue<String> gameNameProperty() {
		if (this.getGameId() == 0) {
			setGame("Hangman");
			return this.game;
		} else {
			setGame("Twister");
			return this.game;
		}
	}

	/**
	 * gameScoreFormatttedProperty function is called to get the score in a properly
	 * formatted way
	 */
	public ObservableValue<String> gameScoreFormatttedProperty() {
		Formatter formatter = new Formatter();
		scoreFormatted.set("" + formatter.format("%.2f", getScore()));
		return this.scoreFormatted;

	}

}
