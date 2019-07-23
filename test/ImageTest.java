import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageTest {
    @Test
    public void testLoadImage(){
        try {
            BufferedImage image= ImageIO.read(new File("E:/idea/Tangk2019/src/images/bulletD.gif"));
            assertNotNull(image);
            BufferedImage image1=ImageIO.read(ImageTest.class.getClassLoader().getResourceAsStream("images/bulletD.gif"));
            assertNotNull(image1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
