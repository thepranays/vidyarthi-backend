package com.vidyarthi.ProductService.services;



import com.vidyarthi.ProductService.constants.Constants;
import com.vidyarthi.ProductService.dtos.ProductRequest;
import com.vidyarthi.ProductService.dtos.ProductResponse;
import com.vidyarthi.ProductService.events.ProductCreatedEvent;
import com.vidyarthi.ProductService.models.Product;
import com.vidyarthi.ProductService.repos.ProductRepository;
import com.vidyarthi.ProductService.utils.FileSystemUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j //for logging
public class ProductService {
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;
    public void createProduct(ProductRequest productRequest, MultipartFile productImg)  {
        String productId=UUID.randomUUID().toString();

        //Store product image to file system
        String pathToProductImg= FileSystemUtils.StoreProductImage(productImg,productId);
       // System.out.println(productRequest.toString());
        //Store product data in DB
        Product newProduct=Product.builder()
                .product_id(productId)
                .description(productRequest.getDescription())
                .category(productRequest.getCategory())
                .price(new BigDecimal(productRequest.getPrice()))
                .title(productRequest.getTitle())
                .type(productRequest.getType())
                .product_img_name(pathToProductImg)
                .uid(productRequest.getUid())
                .createdAt(Timestamp.from(Instant.now()))
                .updatedAt(Timestamp.from(Instant.now()))
                .build();
        productRepository.save(newProduct); //save to postgres

        //Send kafka message to kafka new product created topic (i.e. produce)
        kafkaTemplate.send(Constants.KAFKA_TOPIC_PRODUCT_CREATED,
                ProductCreatedEvent.builder().title(newProduct.getTitle()).type(newProduct.getType()).uid(newProduct.getUid()).build());

        //Logging
        log.info("Created a product title:"+productRequest.getTitle()+" pid:"+newProduct.getProduct_id()+" by uid:"+productRequest.getUid());
    }
    public void updateProductById(String productId,ProductRequest productRequest,MultipartFile productImg){
        Optional<Product> optionalProduct=productRepository.findById(productId);
        if(optionalProduct.isPresent()){ //will be present unless request comes for non-existent productId
            Product product=optionalProduct.get();
            //Update product image
            FileSystemUtils.OverwriteProductImage(productImg,product.getProduct_img_name(),productId);
            //Update product details
            product.setDescription(productRequest.getDescription());
            product.setCategory(productRequest.getCategory());
            product.setPrice(new BigDecimal(productRequest.getPrice()));
            product.setTitle(productRequest.getTitle());
            product.setType(productRequest.getType());
            product.setUid(productRequest.getUid());
            product.setUpdatedAt(Timestamp.from(Instant.now()));
            productRepository.save(product);
        }



    }
    public void deleteProductById(String pid){

        productRepository.deleteById(pid);
        log.info("Delete a product title:"+pid);
    }
    public ProductResponse findProductById(String pid){
        Optional<Product> optionalProduct=productRepository.findById(pid);
        if(optionalProduct.isPresent()){
            Product product=optionalProduct.get();
            //Retrieve product image from file system
            byte[] productImg=FileSystemUtils.RetrieveProductImage(product.getProduct_img_name(), product.getProduct_id());
            return ProductResponse.builder()
                    .product_id(product.getProduct_id())
                    .description(product.getDescription())
                    .category(product.getCategory())
                    .product_img(productImg)
                    .price(product.getPrice().toString())
                    .title(product.getTitle())
                    .type(product.getType())
                    .uid(product.getUid())
                    .updatedAt(product.getUpdatedAt())
                    .createdAt(product.getCreatedAt()).build();

        }
        return null;
    }

    public List<ProductResponse> getAllProducts(){
        List<Product> products=productRepository.findAll();
        List<byte[]> productsImgList=new ArrayList<>();

        //Retrieve product image from file system
        for (Product p : products) {
            productsImgList.add(FileSystemUtils.RetrieveProductImage(p.getProduct_img_name(), p.getProduct_id()));
        }

        List<ProductResponse> responses = new ArrayList<>();
        int pSz=products.size();
        for(int i=0;i<pSz;i++){
            responses.add(ProductResponse.builder()
                .product_id(products.get(i).getProduct_id())
                .description(products.get(i).getDescription())
                .category(products.get(i).getCategory())
                .product_img(productsImgList.get(i))
                .price(products.get(i).getPrice().toString())
                .title(products.get(i).getTitle())
                .type(products.get(i).getType())
                .uid(products.get(i).getUid())
                .updatedAt(products.get(i).getUpdatedAt())
                .createdAt(products.get(i).getCreatedAt()).build());
        }

        return responses;
    }

}
