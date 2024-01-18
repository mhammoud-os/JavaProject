import java.awt.Rectangle;

public class Platform extends Rectangle {
    Rectangle rectTop = new Rectangle(this.x, this.y, this.width, (int)this.height/2);
    Rectangle rectBottom= new Rectangle(this.x, this.y+this.height/2, this.width, (int)this.height/2);
    Platform(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    Rectangle getTop() {
        return this.rectTop;
    }
    Rectangle getBottom() {
        return this.rectBottom;
    }

}