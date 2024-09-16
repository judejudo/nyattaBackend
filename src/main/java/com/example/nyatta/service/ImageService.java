package com.example.nyatta.service;

import com.example.nyatta.dto.ImagesDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    ImagesDTO createImage(MultipartFile file, Long propertyId,int imageOrder);  // Handle image creation with file upload
    ImagesDTO updateImage(Long id, ImagesDTO imagesDTO);          // Update an existing image
    void deleteImage(Long id);                                    // Delete an image by ID
    ImagesDTO getImageById(Long id);                              // Retrieve an image by ID
    List<ImagesDTO> getAllImages();                               // Retrieve all images
}
