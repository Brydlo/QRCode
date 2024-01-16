package qrcodeapi;

import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Map;

public class ImageCorrectionUtil {

    private String correctionLetter = "L";

    public ImageCorrectionUtil(String s) {
        correctionLetter = s;
    }
    private final Map<String, ErrorCorrectionLevel> correction = Map.of(
            "H", ErrorCorrectionLevel.H,
            "Q", ErrorCorrectionLevel.Q,
            "M", ErrorCorrectionLevel.M,
            "L", ErrorCorrectionLevel.L
    );

    public Map<EncodeHintType, ErrorCorrectionLevel> getCorrectionLevelMap () {
        return Map.of(EncodeHintType.ERROR_CORRECTION, correction.get(correctionLetter));
    }
}
