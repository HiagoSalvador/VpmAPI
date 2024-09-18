package vpmLimp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vpmLimp.DTO.ProductRequest;
import vpmLimp.DTO.ProductResponse;
import vpmLimp.model.ProductModel;
import vpmLimp.repositories.ProductModelRepository;

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
}
