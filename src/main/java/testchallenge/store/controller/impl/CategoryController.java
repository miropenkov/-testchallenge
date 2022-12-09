package testchallenge.store.controller.impl;


import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import testchallenge.store.mapper.ProductMapper;
import testchallenge.store.server.controller.CategoriesApi;
import testchallenge.store.server.controller.ProductApi;
import testchallenge.store.server.model.*;
import testchallenge.store.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest")
@Setter
@Slf4j
public class CategoryController implements CategoriesApi {
    private static final ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

    @Autowired
    private ProductService productService;

    @Override
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> listCategory = productService.getProductCategories().stream().map(MAPPER::map).collect(Collectors.toList());
        return ResponseEntity.ok(listCategory);
    }
}
