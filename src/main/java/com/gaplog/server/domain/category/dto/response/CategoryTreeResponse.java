package com.gaplog.server.domain.category.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryTreeResponse {
    private Long id;
    private String name;
    private List<CategoryTreeResponse> children;
}
