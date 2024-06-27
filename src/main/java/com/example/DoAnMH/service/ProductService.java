package com.example.DoAnMH.service;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import com.example.DoAnMH.model.Product;
import org.springframework.stereotype.Service;
import com.example.DoAnMH.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public void addProduct( Product product) {
         productRepository.save(product);
    }

    public void UpdateProduct(@NotNull Product product){
        Product existingProduct=productRepository.findById(product.getId()).
                orElseThrow(()->new IllegalArgumentException("NhanVien with Id"+
                        product.getId()+"does not exist."));
        existingProduct.setName(product.getName());
        existingProduct.setCategoryId(product.getCategoryId());
        existingProduct.setImages(product.getImages());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setImagesJson(product.getImagesJson());
        existingProduct.setQuantityStock(product.getQuantityStock());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDiscountId(product.getDiscountId());

    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalStateException("Category with ID " + id + " does not exist.");
        }
        productRepository.deleteById(id);
    }

}
