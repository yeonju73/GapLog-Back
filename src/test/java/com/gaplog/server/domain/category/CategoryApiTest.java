package com.gaplog.server.domain.category;

import com.gaplog.server.domain.category.api.CategoryApi;
import com.gaplog.server.domain.category.application.CategoryService;
import com.gaplog.server.domain.category.dto.request.CategorySaveRequest;
import com.gaplog.server.domain.category.dto.response.CategoryTreeResponse;
import com.gaplog.server.domain.category.domain.Category;
import com.gaplog.server.domain.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryApi.class)
@WithMockUser
class CategoryApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    void getCategoryTree_ShouldReturnCategoryTree() throws Exception {
        // Given
        List<CategoryTreeResponse> categoryTree = Arrays.asList(
                new CategoryTreeResponse(1L, "Category 1", List.of()),
                new CategoryTreeResponse(2L, "Category 2", List.of())
        );
        when(categoryService.getCategoryTree(1L)).thenReturn(categoryTree);

        // When & Then
        mockMvc.perform(get("/api/category/users/1").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Category 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Category 2"));

        verify(categoryService, times(1)).getCategoryTree(1L);
    }

    @Test
    void getSubCategories_ShouldReturnPosts() throws Exception {
        // To Do : Given
        // when(categoryService.getSubCategories(1L)).thenReturn(posts);

        // When & Then
        mockMvc.perform(get("/api/category/1").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("First Post"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].title").value("Second Post"));

        verify(categoryService, times(1)).getSubCategories(1L);
    }

    @Test
    void addCategory_ShouldCreateCategory() throws Exception {
        // Given
        CategorySaveRequest request = new CategorySaveRequest(1L, 0L, "New Category");
        Category newCategory = Category.of("New Category", new User(1L, "User"));
        when(categoryService.addCategory(1L, 0L, "New Category")).thenReturn(newCategory);

        // When & Then
        mockMvc.perform(post("/api/category/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"ancestorId\":0,\"name\":\"New Category\"}").with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("New Category"));

        verify(categoryService, times(1)).addCategory(1L, 0L, "New Category");
    }

    @Test
    void updateCategory_ShouldUpdateCategory() throws Exception {
        // When & Then
        mockMvc.perform(put("/api/category/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"categoryId\":1,\"ancestorId\":2}").with(csrf()))
                .andExpect(status().isOk());

        verify(categoryService, times(1)).updateCategory(1L, 2L);
    }

    @Test
    void deleteCategory_ShouldDeleteCategory() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/category/1").with(csrf()))
                .andExpect(status().isOk());

        verify(categoryService, times(1)).deleteCategory(1L);
    }
}

