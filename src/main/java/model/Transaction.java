package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.Objects;

@Data
public class Transaction {
    public enum Type {
        INCOME,
        EXPENSE
    }

    private Type type;
    private double amount;
    private String category;


    @JsonCreator
    public Transaction(
            @JsonProperty("type") Type type,
            @JsonProperty("amount") double amount,
            @JsonProperty("category") String category
    ) {
        this.type = Objects.requireNonNull(type, "Тип транзакции не может быть нулевым");
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма транзакции должна быть положительной");
        }
        this.amount = amount;
        this.category = Objects.requireNonNull(category, "Категория не может быть пустой");
    }
}
