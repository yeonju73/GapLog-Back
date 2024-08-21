package com.gaplog.server.domain.category.application;

import com.gaplog.server.domain.category.dao.ClosureCategoryRepository;
import com.gaplog.server.domain.category.domain.Category;
import com.gaplog.server.domain.category.dao.CategoryRepository;
import com.gaplog.server.domain.category.domain.ClosureCategory;
import com.gaplog.server.domain.category.dto.response.CategoryTreeResponse;
import com.gaplog.server.domain.post.dao.PostRepository;
import com.gaplog.server.domain.post.domain.Post;
import com.gaplog.server.domain.user.domain.User;
import com.gaplog.server.domain.user.dao.UserRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ClosureCategoryRepository closureCategoryRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public List<CategoryTreeResponse> getCategoryTree(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        // 유저의 모든 카테고리 id 조회
        List<Category> categories = categoryRepository.findByUser(user.get());
        List<Long> categoryIds = categories.stream()
                .map(Category::getId)
                .toList();

        // 루트 노드 조회
        List<Long> rootCategoryIds = categoryIds.stream()
                .filter(categoryId -> {
                    Long descendantCount = closureCategoryRepository.countByIdDescendantId(categoryId);
                    return descendantCount == 1;  // 자기 자신만 참조하는 경우 (depth = 0)
                })
                .toList();

        // depth가 1인 부모-자식 관계 조회
        List<ClosureCategory> closureCategories = closureCategoryRepository.findByIdAncestorIdInAndDepth(categoryIds, 1L);

        // 트리 매핑
        Map<Long, CategoryTreeResponse> categoryMap = categories.stream()
                .collect(Collectors.toMap(
                        Category::getId,
                        category -> new CategoryTreeResponse(category.getId(), category.getName(), new ArrayList<>())
                ));

        closureCategories.stream()
                .filter(closureCategory -> !closureCategory.getId().getAncestorId().equals(closureCategory.getId().getDescendantId()))
                .forEach(closureCategory -> {
                    Long parentId = closureCategory.getId().getAncestorId();
                    Long childId = closureCategory.getId().getDescendantId();
                    categoryMap.get(parentId).getChildren().add(categoryMap.get(childId));
                });

        // 루트 노드들의 트리 반환
        return rootCategoryIds.stream()
                .map(categoryMap::get)
                .toList();
    }

    public List<Post> getSubCategories(Long categoryId) {
        Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
        if (categoryOpt.isEmpty()) {
            throw new RuntimeException("Category not found with id: " + categoryId);
        }
        List<ClosureCategory> subCategories = closureCategoryRepository.findByIdAncestorId(categoryId);

        return subCategories.stream()
                .flatMap(subCategory -> {
                    Long descendantId = subCategory.getId().getDescendantId();
                    return postRepository.findByCategoryId(descendantId).stream();
                })
                .toList();
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
            ClosureCategory closureCategory = ClosureCategory.of(parentCategory, savedCategory, 1L);
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
