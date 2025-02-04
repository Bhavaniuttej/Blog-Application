package com.springboot.blog.controller;

import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@Tag(
        name="CRUD REST APIs for Category Resource"
)
public class CategoryController
{
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService)
    {
        this.categoryService = categoryService;
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto)
    {
        CategoryDto savedCategory = categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    //Build Get Category Rest Api

    @GetMapping("{id}")
    public ResponseEntity<CategoryDto>  getCategory(@PathVariable("id") Long categoryId)
    {
        CategoryDto categoryDto=categoryService.getCategory(categoryId);

        return ResponseEntity.ok(categoryDto);
    }

    //Build All Categories Rest Apis

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories()
    {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    //Build Rest Api for Update Category

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,
                                                      @PathVariable("id") Long categoryId)
    {
        return ResponseEntity.ok(categoryService.updateCategory(categoryDto,categoryId));
    }

    //Build Delete Category Rest Api

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long categoryId)
    {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("Category Deleted Successfully");
    }
}
