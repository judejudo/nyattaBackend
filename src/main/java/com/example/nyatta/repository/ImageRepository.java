package com.example.nyatta.repository;

import com.example.nyatta.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.*;
import java.util.List;
public interface ImageRepository extends JpaRepository<Images, Long> {
    List<Images> findByPropertyId(Long propertyId);
    void deleteByPropertyId(Long propertyId);
}
