import hsa2.GraphicsConsole;
import java.awt.Color;
import java.util.Random;
import java.lang.Math;

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
        Platform MainP = new Platform(0, 720-100, 1280, 100);
        gc.setColor(Color.GREEN);
        gc.fillRect(MainP.x, MainP.y, MainP.width, MainP.height);

        int[] Blocks = new int[200];
        for (int i = 0; i < 200; i++) {
            Blocks[i] = rand.nextInt(0, 2);
            System.out.println(Blocks[i]);
        }

        for (int i = 0; i < 200; i++) {
            if (Blocks[i] == 1) {
                gc.setColor(Color.BLACK);

                int r_x = 175 + (31*(i%30));
                int r_y = 100 + (48 * ((int)(i/20)));
                int r_width = 30;
                int r_height = 30;
                System.out.println(r_x);
                System.out.println(r_y);
                System.out.println(r_width);
                System.out.println(r_height);
                gc.fillRect(r_x, r_y, r_width, r_height);
            }
        }
    }
}