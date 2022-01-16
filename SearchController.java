// Name: Shamitha Naidu
// andrewid : shamithn
package hw3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class SearchController extends WordNerdController {

	SearchView searchView;
	ObservableList<Score> scores = FXCollections.observableArrayList();

	@Override
	void startController() {
		// Initialize searchView & call setupBindings to bind GUI with model
		searchView = new SearchView();
		searchView.setupView();
		setupBindings();
	}

	@Override
	void setupBindings() {

		// Call readScore method to update the observable list from the csv file
		WordNerdModel.readScore();
		scores = WordNerdModel.scoreList;

		// Bind choosing a option from comboBoxdropdown with GameBoxHandler
		searchView.gameComboBox.setOnAction(new GameBoxHandler());

		// Bind the text field search with the tableview to filter the table values
		// displayed
		// according to the characters the user enters
		searchView.searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			ObservableList<Score> filteredScores = FXCollections.observableArrayList();
			for (int i = 0; i < scores.size(); i++) {
				int count = 0;
				for (int j = 0; j < newValue.length(); j++) {
					if (scores.get(i).getPuzzleWord().contains(newValue.charAt(j) + "")) {
						count++;
					}
				}
				if (count == newValue.length()) {
					filteredScores.add(scores.get(i));
				}
			}

			searchView.searchTableView.setItems(filteredScores);

		});
	}

	class GameBoxHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			WordNerdModel.readScore();
			scores = WordNerdModel.scoreList;
			searchView.searchTextField.clear();

			// Check the filter selected by the user in the comboBox and acordingly filter
			// the tableView according to the game filter selected
			if (searchView.gameComboBox.getValue().equalsIgnoreCase("Hangman")) {
				ObservableList<Score> hangmanScores = FXCollections.observableArrayList();
				for (int i = 0; i < scores.size(); i++) {
					if (scores.get(i).getGameId() == 0) {
						hangmanScores.add(scores.get(i));
					}
				}
				scores = hangmanScores;
				searchView.searchTableView.setItems(hangmanScores);
			} else if (searchView.gameComboBox.getValue().equalsIgnoreCase("Twister")) {
				ObservableList<Score> twisterScores = FXCollections.observableArrayList();
				for (int i = 0; i < scores.size(); i++) {
					if (scores.get(i).getGameId() == 1) {
						twisterScores.add(scores.get(i));
					}
				}
				scores = twisterScores;
				searchView.searchTableView.setItems(twisterScores);
			} else {
				searchView.searchTableView.setItems(scores);
			}

		}

	}

}
