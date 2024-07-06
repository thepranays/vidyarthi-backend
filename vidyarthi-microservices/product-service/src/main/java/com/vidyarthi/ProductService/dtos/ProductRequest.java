package com.vidyarthi.ProductService.dtos;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductRequest {
    private String uid;
    private String title;
    private String description;
    private String price;
    private String category;
    private String type;
    //product_img is sent separately as multipart file (requestpart="product_img")

}
