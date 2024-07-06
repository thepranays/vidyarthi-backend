package com.vidyarthi.ProductService.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="products")
public class Product {
    @Id
    private String product_id;
    private String title;
    private String description;
    private BigDecimal price;
    private String category;
    private String uid;
    private String type;
    private String product_img_name;
    private Timestamp createdAt;
    private Timestamp updatedAt;


}
