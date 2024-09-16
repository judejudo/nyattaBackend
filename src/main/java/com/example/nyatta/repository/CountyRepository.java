package com.example.nyatta.repository;

import com.example.nyatta.entity.County;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountyRepository extends JpaRepository<County, Long> {
}
