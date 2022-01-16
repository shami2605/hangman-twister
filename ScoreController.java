// Name: Shamitha Naidu
// andrewid : shamithn
package hw3;

import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.VBox;

public class ScoreController extends WordNerdController {
	ScoreView scoreView;

	@Override
	void startController() {
		// initialize scoreView
		scoreView = new ScoreView();
		scoreView.setupView();
		setupBindings();

		VBox lowerPanel = new VBox();
		lowerPanel.getChildren().add(scoreView.scoreGrid);
		lowerPanel.getChildren().add(WordNerd.exitButton);
		lowerPanel.setAlignment(Pos.CENTER);
		WordNerd.root.setCenter(lowerPanel);
	}

	@Override
	void setupBindings() {
		// Call the readScore method which updates the Observable list of Score objects
		WordNerdModel.readScore();
		ScoreChart sc = new ScoreChart();

		// Pass the observable list in ScoreChart's drawChart method as a parameter to
		// get List of linecharts & display the linecharts in scoreView
		List<LineChart<Number, Number>> linecharts = sc.drawChart(WordNerdModel.scoreList);
		scoreView.scoreGrid.add(linecharts.get(0), 0, 1, 2, 1);
		scoreView.scoreGrid.add(linecharts.get(1), 0, 2, 2, 1);

	}

}
