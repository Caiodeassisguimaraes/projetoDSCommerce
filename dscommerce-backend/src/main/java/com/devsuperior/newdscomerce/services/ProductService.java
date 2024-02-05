package com.devsuperior.newdscomerce.services;

import com.devsuperior.newdscomerce.dto.CategoryDto;
import com.devsuperior.newdscomerce.dto.ProductDto;
import com.devsuperior.newdscomerce.dto.ProductMinDto;
import com.devsuperior.newdscomerce.entities.Category;
import com.devsuperior.newdscomerce.entities.Product;
import com.devsuperior.newdscomerce.repositories.ProductRepository;
import com.devsuperior.newdscomerce.services.exceptions.DatabaseException;
import com.devsuperior.newdscomerce.services.exceptions.ResourseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repositoty;
    @Transactional(readOnly = true)
    public ProductDto findById(Long id){

        // Código com Exceções não tratadas
        /*Optional <Product> result = repositoty.findById(id);
        Product product = result.get();
        ProductDto dto = new ProductDto(product);
        return dto;*/

        //Codigo abreviado com Exceções não tratadas
        /*Product product = repositoty.findById(id).get();
        return new ProductDto(product);*/

        Product product = repositoty.findById(id).orElseThrow(() -> new ResourseNotFoundException("Recurso não encontrado"));
        return new ProductDto(product);

    }

    @Transactional(readOnly = true)
    public Page<ProductMinDto> findAll(String name, Pageable pageable){
        Page<Product> result = repositoty.searchByName(name, pageable);
        return result.map(registry -> new ProductMinDto(registry));
    }

    @Transactional
    public ProductDto insert(ProductDto dto){
        Product entity = new Product();
        copyDtoToEntity(dto, entity);
        entity = repositoty.save(entity);
        return new ProductDto(entity);
    }

    @Transactional
    public ProductDto update(Long id, ProductDto dto){
        try{
            Product entity = repositoty.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            return new ProductDto(entity);
        }
        catch (EntityNotFoundException e){
            throw new ResourseNotFoundException("Recurso não encontrado");
        }

    }
    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id){
        try {
          repositoty.deleteById(id);
        }
        catch (EmptyResultDataAccessException e){
            throw new ResourseNotFoundException("Recurso não encontrado");
        }
        catch(DataIntegrityViolationException e){
            throw new DatabaseException("Violação de Integridade Referencial");
        }
    }

    private void copyDtoToEntity(ProductDto dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
        entity.getCategories().clear();
        for (CategoryDto catDto : dto.getCategories()){
            Category cat = new Category();
            cat.setId(catDto.getId());
            entity.getCategories().add(cat);
        }
    }

}
