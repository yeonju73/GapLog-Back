package com.gaplog.server.domain.caterory.application;

import com.gaplog.server.domain.caterory.dao.ClosureCategoryRepository;
import com.gaplog.server.domain.caterory.domain.Category;
import com.gaplog.server.domain.caterory.dao.CategoryRepository;
import com.gaplog.server.domain.caterory.domain.ClosureCategory;
import com.gaplog.server.domain.user.domain.User;
import com.gaplog.server.domain.user.dao.UserRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ClosureCategoryRepository closureCategoryRepository;
    private final UserRepository userRepository;

    public List<Category> getCategoriesByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return categoryRepository.findByUser(user.get());
        } else {
            throw new RuntimeException("User not found with id: " + userId);
        }
    }

    public List<Category> getSubCategories(Long categoryId) {
        Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
        if (categoryOpt.isEmpty()) {
            throw new RuntimeException("Category not found with id: " + categoryId);
        }
        List<ClosureCategory> closureCategories = closureCategoryRepository.findByIdAncestorId(categoryId);

        return closureCategories.stream()
                .map(closureCategory -> {
                    Long descendantId = closureCategory.getId().getDescendantId();
                    return categoryRepository.findById(descendantId).orElse(null);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public Category addCategory(Long userId, Long parentId, String name) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found with id: " + userId);
        }
        User user = userOpt.get();

        Category newCategory = Category.of(name, user);
        Category savedCategory = categoryRepository.save(newCategory);

        Optional<Category> parentCategoryOpt = categoryRepository.findById(parentId);
        if (parentCategoryOpt.isPresent()) {
            Category parentCategory = parentCategoryOpt.get();
            //To Do: depth 계산 로직 추가
            ClosureCategory closureCategory = ClosureCategory.of(parentCategory, savedCategory, 1);
            closureCategoryRepository.save(closureCategory);
        }
        return savedCategory;
    }

    public void updateCategory(Long categoryId, Long ancestorId) {
        Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
        if (categoryOpt.isEmpty()) {
            throw new RuntimeException("Category not found with id: " + categoryId);
        }

        Category category = categoryOpt.get();

        Optional<Category> ancestorCategoryOpt = categoryRepository.findById(ancestorId);
        if (ancestorCategoryOpt.isEmpty()) {
            throw new RuntimeException("Ancestor category not found with id: " + ancestorId);
        }

        Category ancestorCategory = ancestorCategoryOpt.get();

        //To Do: 수정 로직 세부 구현
    }


    public void deleteCategory(Long categoryId) {
        Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
        if (categoryOpt.isEmpty()) {
            throw new RuntimeException("Category not found with id: " + categoryId);
        }

        Category category = categoryOpt.get();
        List<ClosureCategory> closureCategories = closureCategoryRepository.findByIdAncestorId(categoryId);
        closureCategoryRepository.deleteAll(closureCategories);
        categoryRepository.delete(category);
    }
}
