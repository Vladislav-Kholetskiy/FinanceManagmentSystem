package model;

import lombok.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
    private double balance;
    private List<Transaction> transactions = new ArrayList<>();
    private Map<String, ExpenseCategory> expenseCategories = new HashMap<>();
    private Map<String, IncomeCategory> incomeCategories = new HashMap<>();

    public void credit(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Сумма расхода не может быть отрицательной");
        }
        this.balance += amount;
    }

    public void debit(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Сумма дохода не может быть отрицательной");
        }
        this.balance -= amount;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        if (transaction.getType() == Transaction.Type.INCOME) {
            credit(transaction.getAmount());
            IncomeCategory category = incomeCategories.get(transaction.getCategory());
            if (category == null) {
                category = new IncomeCategory(transaction.getCategory());
                incomeCategories.put(category.getName(), category);
            }
        } else if (transaction.getType() == Transaction.Type.EXPENSE) {
            debit(transaction.getAmount());
            ExpenseCategory category = expenseCategories.get(transaction.getCategory());
            if (category != null) {
                category.addSpent(transaction.getAmount());
                if (category.isOverBudget()) {
                    System.out.println("Внимание: расходы в категории \"" + category.getName() + "\" превышают лимит!");
                }
            } else {
                throw new IllegalArgumentException("Категория расходов не найдена: " + transaction.getCategory());
            }
            if (balance < 0) {
                System.out.println("Внимание: ваш баланс стал отрицательным: " + balance);
            }
        }
    }

    public void addIncomeCategory(IncomeCategory category) {
        incomeCategories.put(category.getName(), category);
    }

    public void addExpenseCategory(ExpenseCategory category) {
        expenseCategories.put(category.getName(), category);
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    public Map<String, ExpenseCategory> getExpenseCategories() {
        return Collections.unmodifiableMap(expenseCategories);
    }

    public Map<String, IncomeCategory> getIncomeCategories() {
        return Collections.unmodifiableMap(incomeCategories);
    }
}
