package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpenseCategory extends Category {
    private double limit;
    private double spent;

    public ExpenseCategory(String name, double limit) {
        super(name);
        if (limit < 0) {
            throw new IllegalArgumentException("Лимит бюджета не может быть отрицательным");
        }
        this.limit = limit;
        this.spent = 0.0;
    }

    /**
     * Метод для добавления расхода в категорию
     *
     * @param amount Сумма расхода
     */
    public void addSpent(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Расход не может быть отрицательным");
        }
        this.spent += amount;
    }

    /**
     * Метод для получения оставшегося лимита
     *
     * @return Оставшийся лимит
     */
    public double getRemaining() {
        return limit - spent;
    }

    /**
     * Метод для проверки превышения бюджета
     *
     * @return true, если расходы превысили лимит
     */
    public boolean isOverBudget() {
        return spent > limit;
    }
}
