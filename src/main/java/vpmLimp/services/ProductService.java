package vpmLimp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vpmLimp.DTO.ProductRequest;
import vpmLimp.DTO.ProductResponse;
import vpmLimp.DTO.UpdateProduct;
import vpmLimp.DTO.UpdateProductQuantity;
import vpmLimp.model.ProductModel;
import vpmLimp.model.enums.CategoryProduct;
import vpmLimp.repositories.ProductModelRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductModelRepository productModelRepository;

    public ProductResponse create(ProductRequest productRequest) {
        ProductModel product = new ProductModel();
        product.setName(productRequest.name());
        product.setDescription(productRequest.description());
        product.setPrice(productRequest.price());
        product.setQuantity(productRequest.quantity());

        if (productRequest.category() == null) {
            product.setCategoryProduct(CategoryProduct.CASA);
        } else {
            product.setCategoryProduct(productRequest.category());
        }

        ProductModel savedProduct = productModelRepository.save(product);

        return new ProductResponse(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getDescription(),
                savedProduct.getPrice(),
                savedProduct.getQuantity(),
                savedProduct.getCategoryProduct()
        );
    }


    public ProductResponse updateProduct(UpdateProduct updateProduct, Long id) {
        ProductModel product = productModelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not exists "));

        product.setName(updateProduct.name());
        product.setPrice(updateProduct.price());
        product.setDescription(updateProduct.description());
        product.setQuantity(updateProduct.quantity());

        ProductModel updatedProduct = productModelRepository.save(product);

        return new ProductResponse(
                updatedProduct.getId(),
                updatedProduct.getName(),
                updatedProduct.getDescription(),
                updatedProduct.getPrice(),
                updatedProduct.getQuantity(),
                updatedProduct.getCategoryProduct()
        );
    }

    public ProductResponse updateProductQuantity(Long id, UpdateProductQuantity updateProductQuantity) {
        ProductModel product = productModelRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not exists"));

        product.setQuantity(updateProductQuantity.quantity());

        ProductModel updatedProduct = productModelRepository.save(product);

        return new ProductResponse(
                updatedProduct.getId(),
                updatedProduct.getName(),
                updatedProduct.getDescription(),
                updatedProduct.getPrice(),
                updatedProduct.getQuantity(),
                updatedProduct.getCategoryProduct()

        );
    }

    public void deleteProduct(Long id){
        if(!productModelRepository.existsById(id)){
            throw new NoSuchElementException("Product not exists");
        }

        productModelRepository.deleteById(id);
    }

    public List<ProductResponse> getAllProducts(){
        List<ProductModel> users = productModelRepository.findAll();
        return users.stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> searchProductsByName(String name) {
        List<ProductModel> products = productModelRepository.findByNameContainingIgnoreCase(name);
        return products.stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }
    }

