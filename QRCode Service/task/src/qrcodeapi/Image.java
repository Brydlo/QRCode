package qrcodeapi;

import com.google.zxing.EncodeHintType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


@Component
public class Image {

    @Value("${image.default.size}")
    private int DEFAULT_SIZE;

    @Value("${image.default.type}")
    private String IMAGE_TYPE;

    private static final Logger LOGGER = LoggerFactory.getLogger(Image.class);
    private final QRCodeWriter qrCodeWriter = new QRCodeWriter();
    public BufferedImage createQRCode(String data, int width, int height, String correction) {
        BitMatrix bitMatrix;
        ImageCorrectionUtil imageCorrectionUtil = new ImageCorrectionUtil(correction);
        Map<EncodeHintType, ?> hints = imageCorrectionUtil.getCorrectionLevelMap();
        try {
            bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height, hints);
        } catch (WriterException e) {
            LOGGER.error("Error occurred while generating QR code", e);
            return null;
        }
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
}
