package testchallenge.store.controller.impl;


import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import testchallenge.store.exception.DataConflictException;
import testchallenge.store.mapper.ProductMapper;
import testchallenge.store.server.controller.ProductsApi;
import testchallenge.store.server.model.Product;
import testchallenge.store.service.ProductService;

@RestController
@RequestMapping("/rest")
@Setter
@Slf4j
public class OrderController implements ProductsApi {
    private static final ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

    @Autowired
    private ProductService productService;

    @Override
    public ResponseEntity<Product> createProductOrder(Long productId, Long quantity) {
        try {
            return ResponseEntity.ok(MAPPER.map(productService.createProductOrder(productId,quantity)));

        } catch (DataConflictException exception) {
            ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(409);//Conflict
            bodyBuilder.body(exception.getMessage());
            return bodyBuilder.build();
        } catch (Exception exception) {
            ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(406);
            bodyBuilder.body(exception.getMessage());
            return bodyBuilder.build();
        }
    }
}
