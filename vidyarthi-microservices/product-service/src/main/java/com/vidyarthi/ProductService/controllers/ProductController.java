package com.vidyarthi.ProductService.controllers;

import com.vidyarthi.ProductService.dtos.ProductRequest;
import com.vidyarthi.ProductService.dtos.ProductResponse;
import com.vidyarthi.ProductService.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private final ProductService productService;



    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestPart("product") final ProductRequest productRequest, @RequestPart("product_img")MultipartFile productImg){
        System.out.println("hi");
         /*
    @RequestPart : This annotation associates a part of a multipart request with the method argument, which is useful for sending complex multi-attribute data as payload, e.g., JSON or XML.
    In other words Request Part parse your json string object from request to your class object. On the other hand, Request Param just obtain the string value from your json string value.
     */
        productService.createProduct(productRequest,productImg);

    }

    @PutMapping("/update/{product_id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProductById(@PathVariable(value="product_id")final String productId,@RequestPart("product") final ProductRequest productRequest, @RequestPart("product_img")MultipartFile productImg){
        productService.updateProductById(productId,productRequest,productImg);
    }

    @DeleteMapping("/delete/{product_id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProductById(@PathVariable(value="product_id") final String productId){
        productService.deleteProductById(productId);
    }

    @GetMapping("/get/{product_id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getProductById(@PathVariable(value="product_id") final String productId){
        return productService.findProductById(productId);
    }

    @GetMapping("/get/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }
}
