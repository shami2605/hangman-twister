// Name: Shamitha Naidu
// andrewid : shamithn
package hw3;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class SearchView {

	ComboBox<String> gameComboBox = new ComboBox<>(); // shows drop down for filtering the tableView data
	TextField searchTextField = new TextField(); // for entering search letters
	TableView<Score> searchTableView = new TableView<>(); // displays data from scores.csv

	// gameNameCallback is used to get the game name via the game id field
	Callback<CellDataFeatures<Score, String>, ObservableValue<String>> gameNameCallback = new Callback<CellDataFeatures<Score, String>, ObservableValue<String>>() {
		public ObservableValue<String> call(CellDataFeatures<Score, String> p) {
			return p.getValue().gameNameProperty();
		}
	};

	// scoreCallback is used to get formatted score up to 2 digits
	Callback<CellDataFeatures<Score, String>, ObservableValue<String>> scoreCallback = new Callback<CellDataFeatures<Score, String>, ObservableValue<String>>() {
		public ObservableValue<String> call(CellDataFeatures<Score, String> p) {
			return p.getValue().gameScoreFormatttedProperty();
		}
	};

	/**
	 * setupView() sets up the GUI components for Search functionality
	 */
	void setupView() {

		VBox searchVBox = new VBox(); // searchVBox contains searchLabel and searchHBox
		Text searchLabel = new Text("Search");
		searchVBox.getChildren().add(searchLabel);
		HBox searchHBox = new HBox(); // searchHBox contain gameComboBox and searchTextField
		searchHBox.getChildren().add(gameComboBox);
		searchHBox.getChildren().add(new Text("Search letters"));
		searchHBox.getChildren().add(searchTextField);
		searchVBox.getChildren().add(searchHBox);

		searchLabel.setStyle("-fx-font: 30px Tahoma;"
				+ " -fx-fill: linear-gradient(from 0% 50% to 50% 100%, repeat, lightgreen 0%, lightblue 50%);"
				+ " -fx-stroke: gray;" + " -fx-background-color: gray;" + " -fx-stroke-width: 1;");
		searchHBox.setPrefSize(WordNerd.GAME_SCENE_WIDTH, WordNerd.GAME_SCENE_HEIGHT / 3);
		gameComboBox.setPrefWidth(200);
		searchTextField.setPrefWidth(300);
		searchHBox.setAlignment(Pos.CENTER);
		searchVBox.setAlignment(Pos.CENTER);
		searchHBox.setSpacing(10);

		setupSearchTableView();

		ObservableList<String> list = gameComboBox.getItems();
		gameComboBox.setPromptText("All games");
		list.add("Hangman");
		list.add("Twister");
		list.add("All games");

		WordNerd.root.setPadding(new Insets(10, 10, 10, 10));
		WordNerd.root.setTop(searchVBox);
		WordNerd.root.setCenter(searchTableView);
		WordNerd.root.setBottom(WordNerd.exitButton);
	}

	void setupSearchTableView() {
		WordNerdModel.readScore();
		ObservableList<Score> scores = WordNerdModel.scoreList;

		// Create 4 table columns & set corresponding cell value factory acc to bean
		// names in POJO class
		TableColumn column1 = new TableColumn("Game");
		column1.setCellValueFactory(gameNameCallback);
		TableColumn column2 = new TableColumn("Word");
		column2.setCellValueFactory(new PropertyValueFactory<>("puzzleWord"));
		TableColumn column3 = new TableColumn("Time (sec)");
		column3.setCellValueFactory(new PropertyValueFactory<>("timeStamp"));
		TableColumn column4 = new TableColumn("Score");
		column4.setCellValueFactory(scoreCallback);

		// add all columns to the TableView object
		searchTableView.getColumns().addAll(column1, column2, column3, column4);

		// set width to make the columns equidistant
		column1.prefWidthProperty().bind(searchTableView.widthProperty().multiply(0.25));
		column2.prefWidthProperty().bind(searchTableView.widthProperty().multiply(0.25));
		column3.prefWidthProperty().bind(searchTableView.widthProperty().multiply(0.25));
		column4.prefWidthProperty().bind(searchTableView.widthProperty().multiply(0.25));

		searchTableView.setItems(scores);

	}

}
