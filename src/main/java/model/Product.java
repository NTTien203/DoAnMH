package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @ManyToOne
    @JoinColumn(name = "categoryId")
    Category categoryId;
}
