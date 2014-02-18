package mx.com.ledi.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jimmy
 */
public class ImageUtil {

	public static Logger log = LoggerFactory.getLogger(ImageUtil.class);
	//

	public static byte[] getBytes(BufferedImage image) {
		if (image != null) {
			try {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				ImageIO.write(image, "png", out);
				out.flush();
				out.close();
				return out.toByteArray();
			} catch (IOException ioex) {
				log.error(ioex.getMessage(), ioex);
			}
		}
		return null;
	}

	public static BufferedImage getImage(byte[] bytea) {
		if (bytea != null) {
			try {
				return ImageIO.read(new ByteArrayInputStream(bytea));
			} catch (IOException ex) {
				log.error(ex.getMessage(), ex);
			}
		}
		return null;
	}
}
