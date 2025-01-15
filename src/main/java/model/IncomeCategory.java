package model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IncomeCategory extends Category {
    public IncomeCategory(String name) {
        super(name);
    }
}
