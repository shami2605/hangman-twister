// Name: Shamitha Naidu
// andrewid : shamithn
package hw3;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class HangmanRound extends GameRound {

	private IntegerProperty hitCount = new SimpleIntegerProperty();
	private IntegerProperty missCount = new SimpleIntegerProperty();

	public void setHitCount(int hitCount) {
		this.hitCount.set(hitCount);
	}

	public int getHitCount() {
		return hitCount.get();
	}

	public IntegerProperty hitCountProperty() {
		return hitCount;
	}

	public void setMissCount(int missCount) {
		this.missCount.set(missCount);
	}

	public int getMissCount() {
		return missCount.get();
	}

	public IntegerProperty missCountProperty() {
		return missCount;
	}

}
