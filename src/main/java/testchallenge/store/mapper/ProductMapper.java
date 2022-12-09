package testchallenge.store.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import testchallenge.store.domain.entity.Category;
import testchallenge.store.domain.entity.ProductEntity;

import java.util.List;

@Mapper
public interface ProductMapper {
//    @Mappings({
//            @Mapping(source = "Id", target = "id")
//    })
    testchallenge.store.server.model.Product map(ProductEntity source);

    testchallenge.store.server.model.Category map(Category source);

}