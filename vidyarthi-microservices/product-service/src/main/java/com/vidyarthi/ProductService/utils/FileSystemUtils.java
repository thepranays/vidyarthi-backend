package com.vidyarthi.ProductService.utils;

import com.vidyarthi.ProductService.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

/*
utility class used to store files in file system
mainly images
 */
@Slf4j
public class FileSystemUtils {
    //Store product img in file system
    public static String StoreProductImage(MultipartFile imageFile,String productId) {
            if(imageFile.isEmpty()) return null; // No Image file given
            String productImagePath= Constants.FILE_PRODUCTS_PATH+"/"+productId+"/product_img";
            try{
                boolean createdDirectories=new File(productImagePath).mkdirs(); //Create nested directories to save our final file (productImg)
                if(!createdDirectories){ //Failed expectedly
                    throw new IOException("Error while creating nested directories to save our final file(ProductImg)");
                }
                int fileName= Objects.requireNonNull(new File(productImagePath).list()).length; //number of files at product image path
                String[] splittedAtDot=imageFile.getOriginalFilename().split("\\."); // {\\} used to escape regex . (which means match one character(any) except newline)
                String fileExtension=splittedAtDot[splittedAtDot.length-1]; //Eg. abcd.jpg -> [abcd,jpg]
                FileOutputStream fos=new FileOutputStream(productImagePath+"/"+ fileName+"."+fileExtension); //open stream at given path
                fos.write(imageFile.getBytes()); //write bytes
                fos.flush(); //Flush bytes from stream //The flush() method is primarily used to force any buffered data to be written immediately without closing the FileWriter, while the close() method both performs flushing and releases associated resources.
                fos.close(); //close stream to avoid memory leak
                return fileName+"."+fileExtension; //return file name stored at product image path
            }catch(IOException e){
                return null;
            }
    }
    //Delete product img from file system
    public static void DeleteProductImage(String fileFullName,String productId){
        String productImagePath= Constants.FILE_PRODUCTS_PATH+"/"+productId+"/product_img";

        //true if deleted, false if not
        boolean isDeleted= new File(productImagePath+"/"+fileFullName).delete();
        if(isDeleted){
            log.info("Deleted product image in file system.");
        }else{
            log.info("Deleting product image in file system failed.");
        }

    }

    //Overwrite existing product img
    public static void OverwriteProductImage(MultipartFile imageFile,String fileFullName,String productId){
        if(imageFile.isEmpty()) return; //Avoiding overwriting with no bytes, to avoid deleting the file
        String productImagePath= Constants.FILE_PRODUCTS_PATH+"/"+productId+"/product_img";
        try(FileOutputStream fos=new FileOutputStream(productImagePath+"/"+fileFullName)){
            fos.write(imageFile.getBytes());
            fos.flush();
            //fos.close(); as we used try with resources
        }catch(IOException e){
            log.info("Overwriting product image in file system failed."+e.getMessage());

        }
    }
    //Retrieve product img from file system
    public static byte[] RetrieveProductImage(String fileFullName,String productId){ //xyz.xyz
        String productImagePath= Constants.FILE_PRODUCTS_PATH+"/"+productId+"/product_img";
        try {
            byte[] bytes = Files.readAllBytes(new File(productImagePath + "/" + fileFullName).toPath());
            return bytes;
        }catch(IOException e){
            log.info("Storing product image in file system failed."+e.getMessage());
            return null;
        }
    }

}
