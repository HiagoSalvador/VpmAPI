package vpmLimp.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vpmLimp.services.QRCodeService;

@RestController
@RequestMapping("/qrcode")
public class QRCodeController {

    private final QRCodeService qrCodeService;

    public QRCodeController(QRCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    /**
     * Gera um QR Code de pagamento com um valor fictício.
     *
     * OBS: Este QR Code é fictício e não realiza transações reais.
     *
     * @param value Valor do pagamento para gerar o QR Code.
     * @return Imagem PNG do QR Code como resposta.
     */
    @GetMapping("")
    public ResponseEntity<byte[]> getQRCode(@RequestParam Double value) {
        try {
            // TODO: Ajustar para integração real no futuro
            byte[] qrCodeImage = qrCodeService.generateQRCode(String.valueOf(value), 250, 250);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"qrcode.png\"")
                    .contentType(MediaType.IMAGE_PNG)
                    .body(qrCodeImage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
