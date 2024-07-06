package com.vidyarthi.ProductService.dtos;

import jakarta.persistence.Lob;
import lombok.*;
import org.hibernate.annotations.Type;


import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductResponse {
    private String product_id;
    private String title;
    private String description;
    private String price;
    private String category;
    private String uid;
    private String type;
    private byte[] product_img;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
