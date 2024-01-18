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

        /*
        int[] blocks = new int[200];
        for (int i = 0; i < 200; i++) {
            blocks[i] = rand.nextInt(0, 2);
            System.out.println(blocks[i]);
        }
        */

        /*
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

                gc.setStroke(6);
                gc.setColor(Color.BLACK);
                gc.fillRect(rectX, rectY, rectWidth, rectHeight);
                gc.drawRect(rectX, rectY, rectWidth, rectHeight);
            }
        }
        */


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

                    int rectX = 100 + (31 * (j));
                    int rectY = 100 + (48 * (i));
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