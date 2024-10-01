package vpmLimp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vpmLimp.DTO.ProductRequest;
import vpmLimp.DTO.ProductResponse;
import vpmLimp.DTO.UpdateProduct;
import vpmLimp.services.ProductService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

        @Autowired
        private ProductService productService;


        @PostMapping
        public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
            ProductResponse productResponse = productService.create(productRequest);
            return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
        }

        @PutMapping("/{id}")
        public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody UpdateProduct updateProduct) {
            ProductResponse productResponse = productService.updateProduct(updateProduct, id);
            return new ResponseEntity<>(productResponse, HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
            productService.deleteProduct(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        @GetMapping

    public ResponseEntity <List<ProductResponse>> getAllProducts(){
            List<ProductResponse> products = productService.getAllProducts();
            return ResponseEntity.ok().body(products);
        }


    }




