package vpmLimp.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;
import vpmLimp.validations.QRCodeValidation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class QRCodeService {

    /**
     * Gera uma imagem de QR Code em formato PNG a partir do texto fornecido.
     * Este QR Code é fictício e apenas para testes.
     *
     * @param text   O conteúdo que será codificado no QR Code.
     * @param width  Largura da imagem do QR Code.
     * @param height Altura da imagem do QR Code.
     * @return Um array de bytes contendo a imagem PNG do QR Code.
     * @throws WriterException Se ocorrer um erro na geração do QR Code.
     * @throws IOException     Se ocorrer um erro de E/S ao escrever a imagem.
     */
    public byte[] generateQRCode(String text, int width, int height) throws WriterException, IOException {
        QRCodeValidation.validateQRCodeParameters(text, width, height);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            return outputStream.toByteArray();
        }
    }
}
