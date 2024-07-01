package com.example.DoAnMH.service;

import com.example.DoAnMH.model.Category;
import com.example.DoAnMH.model.Discount;
import com.example.DoAnMH.repository.CategoryRepository;
import com.example.DoAnMH.repository.DiscountRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DiscountService {
    private final DiscountRepository discountRepository;
    public List<Discount> getAllDiscount(){
        return discountRepository.findAll();
    }


    public Optional<Discount> getCategoryById(Long id) {
        return discountRepository.findById(id);
    }

    public void addCategory( Discount discount) {
        discountRepository.save(discount);
    }

    public void UpdateDiscount(@NotNull Discount discount){
        Discount existingDiscount=discountRepository.findById(discount.getId()).
                orElseThrow(()->new IllegalArgumentException("NhanVien with Id"+
                        discount.getId()+"does not exist."));

        existingDiscount.setId(discount.getId());
        existingDiscount.setDiscount(discount.getDiscount());
    }

    public void deleteDiscount(Long id) {
        if (!discountRepository.existsById(id)) {
            throw new IllegalStateException("Category with ID " + id + " does not exist.");
        }
        discountRepository.deleteById(id);
    }

}
