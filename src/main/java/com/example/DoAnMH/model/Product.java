package com.example.DoAnMH.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Hãy nhập tên sản phẩm!!")
    private String name;

    @NotNull(message = "Xin hãy thêm ảnh!!")
    private List<String> images;

    @NotNull(message = "Hãy thêm số lượng tồn!!")
    private Integer quantityStock;

    @NotBlank(message = "Mô tả sản phẩm!!!")
    private String description;

    @NotNull(message = "Cập nhật giá bán!!")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    Category categoryId;

    @ManyToOne
    @JoinColumn(name = "discountId")
    Discount discountId;

    private String imagesJson;

    public void convertImagesToJson(List<String>im) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.imagesJson = objectMapper.writeValueAsString(im);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public List<String> convertJsonToImages() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(this.imagesJson, objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
