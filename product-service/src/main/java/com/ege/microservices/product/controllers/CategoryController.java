package com.ege.microservices.product.controllers;

import com.ege.microservices.product.constants.CategoryRestPath;
import com.ege.microservices.product.controllers.requests.CategoryRequestModel;
import com.ege.microservices.product.controllers.responses.CategoryModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = CategoryRestPath.CATEGORY_PATH)
public interface CategoryController {

    @PostMapping(value = CategoryRestPath.CREATE_PATH, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CategoryModel> createCategory(@RequestBody CategoryRequestModel categoryRequestModel);

    @PostMapping(value = CategoryRestPath.DELETE_PATH, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> deleteCategory(@RequestBody CategoryRequestModel categoryRequestModel);

    @PostMapping(value = CategoryRestPath.UPDATE_PATH + "/{categoryName}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CategoryModel> updateCategory(@PathVariable String categoryName, @RequestBody CategoryRequestModel categoryRequestModel);

    @GetMapping(value = CategoryRestPath.GET_BY_NAME_PATH + "/{categoryName}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CategoryModel> getCategoryByName(@PathVariable String categoryName);

    @GetMapping(value = CategoryRestPath.GET_ALL_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<CategoryModel>> getAllCategories();

}
