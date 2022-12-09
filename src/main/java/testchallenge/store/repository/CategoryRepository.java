package testchallenge.store.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import testchallenge.store.domain.entity.Category;
import testchallenge.store.domain.entity.ProductEntity;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<ProductEntity, Long> {
    @Query("SELECT " +
            "    new testchallenge.store.domain.entity.Category(p.category, COUNT(p)) " +
            " FROM ProductEntity p " +
            " WHERE  p.quantity > 0 " +
            " GROUP BY  p.category " +
            " ORDER BY  p.category ")
    List<Category> findCategories();
}
