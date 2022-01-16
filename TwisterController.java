// Name: Shamitha Naidu
// andrewid : shamithn
package hw3;

import java.util.Collections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class TwisterController extends WordNerdController {

	TwisterView twisterView;
	Twister twister;

	/**
	 * startController() creates new Twister, TwisterView, invokes setupRound() to
	 * create a new twisterRound, refreshes the view for next round, and invokes
	 * setupBindings to bind the new TwisterRound properties with GUI components.
	 */
	@Override
	void startController() {
		twisterView = new TwisterView();
		twister = new Twister();
		twister.twisterRound = twister.setupRound();
		twisterView.refreshGameRoundView(twister.twisterRound);
		setupBindings();

		VBox lowerPanel = new VBox();
		lowerPanel.getChildren().add(twisterView.bottomGrid);
		lowerPanel.getChildren().add(WordNerd.exitButton);
		lowerPanel.setAlignment(Pos.CENTER);

		WordNerd.root.setTop(twisterView.topMessageText);
		WordNerd.root.setCenter(twisterView.topGrid);
		WordNerd.root.setBottom(lowerPanel);

	}

	/**
	 * setupRoundBindings binds GUI components with twisterRound properties and the
	 * timer to change clue labels, change smiley button, disable clueGrid and
	 * keyboardGrid
	 */
	@Override
	void setupBindings() {

		// Bind a listener to twisterRounds clueWordProperty
		// so that whenever it changes, the clueLabels should also
		// change in twisterView
		twister.twisterRound.clueWordProperty().addListener((observable, oldValue, newValue) -> {
			for (int i = 0; i < twister.twisterRound.getClueWord().length(); i++) {
				twisterView.clueButtons[i].setText(String.format("%s", newValue.charAt(i)));
			}
		});
		// When timer runs out, set smiley to sadly, isRoundComplete to true
		GameView.wordTimer.timeline.setOnFinished(event -> {
			twisterView.smileyButton.setGraphic(twisterView.smileyImageViews[GameView.SADLY_INDEX]);
			double solutionWords = twister.twisterRound.getSolutionWordsList().size();
			double submittedWords = 0;
			for (int i = 0; i < twister.twisterRound.getSubmittedListsByWordLength().size(); i++) {
				submittedWords = submittedWords + twister.twisterRound.getSubmittedListsByWordLength(i).size();
			}

			double score = (double) submittedWords / solutionWords;
			String scoreString = "1," + twister.twisterRound.getPuzzleWord() + "," + Twister.TWISTER_GAME_TIME + ","
					+ String.format("%.6f", score);
			WordNerdModel.writeScore(scoreString);
			twister.twisterRound.setIsRoundComplete(true);

		});

		for (int i = 0; i < twisterView.clueButtons.length; i++) {
			twisterView.clueButtons[i].setOnAction(new ClueButtonHandler());
		}

		for (int i = 0; i < twisterView.answerButtons.length; i++) {
			twisterView.answerButtons[i].setOnAction(new AnswerButtonHandler());
		}

		twisterView.clueGrid.disableProperty().bind(twister.twisterRound.isRoundCompleteProperty());
		twisterView.playButtons[1].disableProperty().bind(twister.twisterRound.isRoundCompleteProperty());
		twisterView.playButtons[2].disableProperty().bind(twister.twisterRound.isRoundCompleteProperty());
		twisterView.playButtons[3].disableProperty().bind(twister.twisterRound.isRoundCompleteProperty());

		// Bind the New, Twist, Clear & Submit buttons to respective handlers

		twisterView.playButtons[0].setOnAction(new NewButtonHandler());
		twisterView.playButtons[1].setOnAction(new TwistButtonHandler());
		twisterView.playButtons[2].setOnAction(new ClearButtonHandler());
		twisterView.playButtons[3].setOnAction(new SubmitButtonHandler());

	}

	/**
	 * TwistButtonHandler Invokes makeAClue method to get a twisted clue clears the
	 * older clue from the clue button & stores the new one
	 */
	class TwistButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < twisterView.clueButtons.length; i++) {
				if (!twisterView.clueButtons[i].getText().isEmpty()) {
					sb.append(twisterView.clueButtons[i].getText());
				}
			}
			String newClue = twister.makeAClue(sb.toString());

			for (int i = 0; i < twisterView.clueButtons.length; i++) {
				if (!twisterView.clueButtons[i].getText().isEmpty()) {
					twisterView.clueButtons[i].setText("");
				}
			}

			for (int i = 0; i < newClue.length(); i++) {
				twisterView.clueButtons[i].setText(newClue.charAt(i) + "");
			}

		}

	}

	/**
	 * ClearButtonHandler clears the text from the answerButtons & pushes it back to
	 * the clueButtons
	 */
	class ClearButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < twisterView.answerButtons.length; i++) {
				sb.append(twisterView.answerButtons[i].getText());
				twisterView.answerButtons[i].setText("");
			}

			String letters = sb.toString();
			int j = 0;
			for (int i = 0; i < twisterView.clueButtons.length; i++) {
				if (twisterView.clueButtons[i].getText().isBlank()) {
					twisterView.clueButtons[i].setText(letters.charAt(j) + "");
					j++;
				}
			}

		}

	}

	/**
	 * ClueButtonHandler takes clicked letter from clueButton & pushes it to the
	 * next empty slot from left in the answerButtons
	 */
	class ClueButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			Button clue = (Button) event.getSource();
			String a = clue.getText();
			clue.setText("");
			for (int i = 0; i < twisterView.answerButtons.length; i++) {
				if (twisterView.answerButtons[i].getText().isBlank()) {
					twisterView.answerButtons[i].setText(a);
					break;
				}
			}

		}

	}

	/**
	 * AnswerButtonHandler takes clicked letter from answerButtons & pushes it to
	 * the next empty slot from left in the clueButtons
	 */
	class AnswerButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			Button clue = (Button) event.getSource();
			String a = clue.getText();
			clue.setText("");
			for (int i = 0; i < twisterView.clueButtons.length; i++) {
				if (twisterView.clueButtons[i].getText().isBlank()) {
					twisterView.clueButtons[i].setText(a);
					break;
				}
			}

		}

	}

	/**
	 * SubmitButtonHandler takes the submitted word from answerButtons & clears the
	 * answer button whenever the guess is correct, it then displays an image from
	 * GameView according to the submitted word, it also updates the String on top
	 * of the Game by calling the getScoreString method
	 */
	class SubmitButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < twisterView.answerButtons.length; i++) {
				sb.append(twisterView.answerButtons[i].getText());
			}
			if (sb.length() < Twister.TWISTER_MIN_WORD_LENGTH) {
				twisterView.smileyButton.setGraphic(twisterView.smileyImageViews[GameView.THUMBS_DOWN_INDEX]);
			}

			int index = twister.nextTry(sb.toString());
			twisterView.smileyButton.setGraphic(twisterView.smileyImageViews[index]);

			if (index == GameView.THUMBS_UP_INDEX) {
				Collections.sort(twister.twisterRound
						.getSubmittedListsByWordLength(sb.length() - Twister.TWISTER_MIN_WORD_LENGTH));
				int solution = twister.twisterRound
						.getSolutionListsByWordLength(sb.length() - Twister.TWISTER_MIN_WORD_LENGTH).size();
				int submissions = twister.twisterRound
						.getSubmittedListsByWordLength(sb.length() - Twister.TWISTER_MIN_WORD_LENGTH).size();
				twisterView.wordScoreLabels[sb.length() - Twister.TWISTER_MIN_WORD_LENGTH]
						.setText(submissions + "/" + solution);
				twisterView.topMessageText.setText(twister.getScoreString());
			}

			if (index == GameView.SMILEY_INDEX) {
				int duration = (int) GameView.wordTimer.timeline.getCurrentTime().toSeconds();
				GameView.wordTimer.timeline.stop();
				twisterView.topMessageText.setText(twister.getScoreString());
				double solutionWords = twister.twisterRound.getSolutionWordsList().size();
				double submittedWords = 0;
				for (int i = 0; i < twister.twisterRound.getSubmittedListsByWordLength().size(); i++) {
					submittedWords = submittedWords + twister.twisterRound.getSubmittedListsByWordLength(i).size();
				}

				double score = (double) submittedWords / solutionWords;
				String scoreString = "1," + twister.twisterRound.getPuzzleWord() + "," + duration + ","
						+ String.format("%.6f", score);
				WordNerdModel.writeScore(scoreString);
				twister.twisterRound.setIsRoundComplete(true);
			}

			if (index == GameView.SMILEY_INDEX || index == GameView.THUMBS_UP_INDEX) {
				StringBuilder sb1 = new StringBuilder();
				for (int i = 0; i < twisterView.answerButtons.length; i++) {
					sb1.append(twisterView.answerButtons[i].getText());
					twisterView.answerButtons[i].setText("");
				}

				String letters = sb1.toString();
				int j = 0;
				for (int i = 0; i < twisterView.clueButtons.length; i++) {
					if (twisterView.clueButtons[i].getText().isBlank()) {
						twisterView.clueButtons[i].setText(letters.charAt(j) + "");
						j++;
					}
				}
			}
		}

	}

	/**
	 * NewButtonHandler starts a new round of game
	 */
	class NewButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			twister.twisterRound = twister.setupRound();
			twisterView.setupBottomGrid();
			twisterView.refreshGameRoundView(twister.twisterRound);
			GameView.wordTimer.restart(Twister.TWISTER_GAME_TIME);
			setupBindings();

		}

	}
}
