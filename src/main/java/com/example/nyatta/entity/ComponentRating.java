package com.example.nyatta.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Setter
@Getter
public class ComponentRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "component_id")
    private ReviewComponent reviewComponent;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private UserReview userReview;

    private Integer rating;

    // Getters and Setters
}
