package vpmLimp.validations;

import org.springframework.stereotype.Component;

@Component
public class QRCodeValidation {


    public static void validateQRCodeParameters(String text, int width, int height) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("O texto para o QR Code não pode ser vazio.");
        }
    }

}
