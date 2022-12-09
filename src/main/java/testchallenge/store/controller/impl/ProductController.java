package testchallenge.store.controller.impl;


import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import testchallenge.store.domain.entity.ProductEntity;
import testchallenge.store.mapper.ProductMapper;
import testchallenge.store.server.controller.*;
import testchallenge.store.server.model.*;
import testchallenge.store.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest")
@Setter
@Slf4j
public class ProductController implements ProductApi {
    private static final ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

    @Autowired
    private ProductService productService;

    @Override
    public ResponseEntity<Product> createProduct(RequestCreateProduct request) {
        Product product = null;
        try {
            product = MAPPER.map(productService.createProductEntity(request.getName(),request.getCategory(),request.getDescription(),request.getQuantity()));
        } catch (Exception exception) {
            //exception.printStackTrace();
            log.error(exception.getMessage(),exception);
            ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(406); //Not Acceptable
            bodyBuilder.body(exception.getMessage());
            return bodyBuilder.build();
        }
        return ResponseEntity.ok(product);
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Long productId) {
        try {
            productService.deleteProduct(productId);
        } catch (Exception exception) {
            //exception.printStackTrace();
            log.error(exception.getMessage(),exception);
            ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(406); //Not Acceptable
            bodyBuilder.body(exception.getMessage());
            return bodyBuilder.build();
        }
        return ResponseEntity.status(200).build();
    }

    @Override
    public ResponseEntity<Product> getProduct(Long productId) {
        Product product = null;
        try {
            product = MAPPER.map(productService.getProductEntity(productId).orElse(null));
        } catch (Exception exception) {
            //exception.printStackTrace();
            log.error(exception.getMessage(),exception);
            ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(406); //Not Acceptable
            bodyBuilder.body(exception.getMessage());
            return bodyBuilder.build();
        }
        if(Objects.isNull(product)){
            ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(404); //Not Found
            bodyBuilder.body("The product not found.");
            return bodyBuilder.build();
        }
        return ResponseEntity.ok(product);
    }

    @Override
    public ResponseEntity<ResponseGetProducts> getProducts(String direction, String orderBy, Integer page, Integer pageSize) {
        Page<ProductEntity> pageProduct = null;
        try {
            pageProduct = productService.getProductEntitys(orderBy,direction,page, pageSize);
        } catch (Exception exception) {
            //exception.printStackTrace();
            log.error(exception.getMessage(),exception);
            ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(406); //Not Acceptable
            bodyBuilder.body(exception.getMessage());
            return bodyBuilder.build();
        }
        ResponseGetProducts response = new ResponseGetProducts();
        response.setItems(pageProduct.get().map(MAPPER::map).collect(Collectors.toList()));
        response.setTotalRecords(pageProduct.getTotalElements());
        response.setPage(pageProduct.getNumber());
        response.setPageSize(pageProduct.getSize());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Product> updateProduct(Long productId, RequestUpdateProduct request) {
        Product product = null;
        try {
            product = MAPPER.map(productService.updateProductEntity(productId,request.getName(),request.getCategory(),request.getDescription(),request.getQuantity()));
        } catch (Exception exception) {
            //exception.printStackTrace();
            log.error(exception.getMessage(),exception);
            ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(406); //Not Acceptable
            bodyBuilder.body(exception.getMessage());
            return bodyBuilder.build();
        }
        return ResponseEntity.ok(product);
    }
}
