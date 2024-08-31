package com.th.hab.Category;

import com.th.hab.entity.Category;
import com.th.hab.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService service;

    @GetMapping
    public ApiResponse<List<Category>> getCategory() {
        return new ApiResponse<>(service.getCategory());
    }
}
