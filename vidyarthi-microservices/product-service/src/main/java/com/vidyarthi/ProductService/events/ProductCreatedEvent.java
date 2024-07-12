package com.vidyarthi.ProductService.events;

import lombok.*;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductCreatedEvent {
//    private String product_id;
    private String title;
   // private String description;
//    private String price;
//    private String category;
    private String uid;
    private String type;
//    private byte[] product_img;
//    private Timestamp createdAt;
//    private Timestamp updatedAt;
}
