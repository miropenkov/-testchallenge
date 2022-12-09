package testchallenge.store.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import testchallenge.store.domain.entity.ProductEntity;
import testchallenge.store.domain.entity.ProductEntity_;

import javax.persistence.criteria.Predicate;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {


//    List<ProductEntity> findAllByGroupId(Long groupId);
//    List<ProductEntity> findAllByGroupIdAndType(Long groupId, ChatRoomType type);
//    Optional<ProductEntity> findByChatRoomIdAndType(Long chatRoomId, ChatRoomType type);
//    Optional<ProductEntity> findOneByGroupIdAndType(Long groupId,ChatRoomType type);

    default Page<ProductEntity> findAllByParams(Long productId,
                                                String name,
                                                String category,
                                                String description,
                                                String orderBy,
                                                String sortDirection,
                                                int page, int size) {

        Sort sort = sortDirection.equalsIgnoreCase("ASC") ?Sort.by(orderBy).ascending() : Sort.by(orderBy).descending();
        Pageable pageble= PageRequest.of(page, size, Sort.by("name").descending());
        return findAll((root, query, builder) -> {
                    List<Predicate> p = new LinkedList<>();
                    if (Objects.nonNull(productId)) {
                        p.add(builder.equal(root.get(ProductEntity_.Id), productId));
                    }
                    if (Objects.nonNull(name)) {
                        p.add(builder.like(root.get(ProductEntity_.name), "%" + name + "%"));
                    }
                    if (Objects.nonNull(category)) {
                        p.add(builder.like(root.get(ProductEntity_.category), "%" + category+ "%"));
                    }
                    if (Objects.nonNull(description)) {
                        p.add(builder.like(root.get(ProductEntity_.description), "%" + description+ "%"));
                    }

                    return builder.and(p.toArray(new Predicate[0]));
                },
                PageRequest.of(page, size)
        );
    }
}
