package model;

import lombok.*;
import java.util.Objects;

@Data
@NoArgsConstructor
public abstract class Category {
    private String name;

    public Category(String name) {
        this.name = Objects.requireNonNull(name, "Category name cannot be null");
    }
}

