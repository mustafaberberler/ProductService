package com.ege.microservices.product.repositories;

import com.ege.microservices.product.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {
    void deleteCategoryByCategoryName(String categoryName);

    CategoryEntity findCategoryEntityByCategoryName(String categoryName);
}
