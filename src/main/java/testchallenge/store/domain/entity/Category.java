package testchallenge.store.domain.entity;

import lombok.Data;
import org.springframework.context.annotation.Bean;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
public class Category {
    private String name;
    private Long quantity;

    public Category(String name, Long quantity) {
        this.name = name;
        this.quantity  = quantity;
    }
}
