package qrcodeapi;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

@RestController
public class ControllerClass {

    private final Image image;

    public ControllerClass(Image image) {
        this.image = image;
    }


    @GetMapping("/api/health")
    public String health() {
        return "";
    }


    @GetMapping(path = "/api/qrcode")
    public ResponseEntity<byte[]> getQRCode(@RequestParam String contents,
                                            @RequestParam(required = false, defaultValue = "250") int size,
                                            @RequestParam(required = false, defaultValue = "L") String correction,
                                            @RequestParam(required = false, defaultValue = "png") String type) throws IOException {
                validateImageContent(contents);
                validateImageSize(size);
                validateImageCorrection(correction);
                validateImageType(type);
                Optional<MediaType> optionalMediaType = ImageTypeUtil.getMediaType(type);
                if (optionalMediaType.isEmpty()) return ResponseEntity.badRequest().build();
                BufferedImage myImage = image.createQRCode(contents, size, size, correction);
                try (var outputStream = new ByteArrayOutputStream()) {
                    ImageIO.write(myImage, type.toLowerCase(), outputStream);
                    byte[] bytes = outputStream.toByteArray();
                    return ResponseEntity.ok().contentType(optionalMediaType.get()).body(bytes);
                }
    }


    private void validateImageContent(String content) throws IllegalArgumentException {
        if (content == null || content.isEmpty() || content.isBlank()) throw new IllegalArgumentException("{\"error\": \"Contents cannot be null or blank\"}");
    }
    private void validateImageSize(int size) throws IllegalArgumentException {
        if (size > 350 || size < 150) throw new IllegalArgumentException("{\"error\": \"Image size must be between 150 and 350 pixels\"}");
    }

    private void validateImageCorrection(String c) throws IllegalArgumentException {
        if (!c.equalsIgnoreCase("L")
                && !c.equalsIgnoreCase("M")
                && !c.equalsIgnoreCase("Q")
                && !c.equalsIgnoreCase("H")) {
            throw new IllegalArgumentException("{\"error\": \"Permitted error correction levels are L, M, Q, H\"}");
        }

    }
    private void validateImageType(String type) throws IllegalArgumentException {
        if(!type.equalsIgnoreCase("PNG")
                && !type.equalsIgnoreCase("JPEG")
                && !type.equalsIgnoreCase("GIF")) {
            throw new IllegalArgumentException("{\"error\": \"Only png, jpeg and gif image types are supported\"}");
        }
    }
}