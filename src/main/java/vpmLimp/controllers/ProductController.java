package vpmLimp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vpmLimp.DTO.ProductRequest;
import vpmLimp.DTO.ProductResponse;
import vpmLimp.DTO.UpdateProduct;
import vpmLimp.DTO.UpdateProductQuantity;
import vpmLimp.services.ProductService;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

        @Autowired
        private ProductService productService;


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok().body(productService.create(productRequest));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody UpdateProduct updateProduct) {
        return ResponseEntity.ok().body(productService.updateProduct(updateProduct, id));
    }

    @PutMapping("/quantity/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> updateProductQuantity(@PathVariable Long id, @RequestBody UpdateProductQuantity updateProductQuantity) {
        return ResponseEntity.ok().body(productService.updateProductQuantity(id, updateProductQuantity));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok().body(productService.getAllProducts());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String name) {
        List<ProductResponse> products = productService.searchProductsByName(name);
        return ResponseEntity.ok(products);
    }


    }




