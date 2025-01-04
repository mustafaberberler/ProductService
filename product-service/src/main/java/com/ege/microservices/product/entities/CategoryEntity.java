package com.ege.microservices.product.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "all_categories")
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String categoryId;

    private String categoryName;

//    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
//    private List<ProductEntity> products;

}
