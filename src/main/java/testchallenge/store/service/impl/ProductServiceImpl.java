package testchallenge.store.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import testchallenge.store.domain.entity.Category;
import testchallenge.store.domain.entity.ProductEntity;
import testchallenge.store.exception.DataConflictException;
import testchallenge.store.repository.CategoryRepository;
import testchallenge.store.repository.ProductRepository;
import testchallenge.store.service.ProductService;

import java.time.OffsetDateTime;
import java.util.*;

@Component
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Optional<ProductEntity> getProductEntity(Long productId) {
        return productRepository.findById(productId);
    }


    @Override
    public ProductEntity createProductEntity(String name, String category, String description, Long quantity) {
        ProductEntity entity = new ProductEntity();
        entity.setName(name);
        entity.setCategory(category);
        entity.setDescription(description);
        entity.setQuantity(quantity);
        entity.setCreated(OffsetDateTime.now());
        entity.setModified(OffsetDateTime.now());
        return productRepository.save(entity);
    }


    @Override
    public ProductEntity updateProductEntity(Long productId, String name, String category, String description, Long quantity) {
        ProductEntity entity = getProductEntity(productId).orElse(null);
        if(Objects.isNull(entity)) {
            return  createProductEntity( name,  category,  description,  quantity);
        }
        if(Objects.nonNull(name)) {
            entity.setName(name);
        }
        if(Objects.nonNull(category)) {
            entity.setCategory(category);
        }
        if(Objects.nonNull(description)) {
            entity.setDescription(description);
        }
        if(Objects.nonNull(quantity)) {
            entity.setQuantity(quantity);
        }
        entity.setModified(OffsetDateTime.now());
        return productRepository.save(entity);
    }

    @Override
    public Page<ProductEntity> getProductEntitys(String orderBy, String sortDirection,int page, int size) {
        return productRepository.findAllByParams(null,null,null,null,orderBy, sortDirection,page,size);
    }

    @Override
    public ProductEntity createProductOrder(Long productId, Long quantity) throws Exception {
        ProductEntity entity = getProductEntity(productId).orElse(null);
        if(Objects.isNull(entity)) {
            return  null;
        }
        if(entity.getQuantity()-quantity<0) { //409 Conflict
            throw new DataConflictException("The quantity exceed product availability");
        }
        entity.setQuantity(entity.getQuantity()-quantity);
        return productRepository.save(entity);
    }

    @Override
    public List<Category> getProductCategories() {
        return categoryRepository.findCategories();
    }

    @Override
    public boolean deleteProduct(Long productId) {
        ProductEntity entity = getProductEntity(productId).orElse(null);
        if(Objects.isNull(entity)) {
            return  false;
        }
        try {
            productRepository.delete(entity);
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return  false;
    }
}
