package com.th.hab.category;

import com.th.hab.Repository.CategoryRepository;
import com.th.hab.entity.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getCategory() {
        return categoryRepository.findAll();
    }
}
