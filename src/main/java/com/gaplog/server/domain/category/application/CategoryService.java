package com.gaplog.server.domain.category.application;

import com.gaplog.server.domain.category.dao.ClosureCategoryRepository;
import com.gaplog.server.domain.category.domain.Category;
import com.gaplog.server.domain.category.dao.CategoryRepository;
import com.gaplog.server.domain.category.domain.ClosureCategory;
import com.gaplog.server.domain.category.domain.ClosureCategoryId;
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
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found with id: " + userId));

        // 유저의 모든 카테고리 id 조회
        List<Category> categories = categoryRepository.findByUser(user);
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
        List<ClosureCategory> subCategories = closureCategoryRepository.findByIdAncestorId(categoryId);

        return subCategories.stream()
                .flatMap(subCategory -> {
                    Long descendantId = subCategory.getId().getDescendantId();
                    return postRepository.findByCategoryId(descendantId).stream();
                })
                .toList();
    }

    public Category addCategory(Long userId, Long ancestorId, String name) {
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found with id: " + userId));

        Category newCategory = Category.of(name, user);
        Category savedCategory = categoryRepository.save(newCategory);
        
        saveCategory(savedCategory, ancestorId);
        return savedCategory;
    }

    private void saveCategory(Category category, Long ancestorId) {
        ClosureCategory selfNodeClosure = ClosureCategory.of(category,category,0L);
        closureCategoryRepository.save(selfNodeClosure);

        List<ClosureCategory> allAncestorClosures = closureCategoryRepository.findByIdDescendantId(ancestorId);
        for (ClosureCategory ancestorClosure : allAncestorClosures) {
            Long depth = ancestorClosure.getDepth() + 1;
            ClosureCategory newClosureCategory = ClosureCategory.builder()
                    .id(new ClosureCategoryId(ancestorClosure.getId().getAncestorId(), category.getId()))
                    .depth(depth)
                    .build();
            closureCategoryRepository.save(newClosureCategory);
        }
    }

    public void updateCategory(Long categoryId, Long ancestorId) {
        deleteCategory(categoryId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new RuntimeException("Category not found with id: " + categoryId));
        saveCategory(category, ancestorId);
    }

    public void deleteCategory(Long categoryId) {
        // 자신을 포함한 자손들의 모든 조상과의 연결 관계 삭제
        List<ClosureCategory> allDescendantClosures = closureCategoryRepository.findByIdAncestorId(categoryId);
        for(ClosureCategory descendantClosure : allDescendantClosures) {
            Long descendantId = descendantClosure.getId().getDescendantId();
            List<ClosureCategory> allAncestorClosures = closureCategoryRepository.findByIdDescendantId(descendantId);
            closureCategoryRepository.deleteAll(allAncestorClosures);

            Category deleteCategory = categoryRepository.findById(descendantId).orElseThrow(() -> new RuntimeException("Category not found with id: " + descendantId));
            categoryRepository.delete(deleteCategory);
        }
    }
}
