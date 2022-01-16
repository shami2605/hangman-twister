// Name: Shamitha Naidu
// andrewid : shamithn
package hw3;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TwisterRound extends GameRound {

	private ObservableList<String> solutionsWordsList;
	private ObservableList<ObservableList<String>> submittedListsByWordLength;
	private ObservableList<ObservableList<String>> solutionListsByWordLength;

	// Using the default constructor to initialize the Observable lists
	public TwisterRound() {
		super();
		int numberOfLists = Twister.TWISTER_MAX_WORD_LENGTH - Twister.TWISTER_MIN_WORD_LENGTH + 1;
		submittedListsByWordLength = FXCollections.observableArrayList();
		solutionListsByWordLength = FXCollections.observableArrayList();
		ObservableList<String> solutionWordsOfLength;
		ObservableList<String> submittedWordsOfLength;
		for (int i = 0; i < numberOfLists; i++) {
			solutionWordsOfLength = FXCollections.observableArrayList();
			submittedWordsOfLength = FXCollections.observableArrayList();
			solutionListsByWordLength.add(solutionWordsOfLength);
			submittedListsByWordLength.add(submittedWordsOfLength);
		}
	}

	public List<String> getSolutionWordsList() {
		return solutionsWordsList;
	}

	public void setSolutionWordsList(List<String> solutionsWordsList) {
		this.solutionsWordsList = FXCollections.observableArrayList(solutionsWordsList);

	}

	public ObservableList<String> solutionWordsListProperty() {
		return solutionsWordsList;

	}

	public ObservableList<String> getSubmittedListsByWordLength(int letterCount) {
		return submittedListsByWordLength.get(letterCount);
	}

	public ObservableList<ObservableList<String>> getSubmittedListsByWordLength() {
		return submittedListsByWordLength;
	}

	// The length of the input word is checked & accordingly it is added to one of
	// the lists within the submittedListsByWordLength list
	public void setSubmittedListsByWordLength(String word) {
		int numberOfLists = Twister.TWISTER_MAX_WORD_LENGTH - Twister.TWISTER_MIN_WORD_LENGTH + 1;
		for (int i = 0; i < numberOfLists; i++) {
			if (word.length() == Twister.TWISTER_MIN_WORD_LENGTH + i) {
				if (submittedListsByWordLength.get(i) == null) {
					List<String> list = new ArrayList<String>();
					list.add(word);
					submittedListsByWordLength.set(i, (ObservableList<String>) list);
				} else {
					submittedListsByWordLength.get(i).add(word);
					break;
				}
			}
		}

	}

	public ObservableList<ObservableList<String>> submittedListsByWordLengthProperty() {
		return submittedListsByWordLength;

	}

	// The length of the input word is checked & accordingly it is added to one of
	// the lists within the solutionListsByWordLength list
	public void setSolutionListsByWordLength(String word) {
		int numberOfLists = Twister.TWISTER_MAX_WORD_LENGTH - Twister.TWISTER_MIN_WORD_LENGTH + 1;
		for (int i = 0; i < numberOfLists; i++) {
			if (word.length() == Twister.TWISTER_MIN_WORD_LENGTH + i) {
				if (solutionListsByWordLength.get(i) == null) {
					List<String> list = new ArrayList<String>();
					list.add(word);
					solutionListsByWordLength.set(i, (ObservableList<String>) list);
					break;
				} else {
					solutionListsByWordLength.get(i).add(word);
					break;
				}
			}
		}

	}

	public ObservableList<ObservableList<String>> getSolutionListsByWordLength() {
		return solutionListsByWordLength;

	}

	public ObservableList<String> getSolutionListsByWordLength(int letterCount) {
		return solutionListsByWordLength.get(letterCount);

	}

	public ObservableList<ObservableList<String>> solutionListsByWordLengthProperty() {
		return solutionListsByWordLength;

	}

}
