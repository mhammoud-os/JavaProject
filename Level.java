import java.awt.Color;

public class Level{
	Color levelBg;
	String levelName;
	
	Level(int l) {
		if (l%3 == 0) {
			levelName = "Easy";
			levelBg = Color.PINK;
		} else if(l%3 == 1) {
			levelName = "Medium";
			levelBg = Color.ORANGE;
		} else if(l%3 == 2) {
			levelName = "Hard";
			levelBg = Color.RED;
		}
	}
}
