package qrcodeapi;


import org.springframework.http.MediaType;

import java.util.Map;
import java.util.Optional;


public class ImageTypeUtil {
    private static final Map<String, MediaType> SUPPORTED_IMAGE_TYPES = Map.of(
            "png", MediaType.IMAGE_PNG,
            "jpeg", MediaType.IMAGE_JPEG,
            "gif", MediaType.IMAGE_GIF
    );

    public static Optional<MediaType> getMediaType(String type) {
        return Optional.ofNullable(SUPPORTED_IMAGE_TYPES.get(type.toLowerCase()));
    }
}
