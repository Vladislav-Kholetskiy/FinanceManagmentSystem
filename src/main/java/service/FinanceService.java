package service;

import model.ExpenseCategory;
import model.IncomeCategory;
import model.Transaction;
import model.User;
import storage.UserStorage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

public class FinanceService {
    private final UserStorage userStorage;

    public FinanceService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void registerUser(String username, String password) throws IOException {
        User newUser = new User(username, password);
        userStorage.saveUser(newUser);
    }

    public boolean userExists(String username) {
        return userStorage.userExists(username);
    }

    public User authenticateUser(String username, String password) throws IOException {
        if (userStorage.authenticate(username, password)) {
            return userStorage.loadUser(username);
        } else {
            throw new IOException("Неверное имя пользователя или пароль.");
        }
    }

    public void addExpenseCategory(User user, String categoryName, double limit) throws IOException {
        ExpenseCategory category = new ExpenseCategory(categoryName, limit);
        user.getWallet().addExpenseCategory(category);
        userStorage.saveUser(user);
    }

    public void addIncomeCategory(User user, String categoryName) throws IOException {
        IncomeCategory category = new IncomeCategory(categoryName);
        user.getWallet().addIncomeCategory(category);
        userStorage.saveUser(user);
    }

    public void updateExpenseCategoryLimit(User user, String categoryName, double newLimit) throws IOException {
        ExpenseCategory category = user.getWallet().getExpenseCategories().get(categoryName);
        if (category != null) {
            category.setLimit(newLimit);
            userStorage.saveUser(user);
        } else {
            throw new IllegalArgumentException("Категория расходов не найдена: " + categoryName);
        }
    }

    public void addIncome(User user, double amount, String category) throws IOException {
        Transaction income = new Transaction(Transaction.Type.INCOME, amount, category);
        user.getWallet().addTransaction(income);
        userStorage.saveUser(user);
    }

    public void addExpense(User user, double amount, String category) throws IOException {
        Transaction expense = new Transaction(Transaction.Type.EXPENSE, amount, category);
        user.getWallet().addTransaction(expense);
        userStorage.saveUser(user);
    }

    public double calculateTotalIncome(User user) {
        return user.getWallet().getTransactions().stream()
                .filter(t -> t.getType() == Transaction.Type.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double calculateTotalExpense(User user) {
        return user.getWallet().getTransactions().stream()
                .filter(t -> t.getType() == Transaction.Type.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public Map<String, Double> calculateRemainingBudgets(User user) {
        return user.getWallet().getExpenseCategories().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().getRemaining()
                ));
    }

    public Map<String, Double> calculateIncomeByCategory(User user) {
        return user.getWallet().getTransactions().stream()
                .filter(t -> t.getType() == Transaction.Type.INCOME)
                .collect(Collectors.groupingBy(
                        Transaction::getCategory,
                        Collectors.summingDouble(Transaction::getAmount)
                ));
    }


    public Map<String, Double> calculateSpentByCategory(User user) {
        return user.getWallet().getTransactions().stream()
                .filter(t -> t.getType() == Transaction.Type.EXPENSE)
                .collect(Collectors.groupingBy(
                        Transaction::getCategory,
                        Collectors.summingDouble(Transaction::getAmount)
                ));
    }
}
