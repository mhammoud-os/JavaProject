import hsa2.GraphicsConsole;
import java.awt.Color;
import java.util.Random;

public class CreatePlatform {
    GraphicsConsole gc = new GraphicsConsole(1280, 720);
    Random rand = new Random();

    static int[][] blocks;

    public static void main(String[] args) {
        new CreatePlatform();
    }

    void setup() {
        gc.setAntiAlias(true);
        gc.setBackgroundColor(Color.WHITE);
        gc.setLocationRelativeTo(null);
        gc.clear();
    }

    CreatePlatform() {
        setup();

        //Draw grass
        Platform mainPlatform = new Platform(0, 720 - 100, 1280, 100);
        gc.setColor(Color.GREEN);
        gc.fillRect(mainPlatform.x, mainPlatform.y, mainPlatform.width, mainPlatform.height);

        createBlocks();
        drawBlocks();
    }

    void createBlocks() {
        blocks = new int[10][35];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 35; j++) {
                blocks[i][j] = rand.nextInt(0, 2);
            }
        }
    }

    void drawBlocks() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 35; j++) {
                if (blocks[i][j] == 1 && i % 2 == 1) {
                    gc.setColor(Color.BLACK);

                    int rectX = 80 + (31 * (j));
                    int rectY = 75 + (48 * (i));
                    int rectWidth = 30;
                    int rectHeight = 30;

                    gc.setStroke(6);
                    gc.setColor(Color.BLACK);
                    gc.fillRect(rectX, rectY, rectWidth+30, rectHeight);
                    gc.drawRect(rectX, rectY, rectWidth+30, rectHeight);
                }
            }
        }
    }
}