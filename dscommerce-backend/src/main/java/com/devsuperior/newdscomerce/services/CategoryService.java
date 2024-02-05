package com.devsuperior.newdscomerce.services;

import com.devsuperior.newdscomerce.dto.CategoryDto;
import com.devsuperior.newdscomerce.entities.Category;
import com.devsuperior.newdscomerce.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repositoty;

    @Transactional(readOnly = true)
    public List<CategoryDto> findAll(){
        List<Category> result = repositoty.findAll();
        return result.stream().map(registry -> new CategoryDto(registry)).toList();
    }

}
