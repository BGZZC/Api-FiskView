package com.fiskview.apifiskview.controller;

import com.fiskview.apifiskview.model.UsuarioVotante;
import com.fiskview.apifiskview.repository.UsuarioVotanteRepository;
import org.apache.commons.imaging.Imaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Base64;

@RestController
@RequestMapping("/api/recognition")
public class FaceRecognitionController {

    @Autowired
    private UsuarioVotanteRepository usuarioVotanteRepository;


    @PostMapping("/compareImages")
    public ResponseEntity<String> compareImages(@RequestBody String base64CapturedImage, @PathVariable Long userId) {
        try {
            // Recuperar la imagen almacenada en la base de datos
            UsuarioVotante usuario = usuarioVotanteRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            String base64StoredImage = usuario.getImagenFacial();

            // Decodificar la imagen Base64 almacenada
            BufferedImage storedImage = decodeToImage(base64StoredImage);
            BufferedImage capturedImage = decodeToImage(base64CapturedImage);

            // Comparar imágenes (aquí se puede implementar una lógica específica)
            boolean isMatch = compareBufferedImages(storedImage, capturedImage);

            return ResponseEntity.ok(isMatch ? "Las imágenes coinciden." : "Las imágenes no coinciden.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al comparar las imágenes: " + e.getMessage());
        }
    }

    private BufferedImage decodeToImage(String base64Image) throws Exception {
        byte[] imageBytes = Base64.getDecoder().decode(base64Image.split(",")[1]);
        return Imaging.getBufferedImage(new ByteArrayInputStream(imageBytes));
    }

    private boolean compareBufferedImages(BufferedImage imgA, BufferedImage imgB) {
        // Lógica simple para comparar imágenes: comparar dimensiones y píxeles
        if (imgA.getWidth() != imgB.getWidth() || imgA.getHeight() != imgB.getHeight()) {
            return false;
        }
        for (int y = 0; y < imgA.getHeight(); y++) {
            for (int x = 0; x < imgA.getWidth(); x++) {
                if (imgA.getRGB(x, y) != imgB.getRGB(x, y)) {
                    return false;
                }
            }
        }
        return true; // Las imágenes son idénticas
    }
}
