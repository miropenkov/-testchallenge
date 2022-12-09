package testchallenge.store.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "product", schema = "testchallenge")
@Data
public class ProductEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;
	
	@Column(name = "description")
    private String description;
	
	@Column(name = "quantity")
    private Long quantity;
	
	@Column(name = "created")
    private OffsetDateTime created;
	
	@Column(name = "modified")
    private OffsetDateTime modified;
}
