package allinter.lowvision;

import org.eclipse.actf.visualization.internal.engines.lowvision.image.IInt2D;
import org.eclipse.actf.visualization.internal.engines.lowvision.image.Int2D;
import org.eclipse.actf.visualization.internal.engines.lowvision.image.PageImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PageImageFactory {
    public static PageImage loadFromPng(final byte[] data) throws IOException {
        try (InputStream stream = new ByteArrayInputStream(data)) {
            return loadFromPng(stream);
        }
    }

    public static PageImage loadFromPng(final InputStream input) throws IOException {
        final BufferedImage image = ImageIO.read(input);
        return loadFrom(image);
    }

    public static PageImage loadFrom(final BufferedImage image) {
        final IInt2D int2dWhole = loadImageData(image);
        return new PageImage(int2dWhole, false);
    }

    private static IInt2D loadImageData(final BufferedImage image) {
        final int imageWidth = image.getWidth();
        final int imageHeight = image.getHeight();
        Int2D destImage = new Int2D(imageWidth, imageHeight);
        int[][] destData = destImage.getData();
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                int argb = image.getRGB(x, y);
                int rgb = argb & 0x00ffffff;
                destData[y][x] = rgb;
            }
        }
        return destImage;
    }
}
