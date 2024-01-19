package graphics;

import java.awt.Rectangle;

public class Platform extends Rectangle {
	int sidesWidth = 10;
	int heightOffset = 7;
    Platform(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    Rectangle getTop() {
    	return new Rectangle(this.x, this.y, this.width, (int)this.height/4);
    }
    Rectangle getBottom() {
    	return new Rectangle(this.x, this.y+(this.height/4)*3, this.width, (int)this.height/4);
    }
    Rectangle getLeft() {
    	return new Rectangle(this.x-this.sidesWidth, this.y+heightOffset, sidesWidth, this.height-(heightOffset*2));
    }
    Rectangle getRight() {
    	return new Rectangle(this.x+this.width, this.y+heightOffset, sidesWidth, this.height-(heightOffset*2));
    }
}
