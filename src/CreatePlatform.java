import hsa2.GraphicsConsole;
import java.awt.Color;
import java.util.Random;

public class CreatePlatform {
    GraphicsConsole gc = new GraphicsConsole(1280, 720);
    Random rand = new Random();

    public static void main(String[] args) {
        new CreatePlatform();
    }

    void setup() {
        gc.setBackgroundColor(Color.WHITE);
        gc.clear();
    }

    CreatePlatform() {
        setup();

        Platform mainPlatform = new Platform(0, 720 - 100, 1280, 100);
        gc.setColor(Color.GREEN);
        gc.fillRect(mainPlatform.x, mainPlatform.y, mainPlatform.width, mainPlatform.height);

        int[] blocks = new int[200];
        for (int i = 0; i < 200; i++) {
            blocks[i] = rand.nextInt(0, 2);
            System.out.println(blocks[i]);
        }

        for (int i = 0; i < 200; i++) {
            if (blocks[i] == 1) {
                gc.setColor(Color.BLACK);

                int rectX = 175 + (31 * (i % 30));
                int rectY = 100 + (48 * (i / 20));
                int rectWidth = 30;
                int rectHeight = 30;

                System.out.println(rectX);
                System.out.println(rectY);
                System.out.println(rectWidth);
                System.out.println(rectHeight);

                gc.fillRect(rectX, rectY, rectWidth, rectHeight);
            }
        }
    }
}