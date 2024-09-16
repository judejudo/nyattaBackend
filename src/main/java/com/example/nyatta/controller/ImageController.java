package com.example.nyatta.controller;

import com.example.nyatta.dto.ImagesDTO;
import com.example.nyatta.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5500")
@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;


    @PostMapping("/test")
    public ResponseEntity<String> testParams(
            @RequestParam("propertyId") Long propertyId,
            @RequestParam("imageOrder") int imageOrder) {

        System.out.println("Test method invoked");
        return ResponseEntity.ok("Received propertyId: " + propertyId + ", imageOrder: " + imageOrder);
    }
    @PostMapping
    public ResponseEntity<ImagesDTO> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("propertyId") Long propertyId,
            @RequestParam("imageOrder") int imageOrder) {
        ImagesDTO imagesDTO = imageService.createImage(file, propertyId, imageOrder);

        return ResponseEntity.ok(imagesDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImagesDTO> updateImage(@PathVariable Long id, @RequestBody ImagesDTO imagesDTO) {
        ImagesDTO updatedImage = imageService.updateImage(id, imagesDTO);
        return ResponseEntity.ok(updatedImage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImagesDTO> getImageById(@PathVariable Long id) {
        ImagesDTO imagesDTO = imageService.getImageById(id);
        return ResponseEntity.ok(imagesDTO);
    }

    @GetMapping
    public ResponseEntity<List<ImagesDTO>> getAllImages() {
        List<ImagesDTO> imagesDTOList = imageService.getAllImages();
        return ResponseEntity.ok(imagesDTOList);
    }
}
