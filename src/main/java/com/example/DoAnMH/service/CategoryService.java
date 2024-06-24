package com.example.DoAnMH.service;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;


import com.example.DoAnMH.model.Category;
import org.springframework.stereotype.Service;
import com.example.DoAnMH.repository.CategoryRepository;


import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public void addCategory( Category category) {
        categoryRepository.save(category);
    }

    public void UpdateCategory(@NotNull Category category){
        Category existingCategory=categoryRepository.findById(category.getId()).
                orElseThrow(()->new IllegalArgumentException("NhanVien with Id"+
                        category.getId()+"does not exist."));

        existingCategory.setId(category.getId());
        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());
    }

    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new IllegalStateException("Category with ID " + id + " does not exist.");
        }
        categoryRepository.deleteById(id);
    }
}
