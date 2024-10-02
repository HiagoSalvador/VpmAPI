package vpmLimp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vpmLimp.DTO.ProductRequest;
import vpmLimp.DTO.ProductResponse;
import vpmLimp.DTO.UpdateProduct;
import vpmLimp.DTO.UserResponse;
import vpmLimp.model.ProductModel;
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
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());

        ProductModel savedProduct = productModelRepository.save(product);

        return new ProductResponse(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getDescription(),
                savedProduct.getPrice()
        );
    }

    public ProductResponse updateProduct(UpdateProduct updateProduct, Long id){
        ProductModel product = productModelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not exists "));
        product.setName(updateProduct.getName());
        product.setPrice(updateProduct.getPrice());
        product.setDescription(updateProduct.getDescription());

        return new ProductResponse(productModelRepository.save(product));

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

    }

