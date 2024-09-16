package com.example.nyatta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImagesDTO {
    private Long id;
    private String imageURL;
    private int imageOrder;
    private PropertyDTO propertyDTO;

}
