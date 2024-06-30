package com.example.DoAnMH.service;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import com.example.DoAnMH.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public List<Product> getProductsSortedByDiscount() {
        return productRepository.findAllByOrderByDiscountDesc();
    }
    public List<Product> getProductsByCategoryName(String categoryName) {
        return productRepository.findAllByCategoryName(categoryName);
    }
    public void UpdateProduct(@NotNull Product product){
        Product existingProduct=productRepository.findById(product.getId()).
                orElseThrow(()->new IllegalArgumentException("NhanVien with Id"+
                        product.getId()+"does not exist."));
        existingProduct.setName(product.getName());
        existingProduct.setCategoryId(product.getCategoryId());
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

    public Page<Product> findPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }
//    public Page<Product> getProductsByCategoriesAndPrice(List<String> categories, Double price, int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        if (categories != null && price != null) {
//            return productRepository.findByCategoryInAndPriceOrderByPriceDesc(categories, price, pageable);
//        } else {
//            return productRepository.findAll(pageable);
//        }
//    }

}
