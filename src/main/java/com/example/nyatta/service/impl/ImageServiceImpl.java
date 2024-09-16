package com.example.nyatta.service.impl;

import com.example.nyatta.dto.ImagesDTO;
import com.example.nyatta.dto.PropertyDTO;
import com.example.nyatta.entity.Images;
import com.example.nyatta.repository.ImageRepository;
import com.example.nyatta.repository.PropertyRepository;
import com.example.nyatta.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    // Define the path to the static images directory
    private final Path rootLocation = Paths.get("src/main/resources/static/images");

    @Override
    public ImagesDTO createImage(MultipartFile file, Long propertyId, int imageOrder) {
        try {
            String fileName = saveImageToFileSystem(file);
            Images images = new Images();
            images.setImageURL("C:/Users/ocomi/OneDrive/Documents/nyatta/src/main/resources/static/images" + fileName);
            images.setImageOrder(imageOrder); // Define logic to determine image order
            images.setProperty(propertyRepository.findById(propertyId).orElse(null));
            images = imageRepository.save(images);
            return convertToDTO(images);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store image file", e);
        }
//            Images images = new Images();
//            images.setProperty(propertyRepository.findById(propertyId).orElse(null));
//            images = imageRepository.save(images);
//            return convertToDTO(images);
    }

    @Override
    public ImagesDTO updateImage(Long id, ImagesDTO imagesDTO) {
        Images images = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));
        images.setImageURL(imagesDTO.getImageURL());
        images.setImageOrder(imagesDTO.getImageOrder());
        images.setProperty(propertyRepository.findById(imagesDTO.getPropertyDTO().getId()).orElse(null));

        images = imageRepository.save(images);
        return convertToDTO(images);
    }

    @Override
    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }

    @Override
    public ImagesDTO getImageById(Long id) {
        Images images = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));
        return convertToDTO(images);
    }

    @Override
    public List<ImagesDTO> getAllImages() {
        return imageRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private Images convertToEntity(ImagesDTO imagesDTO) {
        Images images = new Images();
        images.setImageURL(imagesDTO.getImageURL());
        images.setImageOrder(imagesDTO.getImageOrder());
        images.setProperty(propertyRepository.findById(imagesDTO.getPropertyDTO().getId()).orElse(null));
        return images;
    }

    private ImagesDTO convertToDTO(Images images) {
        ImagesDTO imagesDTO = new ImagesDTO();
        imagesDTO.setId(images.getId());
        imagesDTO.setImageURL(images.getImageURL());
        imagesDTO.setImageOrder(images.getImageOrder());

        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setId(images.getProperty().getId());
        imagesDTO.setPropertyDTO(propertyDTO);

        return imagesDTO;
    }

    private String saveImageToFileSystem(MultipartFile file) throws IOException {
        // Ensure the directory exists
        if (!Files.exists(rootLocation)) {
            Files.createDirectories(rootLocation);
        }

        // Generate a unique file name
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // Save the file to the file system
        Files.copy(file.getInputStream(), rootLocation.resolve(fileName));

        return fileName;
    }

}
