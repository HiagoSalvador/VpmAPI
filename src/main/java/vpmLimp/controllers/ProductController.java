package vpmLimp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vpmLimp.DTO.ProductRequest;
import vpmLimp.DTO.ProductResponse;
import vpmLimp.services.ProductService;

import java.io.IOException;

@RestController
@RequestMapping("/api/products")
public class ProductController {

        @Autowired
        private ProductService productService;


        @PostMapping
        public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
            ProductResponse productResponse = productService.create(productRequest);
            return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
        }


    }




