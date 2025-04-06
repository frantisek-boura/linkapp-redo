package link.app.backend.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

@Service
public class ImageCompressionService implements IImageCompressionService {

    @Override
    public byte[] compressImage(byte[] imageData) {
        if (imageData == null || imageData.length == 0) {
            return null;
        }

        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
            BufferedImage originalImage = ImageIO.read(inputStream);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(originalImage, "jpg", outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to compress image", e);
        }
    }
    
}
