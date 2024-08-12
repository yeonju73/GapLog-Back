package com.gaplog.server.domain.caterory.api;

import com.gaplog.server.domain.caterory.dto.request.CategorySaveRequest;
import com.gaplog.server.domain.caterory.dto.request.CategoryUpdateRequest;
import com.gaplog.server.domain.caterory.domain.Category;
import com.gaplog.server.domain.caterory.application.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//To Do: closure table을 활용한 쿼리 작성
@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
@Tag(name = "Category", description = "Category API")
public class CategoryApi {

    private final CategoryService categoryService;

    @GetMapping("/{user_id}")
    @Operation(summary = "유저 카테고리 조회", description = "유저의 카테고리 정보를 얻습니다.")
    public ResponseEntity<List<Category>> getCategoriesByUserId(@PathVariable("user_id") Long userId) {
        try {
            List<Category> categories = categoryService.getCategoriesByUserId(userId);
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{category_id}")
    @Operation(summary = "카테고리 게시글 조회", description = "카테고리의 게시글 정보를 얻습니다.")
    // To Do: Post Api 완성 후 Post 반환하도록 변경
    public ResponseEntity<List<Category>> getSubCategories(@PathVariable("category_id") Long categoryId) {
        try {
            List<Category> subCategories = categoryService.getSubCategories(categoryId);
            return new ResponseEntity<>(subCategories, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    @Operation(summary = "카테고리 추가", description = "카테고리를 추가합니다.")
    public ResponseEntity<Category> addCategory(@RequestBody CategorySaveRequest request) {
        try {
            Category newCategory = categoryService.addCategory(request.getUserId(), request.getAncestorId(), request.getName());
            return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/")
    @Operation(summary = "카테고리 수정", description = "카테고리를 수정합니다.")
    public ResponseEntity<Void> updateCategory(@RequestBody CategoryUpdateRequest request) {
        try {
            categoryService.updateCategory(request.getCategoryId(), request.getAncestorId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{category_id}")
    @Operation(summary = "하위 카테고리 삭제", description = "카테고리를 삭제합니다.")
    public ResponseEntity<Void> deleteCategory(@PathVariable("category_id") Long categoryId) {
        try {
            categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
