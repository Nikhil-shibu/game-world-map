package file;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Tile {
    public BufferedImage image;
    private BufferedImage tile[];

    Tile() {
        tile = new BufferedImage[10];
        try {
            tile[0] = ImageIO.read(getClass().getResourceAsStream("/image/grass.png"));
            tile[1] = ImageIO.read(getClass().getResourceAsStream("/image/player.png"));
        } catch (Exception e) {
            System.err.println("Couldnt load the image");
        }
        
    }
    public void loadImage(int n) {
        try {
            image = (n>=0) ? tile[n] : null;
            if (image == null) {
                System.err.println("Failed to load image - stream was null");
            }
        } catch (Exception e) {
            System.err.println("Error loading image:");
        }
    }
}