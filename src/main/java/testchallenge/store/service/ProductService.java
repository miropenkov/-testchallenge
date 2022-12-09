package testchallenge.store.service;


import org.springframework.data.domain.Page;
import testchallenge.store.domain.entity.Category;
import testchallenge.store.domain.entity.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<ProductEntity> getProductEntity(Long productId);

    ProductEntity createProductEntity(String name, String category, String description, Long quantity);

    ProductEntity updateProductEntity(Long productId, String name, String category, String description, Long quantity);

    Page<ProductEntity> getProductEntitys(String orderBy, String sortDirection,int page, int size);

    List<Category> getProductCategories();

    ProductEntity createProductOrder(Long productId, Long quantity) throws Exception;

    boolean deleteProduct(Long productId);

}

